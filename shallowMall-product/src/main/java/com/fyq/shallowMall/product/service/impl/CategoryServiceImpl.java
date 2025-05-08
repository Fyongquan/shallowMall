package com.fyq.shallowMall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyq.common.utils.PageUtils;
import com.fyq.common.utils.Query;
import com.fyq.shallowMall.product.dao.CategoryDao;
import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.CategoryBrandRelationService;
import com.fyq.shallowMall.product.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

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
        while(catalogId != 0){
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

        if(!StringUtils.isEmpty(oldCategoryName) && !oldCategoryName.equals(newCategoryName)){
            //TODO 更新其他关联表中的数据
            categoryBrandRelationService.updateCategory(newCategoryName, category.getCatId());
        }
    }

    // 递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root, Map<Long, List<CategoryEntity>> childrenMap) {
        return childrenMap.getOrDefault(root.getCatId(), Collections.emptyList()).stream()
                .peek(child -> child.setChildren(getChildrens(child, childrenMap)))
                .sorted((menu1, menu2) -> (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()))
                .collect(Collectors.toList());
    }
}