package com.github.easy.auth.core.authentication.manager;


import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.exception.AuthException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证处理器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public interface AuthenticationManager {

    /**
     * 提取token
     * @param request
     * @param response
     * @return
     */
    String extractToken(HttpServletRequest request, HttpServletResponse response);

    /**
     * 设置token
     * @param token
     * @param request
     * @param response
     */
    void setToken(String token,HttpServletRequest request, HttpServletResponse response);

    /**
     * 认证token信息
     * @param token
     * @return 令牌
     * * @throws AuthException
     */
    Authentication authenticate(String token) throws AuthException;

    /**
     * 刷新认证信息
     * @param token 原来的token
     * @return  新的token
     */
    String refreshAuthentication(String token);

    /**
     * 将认证信息设置到上下文
     * @param authentication    认证信息
     */
    void setContext(Authentication authentication);
}
