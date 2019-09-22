package com.github.easy.auth.core.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>服务认证启动器</p>
 *
 * @author PengCheng
 * @date 2019/5/31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(AuthAutoConfiguration.class)
public @interface EnableAutoAuth {
}
