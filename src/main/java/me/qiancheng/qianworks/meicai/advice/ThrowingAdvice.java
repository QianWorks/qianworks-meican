package me.qiancheng.qianworks.meicai.advice;

import me.qiancheng.qianworks.meicai.model.Base;
import org.apache.catalina.filters.RemoteIpFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import static javafx.scene.input.KeyCode.R;

/**
 * Created by iamya on 6/26/2016.
 */
@Aspect
@Component
public class ThrowingAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(ThrowingAdvice.class);

    @Pointcut("execution(* me.qiancheng.qianworks.meicai.controller..*.*(..))")
    public void inWebLayer(){ }

    @Around("inWebLayer()")
    public Object exhandler(ProceedingJoinPoint point){
        Object object = null;
        try {
            object = point.proceed();
        } catch (Throwable throwable) {
            object = new Base("系统繁忙","请稍后再试！");
            LOG.error("::::"+throwable.getMessage(),throwable);
        } finally {
            LOG.debug(point.toLongString());
        }
        return object;
    }

}
