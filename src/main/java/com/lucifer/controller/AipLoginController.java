package com.lucifer.controller;


import com.lucifer.model.User;
import com.lucifer.service.UserLoginService;
import com.lucifer.utils.Result;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户登录
 * @author liufengxuan
 *
 */

@Api(basePath = "/v1", value = "登录", description = "登录controller", produces = "application/json")
@RestController
@RequestMapping(value = "/v1")
public class AipLoginController {

	@Resource
	private UserLoginService userLoginService;
	

	
	/**
	 * 登录
	 * @param  @RequestBody user  example
	 * 账号登陆 {phone:"18610814074",password:"xxxxxxx"}
	 * 微博登陆{weiboId:"xxxxxxx",accessToken:"xxxxxxxxxx"}
	 * 微信登陆{weixinId:"xxxxxxx",accessToken:"xxxxxxxxxx"}
	 * @return  {ok:true,data:"token-value",message:"xxxxx"}
	 * @throws Exception 
	 */
	@ApiOperation(value = "用户登录",  notes = "user login")
	@RequestMapping(value="/logins",method=RequestMethod.POST)
	@ResponseBody
	public Result login(@RequestBody User user) throws Exception{
		Result result = userLoginService.login(user);
		return result;
	}
	
	/**
	 * 登出
	 * @param  @CookieValue String token 
	 * @return @ResponseBody {ok:true,data:"token-value",message:"xxxxx"}
	 */
	@ApiOperation(value = "用户登出")
	@RequestMapping(value="/logins/{user_id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Result logout(@CookieValue String token){
		userLoginService.logout(token);
		return Result.ok();
	}
	
	/**
	 * 登出
	 * @param  @CookieValue String token 
	 * @return @ResponseBody {ok:true,data:"token-value",message:"xxxxx"}
	 */
	@ApiOperation(value = "用户登出")
	@RequestMapping(value="/logout/{user_id}",method=RequestMethod.POST)
	@ResponseBody
	public Result logoutPost(@CookieValue String token){
		userLoginService.logout(token);
		return Result.ok();
	}
	

}
