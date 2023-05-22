package com.cos.security1.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExceptionProcessor {
    public void makeExceptionResponse(HttpServletRequest request, HttpServletResponse response, Exception exception) throws IOException;
}
