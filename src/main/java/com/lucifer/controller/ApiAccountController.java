package com.lucifer.controller;


import com.lucifer.model.User;
import com.lucifer.service.AccountService;
import com.lucifer.service.SmsService;
import com.lucifer.utils.Result;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用来注册,绑定手机
 * @author liufengxuan
 *
 */
@Controller
public class ApiAccountController {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private SmsService smsService;
	
	final Logger logger = LoggerFactory.getLogger(ApiAccountController.class);

	/**
	 * 发送验证码
	 * @param @RequestBody user  example {phone:"18610814074"}
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "发送验证码")
	@RequestMapping(value="/v1/phone-sms",method=RequestMethod.POST)
	@ResponseBody
	public Result sendCheckMessage(@RequestBody User user, HttpServletRequest request) throws Exception{
		logger.info("sendCheckMessage has been called");
		logger.info("phone is : "+user.getPhone());
		Result result = smsService.sendCheckCode(user.getPhone(),this.getIpAddr(request));
		return result;
	}
	
	
	/**
	 * 比较验证码
	 * @param @RequestBody user  example {phone:"18610814074",code:"xxxxxx"}
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "比较验证码")
	@RequestMapping(value="/v1/phone-checks",method=RequestMethod.POST)
	@ResponseBody
	public Result checkCode(@RequestBody User user) throws Exception{
		return smsService.checkCode(user.getPhone(), user.getCode());
	}
	
	
	/**
	 * 注册
	 * @param @RequestBody user  example {phone:"18610814074",code:"xxxxxx",password:"xxxxxxx",rePassword:"xxxxxx"}
	 * @return {ok:true,data:"token-Characters"}
	 * @throws Exception
	 */
	@ApiOperation(value = "注册")
	@RequestMapping(value="/v1/registers",method=RequestMethod.POST)
	@ResponseBody
	public Result register(@RequestBody User user) throws Exception{
		Result result = accountService.register(user);
		return result;
	}
	
	/**
	 * 重置密码
	 * @param @RequestBody user  example {phone:"18610814074",code:"xxxxxx",password:"xxxxxxx",rePassword:"xxxxxx"}
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "重置密码")
	@RequestMapping(value="/v1/reset-pass",method=RequestMethod.POST)
	@ResponseBody
	public Result resetPassword(@RequestBody User user) throws Exception{
		Result result = accountService.resetPassword(user);
		return result;
	}
	
	/**
	 * 绑定手机
	 * @param @RequestBody user  example {phone:"18610814074",code:"xxxxxx",password:"xxxxxxx",rePassword:"xxxxxx"}
	 * @param userId PathVariable
	 * @param token cookieValue
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "绑定手机")
	@RequestMapping(value="/v1/bind-phone/{user_id}",method=RequestMethod.POST)
	@ResponseBody
	public Result bindPhone(@RequestBody User user, @PathVariable("user_id") Long userId, @CookieValue String token) throws Exception{
		user.setId(userId);
		return accountService.bindPhone(user,token);		
	}
	
	@ApiOperation(value = "绑定手机")
	@RequestMapping(value="/v1/bind-phone",method=RequestMethod.POST)
	@ResponseBody
	public Result bindPhone(@RequestBody User user, @CookieValue String token) throws Exception{
		//user.setUserId(userId);
		return accountService.bindPhone(user,token);		
	}
	
	/**
	 * 比较密码
	 * @param @RequestBody user  example {userId:"xxxx",password:"xxxxxxx"}
	 * @param token : session_cookie
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "比较密码")
	@RequestMapping(value="/v1/check-password",method=RequestMethod.POST)
	@ResponseBody
	public Result checkPassword(@RequestBody User user, @CookieValue String token){
		return accountService.checkPassword(user);
	}
	
	/**
	 * 从新绑定手机
	 * @param @RequestBody user  example {phone:"18610814074",code:"xxxxxx",password:"xxxxxxx",userId:"xxx"}
	 * @param userId PathVariable
	 * @param token : session_cookie
	 * @return {ok:true}
	 * @throws Exception
	 */
	@ApiOperation(value = "从新绑定手机")
	@RequestMapping(value="/v1/re-bind-phone/{user_id}",method=RequestMethod.POST)
	@ResponseBody
	public Result reBindPhone(@RequestBody User user, @PathVariable("user_id") Long userId, @CookieValue String token) throws Exception{
		user.setId(userId);
		return accountService.reBindPhone(user,token);
	}
	
	/**
	 * 检查手机是否已经注册
	 * @param phone
	 * @return
	 * @throws Exception 
	 */
	@ApiOperation(value = "检查手机是否已经注册")
	@RequestMapping(value="/v1/is-phone-exist/{phone}",method=RequestMethod.GET)
	@ResponseBody
	public Result isPhoneExist(@PathVariable() String phone) throws Exception{
		return accountService.isPhoneExist(phone);
	}
	
	
	/**
	 * 获取手机发送的验证码 (为方面测试用，千万别在客户端调用)
	 * @param phone
	 * @return
	 */
	@ApiOperation(value = "获取手机发送的验证码 (为方面测试用，千万别在客户端调用")
	@RequestMapping(value="/v1/phone-check-code/{phone}",method=RequestMethod.GET)
	@ResponseBody
	public Result phoneCheckCode(@PathVariable() String phone){
		return smsService.getPhoneCheckCode(phone);
	}
	
	
	/**
	 * 所有用户数量
	 * @return
	 */
	@ApiOperation(value = "所有用户数量")
	@RequestMapping(value="/v1/all-user-count",method=RequestMethod.GET)
	@ResponseBody
	public Result allUserCount(){
		return Result.ok(accountService.allUserCount());
	}
	
	/**
	 * 获取用户的客户端ip
	 * @param request
	 * @return
	 */
	public  String getIpAddr(HttpServletRequest request)  {  
		String ip  =  request.getHeader("X-Real-IP");
		if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
		    ip  =   request.getHeader("x-forwarded-for");   
		}   
		if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
		    ip  =  request.getHeader( "Proxy-Client-IP" );  
		}   
		if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
		    ip  =  request.getHeader( "WL-Proxy-Client-IP" );  
		}   
		if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {  
			ip  =  request.getRemoteAddr();  
		}   
		return  ip;  
	 }  
	
	
}
