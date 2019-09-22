package com.github.easy.auth.core.filter;

import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.authentication.limiter.AuthLimiter;
import com.github.easy.auth.core.authentication.limiter.AuthLimiterChain;
import com.github.easy.auth.core.authentication.manager.AuthenticationManager;
import com.github.easy.auth.core.exception.AuthException;
import com.github.easy.auth.core.exception.ExceptionHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 鉴权过滤器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public class AuthenticationFilter implements Filter {

    private AuthenticationManager authenticationManager;

    private AuthLimiterChain chain;

    private ExceptionHandler exceptionHandler;

    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthLimiterChain chain,
            ExceptionHandler exceptionHandler) {
        this.authenticationManager = authenticationManager;
        this.chain = chain;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            List<AuthLimiter> requestLimiterList = this.chain.getRequestLimiterList();
            for (AuthLimiter authRequestLimiter : requestLimiterList) {
                if (authRequestLimiter.skipAuth(request, response)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String token = this.authenticationManager.extractToken(request, response);
            if (token == null) {
                throw new AuthException();
            }
            Authentication authentication = this.authenticationManager.authenticate(token);
            if (authentication == null) {
                throw new AuthException();
            }
            this.authenticationManager.setContext(authentication);
            String newToken = this.authenticationManager.refreshAuthentication(token);
            this.authenticationManager.setToken(newToken, request, response);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            this.exceptionHandler.handle(response,e);
        }
    }

    @Override
    public void destroy() {

    }


}
