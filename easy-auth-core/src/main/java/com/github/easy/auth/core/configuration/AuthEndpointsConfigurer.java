package com.github.easy.auth.core.configuration;


import com.github.easy.auth.core.endpoint.AuthFrameworkEndpointHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>认证服务视图控制器配置持有</p>
 *
 * @author PengCheng
 * @date 2019/5/30
 */
public class AuthEndpointsConfigurer {

    /**
     * 窗口映射
     */
    private AuthFrameworkEndpointHandlerMapping authFrameworkEndpointHandlerMapping;


    /**
     * 路径前缀分析
     */
    private String prefix;

    /**
     * 路径映射
     */
    private Map<String, String> patternMap = new HashMap<String, String>();

    /**
     * 过滤器
     */
    private List<Object> interceptors = new ArrayList<Object>();


    public AuthFrameworkEndpointHandlerMapping getAuthFrameworkEndpointHandlerMapping() {
        return authFrameworkEndpointHandlerMapping();
    }

    private AuthFrameworkEndpointHandlerMapping authFrameworkEndpointHandlerMapping() {
        if (authFrameworkEndpointHandlerMapping == null) {
            authFrameworkEndpointHandlerMapping = new AuthFrameworkEndpointHandlerMapping();
            authFrameworkEndpointHandlerMapping.setMappings(patternMap);
            authFrameworkEndpointHandlerMapping.setPrefix(prefix);
            authFrameworkEndpointHandlerMapping.setInterceptors(interceptors.toArray());
        }
        return authFrameworkEndpointHandlerMapping;
    }


    public AuthEndpointsConfigurer prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public AuthEndpointsConfigurer pathMapping(String defaultPath, String customPath) {
        this.patternMap.put(defaultPath, customPath);
        return this;
    }
}
