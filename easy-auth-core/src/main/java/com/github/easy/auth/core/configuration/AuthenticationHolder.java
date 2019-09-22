package com.github.easy.auth.core.configuration;


import com.github.easy.auth.core.authentication.Authentication;

/**
 * 认证信息持有器
 *  可以通过该持有器获取当前的登陆用户
 * @author cheng.peng
 * @date 2019/9/5
 */
public class AuthenticationHolder {

    private Authentication authentication;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
