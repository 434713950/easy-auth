package com.github.easy.auth.core;

/**
 * @author cheng.peng
 * @date 2019/8/28
 */
public interface AuthConstants {

    String AUTH_HEADER = "bb_sc_credentials";

    String FRAMEWORK_PREFIX = "/easy_auth";

    /**
     * jwt默认的秘钥
     */
    String JWT_SECRETKEY = "dGhlIHN5c3RlbSBpcyB0ZXJyaWJsZQ==";

    /**
     * token过期时间，单位s
     */
    Long TOKEN_EXPIRE = 3600L;

    /**
     * 认证信息存储的过期时间，单位s
     */
    Long AUTH_STORE_EXPIRE = TOKEN_EXPIRE+100L;

}
