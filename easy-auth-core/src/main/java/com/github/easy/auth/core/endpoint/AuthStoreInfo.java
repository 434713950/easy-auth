package com.github.easy.auth.core.endpoint;


import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.exception.AuthException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cheng.peng
 * @date 2019/9/5
 */
public interface AuthStoreInfo {

    /**
     * 存储key
     * @param request
     * @return
     */
    String storeKey(HttpServletRequest request);

    /**
     * 认证信息
     * @param request
     * @return
     */
    Authentication authentication(HttpServletRequest request) throws AuthException;
}
