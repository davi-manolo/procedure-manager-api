package com.procedure.manager.config.security;

import javassist.NotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN,
                String.format("Authentication failed, requires valid username (verify email, password or token): %s", exception.getMessage()
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void commence( HttpServletResponse response, AccessDeniedException exception) throws IOException {
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN, String.format("Access denies: %s", exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public void commence(HttpServletResponse response, NotFoundException exception) throws IOException {
        setResponseError(response, HttpServletResponse.SC_FORBIDDEN, String.format("Not found: %s", exception.getMessage()));
    }

    private void setResponseError(HttpServletResponse response, int codeError, String messageError) throws IOException {
        response.setStatus(codeError);
        response.getWriter().write(messageError);
        response.getWriter().flush();
        response.getWriter().close();
    }

}
