package com.fyq.shallowMall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.annotation.LogAnnotation;
import com.fyq.shallowMall.product.dao.CategoryDao;
import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import com.fyq.shallowMall.product.service.CategoryService;
import com.fyq.shallowMall.product.vo.Catalog2Vo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1、查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);

        long startTime = System.nanoTime();

        // 2、构建父子关系的哈希表
        Map<Long, List<CategoryEntity>> childrenMap = entities.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getParentCid));

        List<CategoryEntity> collect = entities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .peek(menu -> menu.setChildren(getChildrens(menu, childrenMap)))
                .sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()))
                .collect(Collectors.toList());

        long endTime = System.nanoTime();
        System.out.println("耗时：" + (endTime - startTime) / 1000000 + "ms");

        // 3、找到所有一级分类并设置子节点
        return collect;
    }

    @Override
    public void removeMenuByIds(List<Long> list) {
        //TODO  检查当前删除的菜单，是否被其他地方引用

        //逻辑删除
        baseMapper.deleteBatchIds(list);
    }

    @Override
    public Long[] getCatalogPath(Long catalogId) {
        List<Long> path = new ArrayList<>();
        while (catalogId != 0) {
            CategoryEntity categoryEntity = this.getById(catalogId);
            path.add(catalogId);
            catalogId = categoryEntity.getParentCid();
        }
        Collections.reverse(path);
        System.out.println(path);
        return path.toArray(new Long[path.size()]);
    }

    @Transactional
    @Override
    public void updateCategory(CategoryEntity category) {
        CategoryEntity oldCategory = this.getById(category.getCatId());
        String oldCategoryName = oldCategory.getName();
        String newCategoryName = category.getName();

        this.updateById(category);

        if (!StringUtils.isEmpty(oldCategoryName) && !oldCategoryName.equals(newCategoryName)) {
            //TODO 更新其他关联表中的数据
            categoryBrandRelationService.updateCategory(newCategoryName, category.getCatId());
        }
    }

    @Override
    public List<CategoryEntity> getLevel1Categories() {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryEntity::getParentCid, 0)
                .select(CategoryEntity::getCatId, CategoryEntity::getName);
        return this.list(queryWrapper);
    }

    //TODO 产生堆外内存溢出OutOfDirectMemoryError
    //从redis中获取数据
    @Override
    public Map<String, List<Catalog2Vo>> getCatalogJson(){
        //加入缓存，缓存中存储的是字符串
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String catalogJson = operations.get("catalogJson");
        if(StringUtils.isEmpty(catalogJson)){
            Map<String, List<Catalog2Vo>> catalogJsonFromDb = getCatalogJsonFromDbWithRedisLock();
            return catalogJsonFromDb;
        }
        //缓存不为空，直接返回
        System.out.println("从redis中获取数据");
        return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>(){});
    }

    //使用分布式锁从数据库查询并封装数据
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDbWithRedisLock() {
        //获取分布式锁
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("catalogJsonLock", uuid, 300, TimeUnit.SECONDS);
        if(lock){
            Map<String, List<Catalog2Vo>> result;
            try{
                result = getCatalogJsonFromDb();
            }finally {
                //Redis+Lua脚本删除锁
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList("catalogJsonLock"), uuid);
            }
            return result;

        }else{
            System.out.println("等待其他线程释放锁");
            try {
                Thread.sleep(100); // 等待 100 毫秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return getCatalogJson();
        }
    }


    //使用本地锁从数据库查询并封装数据
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDbWithLocalLock() {

        synchronized (this){
            String catalogJson = redisTemplate.opsForValue().get("catalogJson");
            //缓存不为空，直接返回
            if (!StringUtils.isEmpty(catalogJson)){
                return JSON.parseObject(catalogJson, new TypeReference<Map<String, List<Catalog2Vo>>>(){});
            }
            // 1、查询所有的分类并构建分类map
            List<CategoryEntity> allCategories = this.list();
            if (allCategories == null || allCategories.isEmpty()) {
                return Collections.emptyMap();
            }
            Map<Long, List<CategoryEntity>> categoryMap = allCategories.stream()
                    .collect(Collectors.groupingBy(CategoryEntity::getParentCid));
            // 2、取出父节点是0的一级分类
            List<CategoryEntity> category1Entities = categoryMap.get(0L);

            // 3、根据一级分类去构建<1级分类, List<2级分类>>的map
            Map<String, List<Catalog2Vo>> result = category1Entities
                    .stream().collect(Collectors.toMap(key -> key.getCatId().toString(), l1category -> {
                        List<CategoryEntity> level2List = categoryMap.getOrDefault(l1category.getCatId(), Collections.emptyList());
                        // 4、从分类map中根据一级分类id获取二级分类
                        return level2List.stream()
                                .filter(Objects::nonNull)
                                .map(l2category -> {
                                    List<CategoryEntity> level3List = categoryMap.getOrDefault(l2category.getCatId(), Collections.emptyList());
                                    // 5、从分类map中根据二级分类id获取三级分类
                                    List<Catalog2Vo.Catalog3Vo> catalog3Vo = level3List.stream()
                                            .filter(Objects::nonNull)
                                            .map(l3category -> {
                                                //封装三级分类
                                                return new Catalog2Vo.Catalog3Vo(
                                                        l2category.getCatId().toString(),
                                                        l3category.getCatId().toString(),
                                                        l3category.getName());
                                            }).collect(Collectors.toList());
                                    //封装二级分类
                                    return new Catalog2Vo(
                                            l1category.getCatId().toString(),
                                            catalog3Vo,
                                            l2category.getCatId().toString(),
                                            l2category.getName());
                                }).collect(Collectors.toList());
                    }));
            //如果缓存为空，将查到的对象转为json存储到redis中
            redisTemplate.opsForValue().set("catalogJson", JSON.toJSONString(result), 1, TimeUnit.DAYS);
            return result;
        }
    }

    //从数据库查询并封装数据
    public Map<String, List<Catalog2Vo>> getCatalogJsonFromDb() {
        System.out.println("查询数据库");
        // 1、查询所有的分类并构建分类map
        List<CategoryEntity> allCategories = this.list();
        if (allCategories == null || allCategories.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Long, List<CategoryEntity>> categoryMap = allCategories.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getParentCid));
        // 2、取出父节点是0的一级分类
        List<CategoryEntity> category1Entities = categoryMap.get(0L);

        // 3、根据一级分类去构建<1级分类, List<2级分类>>的map
        Map<String, List<Catalog2Vo>> result = category1Entities
                .stream().collect(Collectors.toMap(key -> key.getCatId().toString(), l1category -> {
                    List<CategoryEntity> level2List = categoryMap.getOrDefault(l1category.getCatId(), Collections.emptyList());
                    // 4、从分类map中根据一级分类id获取二级分类
                    return level2List.stream()
                            .filter(Objects::nonNull)
                            .map(l2category -> {
                                List<CategoryEntity> level3List = categoryMap.getOrDefault(l2category.getCatId(), Collections.emptyList());
                                // 5、从分类map中根据二级分类id获取三级分类
                                List<Catalog2Vo.Catalog3Vo> catalog3Vo = level3List.stream()
                                        .filter(Objects::nonNull)
                                        .map(l3category -> {
                                            //封装三级分类
                                            return new Catalog2Vo.Catalog3Vo(
                                                    l2category.getCatId().toString(),
                                                    l3category.getCatId().toString(),
                                                    l3category.getName());
                                        }).collect(Collectors.toList());
                                //封装二级分类
                                return new Catalog2Vo(
                                        l1category.getCatId().toString(),
                                        catalog3Vo,
                                        l2category.getCatId().toString(),
                                        l2category.getName());
                            }).collect(Collectors.toList());
                }));
        //如果缓存为空，将查到的对象转为json存储到redis中
        redisTemplate.opsForValue().set("catalogJson", JSON.toJSONString(result), 1, TimeUnit.DAYS);
        return result;
    }


    // 递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, Map<Long, List<CategoryEntity>> childrenMap) {
        return childrenMap.getOrDefault(root.getCatId(), Collections.emptyList()).stream()
                .peek(child -> child.setChildren(getChildrens(child, childrenMap)))
                .sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()))
                .collect(Collectors.toList());
    }
}