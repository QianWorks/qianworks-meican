package me.qiancheng.qianworks.meicai.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by iamya on 6/26/2016.
 */
@Aspect
@Component
public class StopwatchAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(StopwatchAdvice.class);

    @Pointcut("execution(* me.qiancheng.qianworks.meicai.service.*.*(..))")
    public void stopwatch(){ }

    @Around(value="stopwatch()")
    public void stopwatchMethod(ProceedingJoinPoint point) throws Throwable {
        long time=System.currentTimeMillis();
        point.proceed();
        LOG.info(point.getSignature().toShortString() +" :: "+(System.currentTimeMillis()-time)+"ms.");
        LOG.debug("::: "+(System.currentTimeMillis()-time)+"ms."+ " "+point.getSignature().toLongString());
    }
}
