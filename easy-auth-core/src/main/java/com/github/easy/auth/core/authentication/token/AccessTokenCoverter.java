package com.github.easy.auth.core.authentication.token;

/**
 * 授权令牌变换器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public interface AccessTokenCoverter{

    /**
     * 生成token
     * @param accessToken 令牌
     * @return
     */
    String generateToken(AccessToken accessToken);

    /**
     *  解读令牌
     * @param token 令牌的token
     * @return
     */
    AccessToken parse(String token);
}
