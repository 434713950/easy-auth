package com.github.easy.auth.core.exception;

/**
 * @author cheng.peng
 * @date 2019/9/5
 */
public class AuthException extends Exception {

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}