package com.github.easy.auth.core.endpoint;

import com.github.easy.auth.core.authentication.Authentication;
import com.github.easy.auth.core.authentication.manager.AuthenticationManager;
import com.github.easy.auth.core.authentication.store.TokenStore;
import com.github.easy.auth.core.authentication.token.AccessToken;
import com.github.easy.auth.core.authentication.token.AccessTokenCoverter;
import com.github.easy.auth.core.exception.AuthException;
import com.github.easy.auth.core.service.AuthenticationSerivce;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2019/5/30
 */
@AuthFrameworkEndpoint
public class AuthEndpoint extends AbstractEndpoint{

    private AuthenticationSerivce authenticationSerivce;

    private TokenStore tokenStore;

    private AccessTokenCoverter accessTokenCoverter;

    private AuthenticationManager authenticationManager;

    public AuthEndpoint(AuthenticationSerivce authenticationSerivce, TokenStore tokenStore,
            AccessTokenCoverter accessTokenCoverter,AuthenticationManager authenticationManager) {
        this.authenticationSerivce = authenticationSerivce;
        this.tokenStore = tokenStore;
        this.accessTokenCoverter = accessTokenCoverter;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 生成服务token
     * @return
     */
    @GetMapping(value = "/token")
    @ResponseBody
    public void token(HttpServletRequest request, HttpServletResponse response) throws AuthException {
        String storeKey = authenticationSerivce.storeKey(request,response);
        if (storeKey == null) {
           throw new AuthException("lack of request param");
        }
        Authentication authentication = authenticationSerivce.authentication(request,response);
        tokenStore.storeAuthentication(storeKey,authentication,null);
        AccessToken accessToken = new AccessToken();
        accessToken.setSub(storeKey);
        accessToken.setExpireTime(tokenStore.expire());
        String token = accessTokenCoverter.generateToken(accessToken);
        //根据请求中的认证参数信息,去其请求接口生成token
        authenticationManager.setToken(token,request,response);
    }

    @GetMapping(value = "/token/remove")
    @ResponseBody
    public void removeToken(HttpServletRequest request,HttpServletResponse response) {
        String token = authenticationManager.extractToken(request,response);
        if (!StringUtils.isEmpty(token)) {
            AccessToken accessToken = accessTokenCoverter.parse(token);
            if (accessToken != null) {
                String storeKey = accessToken.getSub();
                if (!StringUtils.isEmpty(storeKey)) {
                    tokenStore.removeAuthentication(storeKey);
                }
            }
        }
    }
}
