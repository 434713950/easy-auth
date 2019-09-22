package com.github.easy.auth.core.authentication.store;

import com.github.easy.auth.core.AuthConstants;
import com.github.easy.auth.core.authentication.Authentication;

import java.util.concurrent.ConcurrentHashMap;

public class LocalTokenStore implements TokenStore{

    private ConcurrentHashMap<String,ExtStore> store = new ConcurrentHashMap<String, ExtStore>();

    @Override
    public Authentication readAuthentication(String storeKey) {
        ExtStore extStore = this.store.get(storeKey);
        if (extStore == null) {
            return null;
        }
        if (extStore.getExpireTimeStamp() < System.currentTimeMillis()) {
            this.store.remove(storeKey);
            return null;
        } else {
            return extStore.getAuthentication();
        }
    }

    @Override
    public void storeAuthentication(String storeKey, Authentication authentication, Long time) {
        if (time == null) {
            time = expire();
        }
        Long current = System.currentTimeMillis();
        ExtStore extStore=new ExtStore(authentication,current,current+time);
        this.store.put(storeKey,extStore);
    }

    @Override
    public void removeAuthentication(String storeKey) {
        this.store.remove(storeKey);
    }

    @Override
    public void refreshExpire(String storeKey, Long time) {
        if (time == null) {
            time = expire();
        }
        ExtStore extStore = this.store.get(storeKey);
        if (extStore!=null) {
            Long current = System.currentTimeMillis();
            extStore.setStoreTimeStamp(current);
            extStore.setExpireTimeStamp(current+time);
            this.store.put(storeKey,extStore);
        }
    }

    @Override
    public String storePrefix() {
        return null;
    }

    @Override
    public Long expire() {
        return AuthConstants.AUTH_STORE_EXPIRE;
    }


    class ExtStore {
        /**
         * 认账信息
         */
        private Authentication authentication;

        /**
         * 存储时间
         */
        private Long storeTimeStamp;


        /**
         * 过期时间
         */
        private Long expireTimeStamp;

        public ExtStore() {
        }

        public ExtStore(Authentication authentication, Long storeTimeStamp, Long expireTimeStamp) {
            this.authentication = authentication;
            this.storeTimeStamp = storeTimeStamp;
            this.expireTimeStamp = expireTimeStamp;
        }

        public Authentication getAuthentication() {
            return authentication;
        }

        public void setAuthentication(Authentication authentication) {
            this.authentication = authentication;
        }

        public Long getStoreTimeStamp() {
            return storeTimeStamp;
        }

        public void setStoreTimeStamp(Long storeTimeStamp) {
            this.storeTimeStamp = storeTimeStamp;
        }

        public Long getExpireTimeStamp() {
            return expireTimeStamp;
        }

        public void setExpireTimeStamp(Long expireTimeStamp) {
            this.expireTimeStamp = expireTimeStamp;
        }
    }
}
