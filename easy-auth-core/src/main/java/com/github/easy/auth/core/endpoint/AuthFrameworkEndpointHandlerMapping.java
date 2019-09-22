package com.github.easy.auth.core.endpoint;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p></p>
 *
 * @author PengCheng
 * @date 2019/5/30
 */
public class AuthFrameworkEndpointHandlerMapping extends RequestMappingHandlerMapping {

    private static final String REDIRECT = UrlBasedViewResolver.REDIRECT_URL_PREFIX;

    private static final String FORWARD = UrlBasedViewResolver.FORWARD_URL_PREFIX;

    private Map<String, String> mappings = new HashMap<String, String>();


    private Set<String> paths = new HashSet<String>();

    private String prefix;

    public AuthFrameworkEndpointHandlerMapping() {
        setOrder(Ordered.LOWEST_PRECEDENCE - 2);
    }

    public void setPrefix(String prefix) {
        if (!StringUtils.hasText(prefix)) {
            prefix = "";
        } else {
            while (prefix.endsWith("/")) {
                prefix = prefix.substring(0, prefix.lastIndexOf("/"));
            }
        }
        this.prefix = prefix;
    }

    public void setMappings(Map<String, String> patternMap) {
        this.mappings = new HashMap<String, String>(patternMap);
        for (String key : mappings.keySet()) {
            String result = mappings.get(key);
            if (result.startsWith(FORWARD)) {
                result = result.substring(FORWARD.length());
            }
            if (result.startsWith(REDIRECT)) {
                result = result.substring(REDIRECT.length());
            }
            mappings.put(key, result);
        }
    }

    public String getServletPath(String defaultPath) {
        return (prefix == null ? "" : prefix) + getPath(defaultPath);
    }


    public String getPath(String defaultPath) {
        String result = defaultPath;
        if (mappings.containsKey(defaultPath)) {
            result = mappings.get(defaultPath);
        }
        return result;
    }

    public Set<String> getPaths() {
        return paths;
    }


    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotationUtils.findAnnotation(beanType, AuthFrameworkEndpoint.class) != null;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo defaultMapping = super.getMappingForMethod(method, handlerType);
        if (defaultMapping == null) {
            return null;
        }

        Set<String> defaultPatterns = defaultMapping.getPatternsCondition().getPatterns();
        String[] patterns = new String[defaultPatterns.size()];

        int i = 0;
        for (String pattern : defaultPatterns) {
            patterns[i] = getServletPath(pattern);
            paths.add(pattern);
            i++;
        }
        PatternsRequestCondition requestCondition = new PatternsRequestCondition(patterns, getUrlPathHelper(),
                getPathMatcher(), useSuffixPatternMatch(), useTrailingSlashMatch(), getFileExtensions());

        RequestMappingInfo mapping = new RequestMappingInfo(requestCondition, defaultMapping.getMethodsCondition(),
                defaultMapping.getParamsCondition(), defaultMapping.getHeadersCondition(), defaultMapping.getConsumesCondition(),
                defaultMapping.getProducesCondition(), defaultMapping.getCustomCondition());
        return mapping;

    }
}
