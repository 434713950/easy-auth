package com.github.easy.auth.core.authentication.limiter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证请求限制器
 *
 * @author cheng.peng
 * @date 2019/9/6
 */
public interface AuthLimiter {

    /**
     * 判断是否跳过认证
     * @param request
     * @param response
     * @return
     */
    boolean skipAuth(HttpServletRequest request, HttpServletResponse response);
}
