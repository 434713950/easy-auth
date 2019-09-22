package com.github.easy.auth.core.service;

import com.github.easy.auth.core.authentication.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationSerivce {

    /**
     * 存储key
     * @param request
     * @param response
     * @return
     */
    String storeKey(HttpServletRequest request, HttpServletResponse response);

    /**
     * 认证信息
     * @param request
     * @param response
     * @return
     */
    Authentication authentication(HttpServletRequest request, HttpServletResponse response);
}
