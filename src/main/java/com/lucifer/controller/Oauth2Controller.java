package com.lucifer.controller;


import com.lucifer.exception.Oauth2CodeInvalidException;
import com.lucifer.model.AccessToken;
import com.lucifer.model.User;
import com.lucifer.service.UserLoginService;
import com.lucifer.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/26.
 */
@Controller
@RequestMapping(value = "/oauth2.0")
public class Oauth2Controller {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/authorize",method= RequestMethod.GET)
    public String authorize(Model model, @RequestParam(value = "response_type") String responseType,@RequestParam(value = "client_id") String clientId, @RequestParam(value = "redirect_uri")String redirectUri) throws IOException {
        //response.sendRedirect("/auth2.0/login");
        model.addAttribute("response_type",responseType);
        model.addAttribute("client_id",clientId);
        model.addAttribute("redirect_uri",redirectUri);
        logger.info("authorize has been called");
        return "oauth2/login";
    }

    @ApiOperation(value = "oauth2登录")
    @RequestMapping(value = "/authorize",method= RequestMethod.POST)
    public String login(Model model,
                        @RequestParam(value = "response_type") String responseType,
                        @RequestParam(value = "client_id") String clientId,
                        @RequestParam(value = "redirect_uri")String redirectUri,
                        User user) throws Exception {
        AccessToken accessToken = userLoginService.oauth2LoginByAccount(user);

        String url = null;
        if ("code".equals(responseType)) {
            url = redirectUri+"?code="+accessToken.getCode();
            return "redirect:"+ url;
        }else if ("token".equals(responseType)) {
            url = redirectUri+"?access_token="+accessToken.getToken();
            return "redirect:" + url;
        }

        return  "oauth2/login";
    }

    @RequestMapping(value = "/token",method= RequestMethod.GET)
    public String token(@RequestParam(value = "grant_type") String grantType,
                     @RequestParam(value = "client_id") String clientId,
                     @RequestParam(value = "client_secret") String clientSecret,
                     @RequestParam(value = "redirect_uri")String redirectUri,
                     @RequestParam(value = "code") String code,
                     HttpServletResponse response) throws IOException, Oauth2CodeInvalidException {
        AccessToken accessToken = userLoginService.loginByCode(code);
        String url = redirectUri+"?access_token="+accessToken.getToken();
        //response.sendRedirect(url);
        return "redirect:" + url;
    }

    @RequestMapping(value = "/me",method= RequestMethod.GET)
    @ResponseBody
    public Map me(@RequestParam(value = "access_token") String token){
        AccessToken accessToken = userLoginService.getAccessTokenByToken(token);
        Map resultMap = new HashMap<>();
        resultMap.put("openid",accessToken.getUserId());
        resultMap.put("client_id","none");
        return resultMap;
    }

    @RequestMapping(value = "/get_user_info",method= RequestMethod.GET)
    public User getUserInfo(@RequestParam(value = "access_token") String token){
        AccessToken accessToken = userLoginService.getAccessTokenByToken(token);
        User userInfo = userService.getUserInfo(accessToken.getUserId());
        return userInfo;
    }
}
