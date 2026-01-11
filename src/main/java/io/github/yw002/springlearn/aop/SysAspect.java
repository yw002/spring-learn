package io.github.yw002.springlearn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component // 把当前类交给Spring容器管理
@Aspect // 标记为切面类，Spring会识别这个类为AOP切面
public class SysAspect {
    /**
     * 单独定义了一个空方法 pointCut() 本质是抽离复用，两种写法等价，效果完全一致
     */
    @Pointcut("@annotation(io.github.yw002.springlearn.aop.Log)")
    private void pointCut() {
    }

    /**
     * 说明：@Around是环绕通知，环绕通知是功能最强的通知，能控制目标方法【执行前/执行后/异常时】的所有逻辑
     * 备注：也可以直接把切点表达式写在@Around中，例如：@Around("@annotation(io.github.yw002.springlearn.aop.Log)")
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("========== 环绕通知【开始】=============");
        // 可以在这里获取用户名，解析session或token
        System.out.println("可以在这里获取用户名，解析session或token");
        // 获取被增强类和方法的信息
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取被增强的方法对象
        Method method = methodSignature.getMethod();
        // 从方法中解析注解
        if (method != null) {
            Log logAnnotation = method.getAnnotation(Log.class);
            System.out.println("从joinPoint对象中解析了被增强方法，是在@Log注解里的参数name："
                    + logAnnotation.name());
        }
        // 方法名字
        String name = method.getName();
        System.out.println("获取到注解的方法名称：" + name);
        System.out.println("此处可以使用工具类获取Request对象，从而获取url、请求方式、IP、操作时间等信息，进行保存日志操作");
        // 将日志保存到数据库等操作
        System.out.println("========== 环绕通知【结束】=============");
        return joinPoint.proceed();
    }
}
