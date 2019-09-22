package com.github.easy.auth.core.authentication.limiter;

import java.util.ArrayList;
import java.util.List;

/**
 * 限制器链
 */
public class AuthLimiterChain {

    private List<AuthLimiter> requestLimiterList = new ArrayList<AuthLimiter>();

    public List<AuthLimiter> getRequestLimiterList() {
        return requestLimiterList;
    }

    public void setRequestLimiterList(List<AuthLimiter> requestLimiterList) {
        this.requestLimiterList = requestLimiterList;
    }

    public void addRequestLimiter(AuthLimiter requestLimiter) {
        this.requestLimiterList.add(requestLimiter);
    }
}
