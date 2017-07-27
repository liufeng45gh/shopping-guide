package com.lucifer.service;


import com.lucifer.dao.UserDao;
import com.lucifer.model.User;
import com.lucifer.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Service
public class AccountService {
	
	@Resource
	private SmsService smsService;
	
	@Resource
	private UserLoginService userLoginService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private UserDao userDao;
	

		
	@Transactional(propagation= Propagation.REQUIRED)
	public Result register(User user) throws Exception{
		
		Result result = smsService.checkCode(user.getPhone(), user.getCode());
		if (!result.isOk()) {
			return Result.fail("验证码错误");
		}
		if (!user.getPassword().equals(user.getRePassword())) {
			return Result.fail("2次密码不一致");
		}
		if (this.isUserExist(user.getPhone())) {
			return Result.fail("用户已经存在");
		}
		String account = "mp_"+ RandomUtil.getNextAccount();
		user.setAccount(account);
		user.setPhone(user.getPhone());
		String salt = RandomUtil.getNextSalt();
		user.setSalt(salt);
		String encrypt_password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+salt);
		user.setPassword(encrypt_password);
		UUID uuid = UUID.randomUUID();
		user.setUuid(uuid.toString());
		user.setCreatedAt(DateUtils.now());
		user.setUpdatedAt(DateUtils.now());
		userDao.insertUser(user);
		user.setPassword(user.getRePassword());
		//userCenterService.register(user);
		//user.setNickName("用户"+user.getUserId());
		//userDao.initUserNick(user);
		//初始化
		userService.userInit(user.getId());
		
		//登录
		user.setPassword(user.getRePassword());
		return userLoginService.login(user);
		//return Result.ok();
	}
	
	@Transactional(propagation= Propagation.REQUIRED)
	public Result resetPassword(User user) throws Exception{
		Result result = smsService.checkCode(user.getPhone(), user.getCode());
		if (!result.isOk()) {
			return Result.fail("验证码错误");
		}
		if (!user.getPassword().equals(user.getRePassword())) {
			return Result.fail("2次密码不一致");
		}
		User dbUser = userDao.getUserByPhone(user.getPhone());
		if  (null == dbUser)  {
			return Result.fail("用户未找到");
		}
		//userCenterService.resetPassword(user);
		String encrypt_password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+dbUser.getSalt());
		dbUser.setPassword(encrypt_password);
		

		userDao.updatePassword(dbUser);
		return Result.ok();
	}
	
	@Transactional(propagation= Propagation.REQUIRED)
	public Result bindPhone(User user, String token) throws Exception{
		
		Result result = smsService.checkCode(user.getPhone(), user.getCode());
		if (!result.isOk()) {
			return Result.fail("验证码错误");
		}
		if (!user.getPassword().equals(user.getRePassword())) {
			return Result.fail("2次密码不一致");
		}
		
		User dbUser = userDao.getUserById(user.getId());
		
		
		if (null == dbUser) {
			return Result.fail("未找到用户");
		}
		
		if (!StringHelper.isEmpty(dbUser.getPhone())) {
			return Result.fail("用户已绑定手机");
		}
		
		Boolean isExist = this.isUserExist(user.getPhone());
		
		if (isExist) {
			return Result.fail("该手机号已被占用");
		}
		//userCenterService.thirdLoginBindPhone(user, token);
		
		dbUser.setPhone(user.getPhone());
		String salt = RandomUtil.getNextSalt();
		dbUser.setSalt(salt);
		String encrypt_password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+salt);
		dbUser.setPassword(encrypt_password);		
		//
		this.bindPhoneWidthCache(dbUser);
				
		return Result.ok();
	}
	
	
	
	public Result checkPassword(User user){
		User dbUser = userDao.getUserById(user.getId());
		if  (null == dbUser)  {
			return Result.fail("用户未找到");
		}
		String md5Password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+dbUser.getSalt());
		if (md5Password.equals(dbUser.getPassword())) {
			return Result.ok();
		}		
		return Result.fail("密码错误");
	}
	
	public Result reBindPhone(User user, String token) throws Exception{
		
		Result result = smsService.checkCode(user.getPhone(), user.getCode());
		if (!result.isOk()) {
			return Result.fail("验证码错误");
		}
		
		User dbUser = userDao.getUserById(user.getId());
		
		if  (null == dbUser)  {
			return Result.fail("未找到用户");
		}
		String md5Password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+dbUser.getSalt());
		if (!md5Password.equals(dbUser.getPassword())) {
			return Result.fail("密码错误");
		}		
		
				
		Boolean isExist = this.isUserExist(user.getPhone());
		
		if (isExist) {
			return Result.fail("该手机号已被占用");
		}
		user.setId(dbUser.getId());
//		Result resultUserCenter = userCenterService.reBindPhone(user, token);
//		if (!resultUserCenter._isOk()) {
//			return resultUserCenter;
//		}
		dbUser.setPhone(user.getPhone());
		String salt = RandomUtil.getNextSalt();
		dbUser.setSalt(salt);
		String encrypt_password = Md5Utils.md5(Md5Utils.md5(user.getPassword())+salt);
		dbUser.setPassword(encrypt_password);
		
		this.bindPhoneWidthCache(dbUser);
		
		
		return Result.ok();
	}
	
	public Result isPhoneExist(String phone) throws Exception{
		if (this.isUserExist(phone)) {
			return Result.fail("用户已经存在");
		}
		return Result.ok("手机未注册");
	}
	
	public void bindPhoneWidthCache(User user){
		
		userDao.userBindPhone(user);
	}
	
	public Boolean isUserExist(String telephone) throws Exception{
//		Integer resultCount = sqlSession.selectOne("userCountByPhone", telephone);
//		if (resultCount>0) {
//			return true;
//		}
//		Boolean isUserCanRegister = userCenterService.canUserRegister(telephone);
//		if (isUserCanRegister) {
//			return false;
//		}
//		return true;
		User user = userDao.getUserByPhone(telephone);
		if (null!=user) {
			return true;
		}
		return false;
	}
	
	public Integer allUserCount(){
		return userDao.allUserCount();
	}

	public boolean resetPassword(String account,String oldPass,String newPass){
		User user = userDao.getUserByAccount(account);
		String md5OldPassword = Md5Utils.md5(Md5Utils.md5(oldPass)+user.getSalt());
		if(md5OldPassword.equals(user.getPassword())){
			String md5NewPassword = Md5Utils.md5(Md5Utils.md5(newPass)+user.getSalt());
			user.setPassword(md5NewPassword);
			userDao.updatePassword(user);
			return true;
		}else{
			return false;
		}
	}

}
