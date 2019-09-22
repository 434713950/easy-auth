package com.github.easy.auth.core.endpoint;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>认证请求控制器注解</p>
 *
 * @author PengCheng
 * @date 2019/5/30
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AuthFrameworkEndpoint {
}
