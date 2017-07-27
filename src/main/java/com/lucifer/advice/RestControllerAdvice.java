package com.lucifer.advice;

import com.lucifer.controller.web.WebItemController;
import com.lucifer.exception.NotLoginException;
import com.lucifer.exception.RepetitiveOperationException;
import com.lucifer.utils.DateUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by liufx on 15/12/16.
 */
@ControllerAdvice(basePackageClasses =  WebItemController.class)
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerException(HttpServletRequest request, NotLoginException ex,HttpServletResponse response) throws UnsupportedEncodingException {
        HttpStatus status = HttpStatus.valueOf(401);
        response.setHeader("X-Err-Message", URLEncoder.encode(ex.getMessage(), "utf-8"));
        return new ResponseEntity<>(new NotLoginExceptionType(status.value(),request, ex), status);
    }

    public class NotLoginExceptionType extends HashMap{
        NotLoginExceptionType (Integer status, HttpServletRequest request, com.lucifer.exception.NotLoginException ex){
            this.put("status",status);
            this.put("oper_code",-1);
            this.put("error","NoAuth Server Error");
            this.put("exception",ex.getClass().getName());
            this.put("message",ex.getMessage());

            this.put("detail",ex.getStackTrace()[0].toString());
            this.put("path",request.getContextPath()+request.getServletPath());
            this.put("url",request.getRequestURL());
            this.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.now()));
        }
    }

    @ExceptionHandler(RepetitiveOperationException.class)
    @ResponseBody
    ResponseEntity<?> handleControllerRepetitiveOperationException(HttpServletRequest request, Exception ex,HttpServletResponse response) throws UnsupportedEncodingException {
        HttpStatus status = HttpStatus.valueOf(410);
        response.setHeader("X-Err-Message", URLEncoder.encode(ex.getMessage(), "utf-8"));
        return new ResponseEntity<>(new RepetitiveOperationExceptionType(status.value(),request, ex), status);
    }

    public class RepetitiveOperationExceptionType extends HashMap{
        RepetitiveOperationExceptionType (Integer status, HttpServletRequest request, Exception ex){
            this.put("status",status);
            this.put("oper_code",-1);
            this.put("error","Repetitive Server Error");
            this.put("exception",ex.getClass().getName());
            this.put("message",ex.getMessage());

            this.put("detail",ex.getStackTrace()[0].toString());
            this.put("path",request.getContextPath()+request.getServletPath());
            this.put("url",request.getRequestURL());
            this.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(DateUtils.now()));
        }
    }



}
