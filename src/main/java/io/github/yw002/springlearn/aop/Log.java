package io.github.yw002.springlearn.aop;

import java.lang.annotation.*;

/**
 * @interface是自定义注解
 * @Target 是 Java 原生元注解，必填项，
 *  ElementType.PARAMETER → 允许标注在 方法的参数上（比如public void addUser(@Log Integer userId)）
 *  ElementType.METHOD → 允许标注在 方法上（比如 Mapper 方法、Service 业务方法）
 * @Retention 约束注解的「生效生命周期」
 * @Documented 非必填，用javadoc命令给项目生成API 文档时，被@Log注解标记的方法/参数，在文档里会显示这个注解的信息
 */

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    public String name() default "";
}
