package com.lucifer.dao;

import com.lucifer.model.AccessToken;
import com.lucifer.model.User;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserDao extends IBatisBaseDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	//@Cacheable(value="userByPhoneCache", key="'userByPhoneCache:'+#phone")//
	public User getUserByAccount(String account){
		logger.info("----getUserByAccount has been called!--");
		//return (User)sqlSession.selectOne("getUserByPhone", phone);
		return sqlSession.selectOne("getUserByAccount", account);

	}
	
	//@Cacheable(value="userByPhoneCache", key="'userByPhoneCache:'+#phone")// 
	public User getUserByPhone(final String phone){
		return sqlSession.selectOne("getUserByPhone", phone);
	}
	

	//@Cacheable(value="userByQqIdCache" ,key="'userByQqIdCache:'+#qqId")
	public User getUserByQqId(String qqId){
		return (User)sqlSession.selectOne("getUserByQqId", qqId);
	}
	


	public User insertUser(User user){		
		//this.removeUserCache(user);
		 
		user.setId(this.nextId());
		//user.setNickName(user.getAccount());
		sqlSession.insert("insertUser",user);
		//redisTemplate.opsForList().leftPush(RedisKeyPreConstant.USER_WILL_INSERT_QUEQUE, user);
		 return user;
		
	}
	

	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 */
	public Integer updatePassword(User user){
		return sqlSession.update("updatePassword", user);
	}
	
	/**
	 * 绑定手机
	 * @param user
	 * @return
	 */
	public Integer userBindPhone(User user){
		return sqlSession.update("userBindPhone", user);
	}
	


	//@Cacheable(value="userByIdCache" ,key="'userByIdCache:'+#userId")// 
	public User getUserById(final Long userId){

		return sqlSession.selectOne("getUserById", userId);


	}
	

	//@CacheEvict(value="userByIdCache",key="#user.getUserId()")// 清空accountCache 缓存  
	public Integer updateUserInfo(User user){
		Integer updateCount = sqlSession.update("updateUserInfo",user);		
		return updateCount;
	}
	

	
	public Boolean isNickExist(String nickName){
		Integer resultCount = sqlSession.selectOne("userCountByNickName", nickName);
		if (resultCount>0) {
			return true;
		}
		return false;
	}
	
	public Integer updateUserNick(User user){
		return sqlSession.update("updateUserNick",user);
	}
	
	public Integer initUserNick(User user){
		return sqlSession.update("initUserNick",user);
	}
	
	public Integer allUserCount(){
		Integer resultCount = sqlSession.selectOne("allUserCount");
		return resultCount;
	}



	
	public List<User> userCmsSearch(String sql){
		return this.sqlSession.selectList("userCmsSearch", sql);
	}
	
	public Integer userCmsSearchCount(String sql){
		return this.sqlSession.selectOne("userCmsSearchCount",sql);
	}
	
	public List<User> getAllThirdPartUserList(){
		return this.sqlSession.selectList("getAllThirdPartUserList");
	}
	
	public List<User> getAllNeedBindPhoneUserList(){
		return this.sqlSession.selectList("getAllNeedBindPhoneUserList");
	}
	
	public List<User> getAllPhoneOnlyUserList(){
		return this.sqlSession.selectList("getAllPhoneOnlyUserList");
	}


	public Long getUserIdByToken(String token){
		return sqlSession.selectOne("getUserIdByToken",token);
	}

	public AccessToken newUserLoginToken(Long userId){
		AccessToken accessToken = new AccessToken();
		String token = RandomStringUtils.randomAlphanumeric(20);
		String code = RandomStringUtils.randomAlphanumeric(20);
		accessToken.setToken(token);
		accessToken.setUserId(userId);
		accessToken.setCode(code);

		this.sqlSession.insert("insertUserLoginToken",accessToken);
		return accessToken;
	}

	public void removeToken(String token){
		this.sqlSession.update("removeToken",token);
	}

	public AccessToken getAccessTokenByCode(String code){
		return this.sqlSession.selectOne("getAccessTokenByCode",code);
	}

	public void setCodeInvalid(String code){
		this.sqlSession.update("setCodeInvalid",code);
	}

	public AccessToken getAccessTokenByToken(String accessToken){
		return this.sqlSession.selectOne("getAccessTokenByToken",accessToken);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<User> getUserInfoList(Map params){
		return sqlSession.selectList("getUserInfoList", params);
	}

	@SuppressWarnings("rawtypes")
	public Integer getUserInfoListCount(Map params){
		return (Integer)sqlSession.selectOne("getUserInfoListCount", params);
	}


	public void setUserBlock(User user){
		sqlSession.update("setUserBlock",user);
	}





}
