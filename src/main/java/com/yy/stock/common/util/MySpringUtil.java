package com.yy.stock.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class MySpringUtil implements ApplicationContextAware {
    private static ApplicationContext context;

    public static void set(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    /**
     * 通过字节码获取
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    public static <T> T getBean(Class<T> requiredType, Object... args) throws BeansException {
        return context.getBean(requiredType, args);
    }

    /**
     * 通过name获取Bean
     *
     * @param name
     * @return
     */
    public static Object getObjBean(String name) {
        return context.getBean(name);
    }

    /**
     * 通过BeanName获取
     *
     * @param beanName
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    /**
     * 通过beanName和字节码获取
     *
     * @param name
     * @param beanClass
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> beanClass) {
        return context.getBean(name, beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
 