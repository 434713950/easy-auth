package com.github.easy.auth.core.authentication;

import java.io.Serializable;

/**
 * @author cheng.peng
 * @date 2019/8/28
 */
public class Authentication implements Serializable {

    private static final long serialVersionUID = 4801644086846431573L;

    private Object credentials;

    private Object details;

    private Object principal;

    public void setCredentials(Object credentials) {
        this.credentials = credentials;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public void setPrincipal(Object principal) {
        this.principal = principal;
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public Object getDetails() {
        return this.details;
    }

    public Object getPrincipal() {
        return this.principal;
    }
}
