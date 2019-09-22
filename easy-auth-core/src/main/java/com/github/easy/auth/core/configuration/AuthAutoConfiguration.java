package com.github.easy.auth.core.configuration;

import com.github.easy.auth.core.AuthConstants;
import com.github.easy.auth.core.authentication.limiter.AuthLimiterChain;
import com.github.easy.auth.core.authentication.limiter.EndpointAuthLimiter;
import com.github.easy.auth.core.authentication.manager.AuthenticationManager;
import com.github.easy.auth.core.authentication.manager.DefaultAuthenticationManager;
import com.github.easy.auth.core.authentication.store.LocalTokenStore;
import com.github.easy.auth.core.authentication.store.TokenStore;
import com.github.easy.auth.core.authentication.token.AccessTokenCoverter;
import com.github.easy.auth.core.authentication.token.JWTAccessTokenCoverter;
import com.github.easy.auth.core.endpoint.AuthEndpoint;
import com.github.easy.auth.core.endpoint.AuthFrameworkEndpointHandlerMapping;
import com.github.easy.auth.core.exception.DefaultExceptionHandler;
import com.github.easy.auth.core.exception.ExceptionHandler;
import com.github.easy.auth.core.filter.AuthenticationFilter;
import com.github.easy.auth.core.service.AuthenticationSerivce;
import com.github.easy.auth.core.signer.MacSigner;
import com.github.easy.auth.core.signer.Signer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * <p>认证相关的自动配置项</p>
 *
 * @author PengCheng
 * @date 2019/5/30
 */
public class AuthAutoConfiguration {

    /**
     * 断点相关配置
     */
    private AuthEndpointsConfigurer endpoints;

    public AuthAutoConfiguration() {
        this.endpoints = new AuthEndpointsConfigurer();
        this.endpoints.prefix(AuthConstants.FRAMEWORK_PREFIX);
    }

    @Bean
    public AuthFrameworkEndpointHandlerMapping authFrameworkEndpointHandlerMapping() {
        return this.getEndpointsConfigurer().getAuthFrameworkEndpointHandlerMapping();
    }

    @Bean
    public AuthEndpoint authEndpoint(
            AuthenticationSerivce authenticationSerivce,
            TokenStore tokenStore,
            AccessTokenCoverter accessTokenCoverter,
            AuthenticationManager authenticationManager) {
        return new AuthEndpoint(authenticationSerivce,tokenStore,accessTokenCoverter,authenticationManager);
    }

    @Bean
    @ConditionalOnMissingBean({ ExceptionHandler.class})
    public ExceptionHandler exceptionHandler(){
        return new DefaultExceptionHandler();
    }

    @Bean
    public FilterRegistrationBean AuthFilterRegistrationBean(
            AuthenticationManager authenticationManager,
            AuthLimiterChain chain,
            ExceptionHandler exceptionHandler) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AuthenticationFilter(authenticationManager,chain,exceptionHandler));
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("easyAuthFilter");
        return filterRegistrationBean;
    }

    @Bean
    @ConditionalOnMissingBean({ AuthenticationManager.class})
    public AuthenticationManager authenticationManager(AccessTokenCoverter accessTokenCoverter,
                                                       TokenStore tokenStore,
                                                       AuthenticationHolder holder){
        return new DefaultAuthenticationManager(accessTokenCoverter,tokenStore,holder,"Authentication");
    }

    @Bean
    @ConditionalOnMissingBean({ AccessTokenCoverter.class})
    public AccessTokenCoverter accessTokenCoverter(){
        return new JWTAccessTokenCoverter(AuthConstants.JWT_SECRETKEY);
    }

    @Bean
    @ConditionalOnMissingBean({ TokenStore.class})
    public TokenStore tokenStore(){
        return new LocalTokenStore();
    }

    @Bean
    public AuthLimiterChain authLimiterChain(){
        AuthLimiterChain chain = new AuthLimiterChain();
        chain.addRequestLimiter(new EndpointAuthLimiter());
        return chain;
    }

    @Bean
    public AuthenticationHolder authenticationHolder(){
        return new AuthenticationHolder();
    }

    @Bean
    @ConditionalOnMissingBean({ Signer.class})
    public Signer signer(){
        return new MacSigner(AuthConstants.JWT_SECRETKEY);
    }

    public AuthEndpointsConfigurer getEndpointsConfigurer() {
        return endpoints;
    }

}
