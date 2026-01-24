package io.github.yw002.springlearn.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Bean的后置处理器
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("user")) {
            System.out.println("BeanPostProcessor 的 postProcessBeforeInitialization 方法执行了->Bean 的【所有初始化动作执行之前】执行的后置增强");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("user")) {
            System.out.println("BeanPostProcessor 的 postProcessAfterInitialization 方法执行了->Bean 的【所有初始化动作完成之后】执行的后置增强");
            /**
             * Spring AOP 的核心实现位置，标准做法
             */
            System.out.println("\n===== cglib 动态代理部分【开始】 =====");
            // 创建CGLIB的核心增强器对象，用于生成代理子类
            Enhancer enhancer = new Enhancer();
            // 设置【被代理的父类】：CGLIB是子类代理，代理类是User的子类
            enhancer.setSuperclass(bean.getClass());
            // 设置【回调处理器】：核心增强逻辑，拦截所有方法调用
            enhancer.setCallback(new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    // proxy：生成的CGLIB代理对象本身
                    // method：被拦截到的、要执行的原User类的方法（比如getName、toString等）
                    // args：方法执行时传入的参数
                    // ========== 这里是【增强逻辑】的核心位置 ==========
                    System.out.println("【CGLIB代理-前置增强（原User方法执行前的代码）】原生User类准备执行的方法：" + method.getName());
                    // 执行【原User对象】的目标方法，获取返回值。相当于在CGLIB生成的子类中调用 super.xxx()方法，在执行前后可以做增强。
                    Object result = method.invoke(bean, args);
                    System.out.println("【CGLIB代理-执行中（原User方法）】：执行了【Object result = method.invoke(bean, args);】");
                    System.out.println("【CGLIB代理-后置增强（原User方法执行后的代码）】原生User类执行完毕的方法：" + method.getName() + "，返回值（测试类打印对象toString字符串）：" + result);
                    // =================================================
                    System.out.println("===== cglib 动态代理部分【结束】 =====\n");
                    return result;
                }
            });
            // 创建并返回CGLIB动态代理对象，替换原User对象，此处会在控制台执行被代理的User（proxy）的构造方法
            return enhancer.create();
        }
        // 非user的Bean，直接返回原生对象，不做任何增强
        return bean;
    }
}
