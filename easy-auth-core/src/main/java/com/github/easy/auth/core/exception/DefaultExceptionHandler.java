package com.github.easy.auth.core.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public void handle(HttpServletResponse response, Exception e) {
        if (e instanceof AuthException) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.append(e.getMessage());
            } catch (IOException ex) {
                //nothing
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }
}
