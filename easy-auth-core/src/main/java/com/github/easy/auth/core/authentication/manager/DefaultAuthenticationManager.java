package com.github.easy.auth.core.authentication.manager;


import com.github.easy.auth.core.AuthConstants;
import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.authentication.store.TokenStore;
import com.github.easy.auth.core.authentication.token.AccessToken;
import com.github.easy.auth.core.authentication.token.AccessTokenCoverter;
import com.github.easy.auth.core.configuration.AuthenticationHolder;
import com.github.easy.auth.core.exception.AuthException;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author cheng.peng
 * @date 2019/9/5
 */
public class DefaultAuthenticationManager implements AuthenticationManager{

    private AccessTokenCoverter accessTokenCoverter;

    private TokenStore tokenStore;

    private AuthenticationHolder authenticationHolder;

    private String authHeader;

    public DefaultAuthenticationManager(
            AccessTokenCoverter accessTokenCoverter,
            TokenStore tokenStore,
            AuthenticationHolder authenticationHolder,
            String authHeader) {
        this.accessTokenCoverter = accessTokenCoverter;
        this.tokenStore = tokenStore;
        this.authenticationHolder = authenticationHolder;
        this.authHeader = authHeader;
    }

    @Override
    public String extractToken(HttpServletRequest request, HttpServletResponse response) {
        return request.getHeader(this.authHeader);
    }

    @Override
    public void setToken(String token, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(AuthConstants.AUTH_HEADER,token);
    }

    @Override
    public Authentication authenticate(String token) throws AuthException {
        AccessToken accessToken = accessTokenCoverter.parse(token);
        if (accessToken.isExpired()) {
            throw new AuthException("token expired，please retrieve token");
        }
        String storeKey = accessToken.getSub();
        if (StringUtils.isEmpty(storeKey)){
            return null;
        }
        return tokenStore.readAuthentication(storeKey);
    }

    @Override
    public String refreshAuthentication(String token) {
        AccessToken accessToken = accessTokenCoverter.parse(token);
        String storeKey = accessToken.getSub();
        //刷新存储过期时间
        tokenStore.refreshExpire(storeKey, AuthConstants.AUTH_STORE_EXPIRE);
        AccessToken newToken = new AccessToken();
        newToken.setSub(accessToken.getSub());
        newToken.setExpireTime(AuthConstants.TOKEN_EXPIRE);
        return accessTokenCoverter.generateToken(accessToken);
    }

    @Override
    public void setContext(Authentication authentication) {
        this.authenticationHolder.setAuthentication(authentication);
    }
}
