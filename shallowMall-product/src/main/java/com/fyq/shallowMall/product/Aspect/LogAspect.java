package com.fyq.shallowMall.product.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.fyq.shallowMall.product.annotation.LogAnnotation)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint point){
        Long start = System.nanoTime();
        try {
            Object result = point.proceed();
            Long end = System.nanoTime();
            log.info("总耗时：{}ms", (end - start) * 0.000001);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
