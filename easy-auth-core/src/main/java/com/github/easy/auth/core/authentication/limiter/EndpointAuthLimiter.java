package com.github.easy.auth.core.authentication.limiter;

import com.github.easy.auth.core.AuthConstants;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EndpointAuthLimiter implements AuthLimiter {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean skipAuth(HttpServletRequest request, HttpServletResponse response) {
        if (antPathMatcher.match(AuthConstants.FRAMEWORK_PREFIX+"/**",request.getRequestURI())){
            return true;
        }
        return false;
    }
}
