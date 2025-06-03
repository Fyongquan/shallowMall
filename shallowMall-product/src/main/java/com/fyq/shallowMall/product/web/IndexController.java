package com.fyq.shallowMall.product.web;

import com.fyq.shallowMall.product.entity.CategoryEntity;
import com.fyq.shallowMall.product.service.CategoryService;
import com.fyq.shallowMall.product.vo.Catalog2Vo;
import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: wanzenghui
 * @Date: 2021/10/26 22:01
 * 首页页面跳转
 */
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {

        // 查询所有1级分类
        List<CategoryEntity> categoryEntitys = categoryService.getLevel1Categories();

        //
        model.addAttribute("categorys", categoryEntitys);


        // 解析器自动拼装classpath:/templates/  + index +  .html =》 classpath:/templates/index.html
        // classpath表示类路径，编译前是resources文件夹，编译后resources文件夹内的文件会统一存放至classes文件夹内
        return "index";
    }

    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2Vo>> getCatalogJson() {
        Map<String, List<Catalog2Vo>> map = categoryService.getCatalogJson();
        return map;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        //获取一把锁，只要锁的名字一致，就是同一把锁
        RLock lock = redisson.getLock("my-lock" + Thread.currentThread().getId());

        // 加锁
        lock.lock();
        try {
            System.out.println("加锁成功");
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //解锁
            System.out.println("解锁成功..." + Thread.currentThread().getId());
            lock.unlock();
        }

        return "hello";
    }

    @GetMapping("/write")
    @ResponseBody
    public String writeValue() {

        String s = "";

        //改数据加写锁
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.writeLock();
        rLock.lock();
        try {
            s = UUID.randomUUID().toString();
            System.out.println("写锁加锁成功。。。" + Thread.currentThread().getId());
            Thread.sleep(12000);
            redisTemplate.opsForValue().set("writeValue", s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("解锁成功。。。" + Thread.currentThread().getId());
        }

        return s;
    }

    @GetMapping("/read")
    @ResponseBody
    public String readValue() {

        String s = "";
        //读数据加读锁
        RReadWriteLock lock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = lock.readLock();
        rLock.lock();
        try {
            System.out.println("读锁加锁成功。。。" + Thread.currentThread().getId());
            Thread.sleep(12000);
            s = redisTemplate.opsForValue().get("writeValue");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
            System.out.println("读锁成功。。。" + Thread.currentThread().getId());
        }


        return s;
    }

    @GetMapping("/park")
    @ResponseBody
    public String park() {
        RSemaphore semaphore = redisson.getSemaphore("park");
        boolean b = semaphore.tryAcquire();

        if(b){
            //执行业务
        }else{
            return "error";
        }

        return "ok";
    }

    @GetMapping("/go")
    @ResponseBody
    public String go() {
        RSemaphore semaphore = redisson.getSemaphore("park");
        semaphore.release();

        return "ok";
    }

    @GetMapping ("/lockDoor")
    @ResponseBody
    public String lockDoor(){
        RCountDownLatch door = redisson.getCountDownLatch("lockDoor");
        door.trySetCount(5);
        try {
            door.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "放假了...";
    }

    @GetMapping ("/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable("id") Long id){
        RCountDownLatch door = redisson.getCountDownLatch("lockDoor");
        door.countDown();

        return id + "班的人都走了";
    }

}