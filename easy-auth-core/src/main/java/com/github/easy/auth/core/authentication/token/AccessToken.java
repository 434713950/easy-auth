package com.github.easy.auth.core.authentication.token;

import java.io.Serializable;
import java.util.Date;

/**
 * 访问令牌
 *
 * @author cheng.peng
 * @date 2019/8/28
 */
public class AccessToken implements Serializable {

    private static final long serialVersionUID = 6670129471093570262L;

    /**
     * 令牌主体值
     */
    private String sub;

    /**
     * 令牌的过期时间
     */
    private Date expiration;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     * 令牌是否已经过期
     * @return
     */
    public boolean isExpired() {
        return this.expiration != null && this.expiration.before(new Date(System.currentTimeMillis()));
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    /**
     * 设置过期时长,单位s
     * @param expire
     */
    public void setExpireTime(Long expire){
        this.expiration = new Date(System.currentTimeMillis() + expire*1000);
    }

    /**
     * 令牌过期时间
     * @return
     */
    public Date getExpiration() {
        return this.expiration;
    }

    /**
     * 获取距离令牌过期的时长,单位s
     * @return
     */
    public int getExpiresIn() {
        return this.expiration != null ? Long.valueOf((this.expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue() : 0;
    }

}


