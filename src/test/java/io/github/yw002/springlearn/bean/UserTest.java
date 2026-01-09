package io.github.yw002.springlearn.bean;

import io.github.yw002.springlearn.bean.config.MySpringConfig;
import io.github.yw002.springlearn.bean.lifecycle.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Bean的生命周期测试类
 * 纯 Spring 项目的入口，等价于 SpringBoot 项目的SpringlearnApplication启动类
 */
public class UserTest {
    public static void main(String[] args) {
        /* 第一步，创建Spring容器 + 加载我的配置类 MySpringConfig。
         基于注解配置的应用上下文，是 Spring 提供的纯注解开发的核心容器类，专门用来加载@Configuration标注的配置类
         作用 = SpringBoot 中的SpringApplication.run(...)，都是启动 Spring 容器
         */
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MySpringConfig.class);
        /* 第二步，从Spring容器中，获取已经创建好的User Bean对象。
         这里的 User 对象，是 Spring 已创建好、完成所有初始化、完成所有增强的成品Bean
        */
        User user = ctx.getBean(User.class);
        // 打印User对象，验证Bean确实被创建成功
        System.out.println("Bean创建成功了，这个bean是在生命周期【BeanPostProcessor#after中】被CGLIB代理后的user：" + user);
        // 关闭容器，触发Bean的销毁方法
        ctx.close();
    }
}
