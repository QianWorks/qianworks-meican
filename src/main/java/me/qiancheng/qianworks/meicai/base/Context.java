package me.qiancheng.qianworks.meicai.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Context implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        setApplicationContextValue(applicationContext);
    }

    /**
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException 
     */  
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);  
    }


    /**
     * 为了解决findbugs ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD
     *
     * @param applicationContext
     */
    public static void setApplicationContextValue(ApplicationContext applicationContext) {
        Context.applicationContext = applicationContext;
    }


    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return Context.applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return Context.applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return Context.applicationContext.isSingleton(name);
    }

    public static <T> Class<T> getType(String name) throws NoSuchBeanDefinitionException {
        return (Class<T>) Context.applicationContext.getType(name);
    }

    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return Context.applicationContext.getAliases(name);
    }
}