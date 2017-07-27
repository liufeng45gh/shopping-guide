package com.lucifer.controller;


import com.lucifer.dao.UserDao;
import com.lucifer.exception.ParamException;
import com.lucifer.model.SearchParam;
import com.lucifer.model.User;
import com.lucifer.service.UserService;
import com.lucifer.utils.PageInfoWriter;
import com.lucifer.utils.Result;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApiUserController {
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserDao userDao;
	

	
	/**
	 * 获取用户信息
	 * @return
	 */
	@ApiOperation(value = " 获取用户信息")
	@RequestMapping(value="/v1/users/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public Result userInfo(@PathVariable  Long userId){
		User user = userDao.getUserById(userId);
		user.setPassword(null);
		user.setSalt(null);
		return Result.ok(user);
	}
	
	/**
	 * 获取用户信息
	 * @return
	 */
	@ApiOperation(value = "获取用户信息")
	@RequestMapping(value="/v1/users-in/{userId}",method=RequestMethod.GET)
	@ResponseBody
	public User userInfoV2(@PathVariable  Long userId,HttpServletResponse response){
		User user = userDao.getUserById(userId);		
		if (null == user) {
			response.setStatus(404);
		} else {
			user.setPassword(null);
			user.setSalt(null);
		}
		return user;
	}
	
	
	/**
	 * 获取多用户信息
	 * @param ids : ids=1,2,3
	 * @return
	 */
	@ApiOperation(value = "获取多用户信息")
	@RequestMapping(value="/v1/users",method=RequestMethod.GET)
	@ResponseBody
	public List<User> usersInfo(@RequestParam  String  ids){
		List<User> userList = new ArrayList<User>();
		String [] idstrs = ids.split(",");
		for (String idstr:idstrs) {
			Long userId = Long.valueOf(idstr);
			User user = userDao.getUserById(userId);
			userList.add(user);
		}
		
		
		return userList;
	}
	
	/**
	 * 修改用户信息
	 * @param token : session_cookie
	 * @param user {userId:1,nickName:"xxx",avatar:"http://xxx.png"}
	 * @return
	 * @throws IOException 
	 */
	@ApiOperation(value = "修改用户信息")
	@RequestMapping(value="/v1/users/{userId}",method=RequestMethod.POST)
	@ResponseBody
	public Result updateUserInfo(@CookieValue String token, @RequestBody User user, @PathVariable Long userId) throws IOException{
		user.setId(userId);
		Result result =  userService.updateUserInfo(token, user);
		if (!result.isOk()) {
			return result;
		}
		User dbUser = userDao.getUserById(userId);		
		return Result.ok(dbUser);
	}
	


	@ApiOperation(value = "管理后台查询用户信息")
	@RequestMapping(value="/v1/user-cms-search",method=RequestMethod.GET)
	@ResponseBody
	public List cmsSearch(SearchParam param,
			@RequestHeader(value = "X-Page-Num",required=false,defaultValue="1") Integer page,
            @RequestHeader(value = "X-Page-Row",required=false,defaultValue="10") Integer count,
            HttpServletResponse response){
		param.setPage(page);
		param.setCount(count);
		List resultList = userService.userCmsSearch(param);
		
		Integer resultCount = userService.userCmsSearchCount(param);
		PageInfoWriter pageInfo = PageInfoWriter.create(page, count, resultCount);
		pageInfo.write(response);
		return resultList;
	}


	@ApiOperation(value = "新建用户")
	@RequestMapping(value="/v1/create-user",method=RequestMethod.POST)
	@ResponseBody
	public User create(@RequestBody User user) throws ParamException {
		 return userService.createUser(user);
	}

}
