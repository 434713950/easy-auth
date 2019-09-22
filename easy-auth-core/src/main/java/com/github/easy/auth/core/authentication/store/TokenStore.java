package com.github.easy.auth.core.authentication.store;


import com.github.easy.auth.core.authentication.Authentication;

/**
 * token存储器
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public interface TokenStore {

    /**
     * 根据token读取鉴权信息
     * @param storeKey
     * @return
     */
    Authentication readAuthentication(String storeKey);

    /**
     * 存储鉴权信息
     * @param storeKey
     * @param authentication
     * @param time
     */
    void storeAuthentication(String storeKey, Authentication authentication, Long time);

    /**
     * 移除鉴权信息
     * @param storeKey
     */
    void removeAuthentication(String storeKey);

    /**
     * 刷新token过期时间
     * @param storeKey
     * @param time
     */
    void refreshExpire(String storeKey, Long time);

    /**
     * 设置存储前缀
     * @return
     */
    String storePrefix();

    /**
     * 过期时长
     * @return
     */
    Long expire();
}
