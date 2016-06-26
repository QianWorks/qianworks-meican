package me.qiancheng.qianworks.meicai.base;

import me.qiancheng.qianworks.meicai.schedule.AsyncMailWorker;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MethodTimeActive implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncMailWorker.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
        long time = System.currentTimeMillis();
        Object object = invocation.proceed();
        String methodName = invocation.getMethod().getName();
       LOG.debug(methodName+":::"+(System.currentTimeMillis()-time)+"ms");
        return object;
    }
    
}