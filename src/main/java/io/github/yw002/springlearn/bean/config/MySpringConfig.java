package io.github.yw002.springlearn.bean.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 告诉 Spring 容器：去哪里扫描、哪些类要被创建为 Bean、纳入 Spring 容器管理。
 */
@Configuration
@ComponentScan("io.github.yw002.springlearn.bean.lifecycle")
public class MySpringConfig {
}
