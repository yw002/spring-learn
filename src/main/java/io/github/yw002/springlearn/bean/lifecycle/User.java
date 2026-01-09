package io.github.yw002.springlearn.bean.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class User implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    public User() {
        System.out.println("构造函数，User 的构造函数执行了");
    }

    private String name;

    @Value("李东子")
    public void setName(String name) {
        System.out.println("依赖注入，setName 函数");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware 接口执行了 的setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware 接口执行了 setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ApplicationContextAware 接口执行了 setApplicationContext");
    }

    @PostConstruct
    public void init() {
        System.out.println("初始化方法，自定义init方法（PostConstruct注解）");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("初始化方法，InitializingBean 接口执行了 afterPropertiesSet");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("销毁方法，自定义destroy方法（PreDestroy注解）");
    }
}
