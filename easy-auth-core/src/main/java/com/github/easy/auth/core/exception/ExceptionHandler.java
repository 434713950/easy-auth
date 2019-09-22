package com.github.easy.auth.core.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * 认证异常处理器
 *
 * @author cheng.peng
 * @date 2019/9/5
 */
public interface ExceptionHandler {

    void handle(HttpServletResponse response, Exception e);
}
