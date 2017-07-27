package com.lucifer.advice;

import com.lucifer.controller.Oauth2Controller;
import com.lucifer.exception.Oauth2LoginException;
import com.lucifer.utils.Constant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2016/7/10.
 */
@ControllerAdvice(basePackageClasses =  Oauth2Controller.class)
public class Oauth2ControllerAdvice {

    @ExceptionHandler(Oauth2LoginException.class)
    String handleControllerException(HttpServletRequest request, Throwable ex) {
        request.setAttribute(Constant.KEY_RESULT_MESSAGE,ex.getMessage());
        request.setAttribute("response_type",request.getParameter("response_type"));
        request.setAttribute("client_id",request.getParameter("client_id"));
        request.setAttribute("redirect_uri",request.getParameter("redirect_uri"));
        return "oauth2/login";
    }

}
