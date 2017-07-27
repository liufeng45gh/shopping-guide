package com.lucifer.service;


import com.lucifer.dao.SmsDao;
import com.lucifer.utils.DateUtils;
import com.lucifer.utils.Result;
import com.lucifer.utils.StringHelper;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;


@Service
public class SmsService {

	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	private String key_pre="msgcode:";
	
	private String codeSendPhonesList = "CODE_SENDING_PHONE_LIST";

	@Autowired
	private SmsDao smsDao;
	
	private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
	
	public Result sendCheckCode(String telephone, String remoteAddr) throws Exception{
		
		
		String code = smsDao.getCheckCode(telephone);
		logger.info("code is :"+code);		
		stringRedisTemplate.opsForValue().set(key_pre+telephone, code);
		stringRedisTemplate.expire(key_pre+telephone, 20, TimeUnit.MINUTES);
		
		boolean isRecordIpMoreThan20 = this.isRecordIpMoreThan20(remoteAddr);
		if (isRecordIpMoreThan20) {
			return Result.fail("同ip发送次数超限");
		}
		this.recordSendIp(remoteAddr);
		
		
		boolean isRecordPhoneMoreThan5 = this.isRecordPhoneMoreThan5(telephone);
		if (isRecordPhoneMoreThan5) {
			return Result.fail("手机号一天内发送次数超限");
		}
		this.recordSendPhone(telephone);
		
		
		stringRedisTemplate.opsForList().leftPush(codeSendPhonesList, telephone);
		//log.info("redisTemplate.opsForValue().set(key_pre+telephone, code,60000);");
		//this.sendToRomotApi(telephone, code);
		return Result.ok();
	}
	
	private static final String recordPrefix = "MESSAGE_SEND_RECORD:";
	
	private void recordSendIp(String ip){
		stringRedisTemplate.opsForZSet().add(recordPrefix+ip, String.valueOf(DateUtils.now().getTime()), DateUtils.now().getTime());
		stringRedisTemplate.expire(recordPrefix+ip, 1, TimeUnit.DAYS);
	}
	
	private boolean isRecordIpMoreThan20(String ip){
		Long count = stringRedisTemplate.opsForZSet().count(recordPrefix+ip, DateUtils.yesterday().getTime(), Long.MAX_VALUE);
		logger.info("isRecordIpMoreThan20 ? ip is:"+ip +"  count is :"+count);
		if (count>=20) {
			return true;
		}
		return false;
	}
	
	private void recordSendPhone(String phone){
		stringRedisTemplate.opsForZSet().add(recordPrefix+phone, String.valueOf(DateUtils.now().getTime()), DateUtils.now().getTime());
		stringRedisTemplate.expire(recordPrefix+phone, 1, TimeUnit.DAYS);
	}
	
	private boolean isRecordPhoneMoreThan5(String phone){
		Long count = stringRedisTemplate.opsForZSet().count(recordPrefix+phone, DateUtils.yesterday().getTime(), Long.MAX_VALUE);
		logger.info("isRecordPhoneMoreThan5 ? phone is:"+phone +"  count is :"+count);
		if (count>=5) {
			return true;
		}
		return false;
	}
	
	public Result checkCode(String telephone, String code) throws Exception{
		logger.info("telephone is "+telephone);
		logger.info("code is "+code);
//		String cached_code = stringRedisTemplate.opsForValue().get(key_pre+telephone);
//		if  (null == cached_code)  {
//			return Result.fail("验证码过期");
//		}
//		logger.info("cached_code is : "+cached_code);
//		if (code.equals(cached_code)) {
//			return Result.ok();
//		}
		Boolean isRight = smsDao.checkCode(telephone, code);
		if (isRight) {
			return Result.ok();
		}
		return Result.fail("验证码错误");
	}
	
	public Result getPhoneCheckCode(String telephone){
		String cached_code = stringRedisTemplate.opsForValue().get(key_pre+telephone);
		if  (null == cached_code)  {
			return Result.fail("验证码过期");
		}
		logger.info("cached_code is : "+cached_code);
		
		return Result.ok(cached_code);
		
	}
	
	public void sendToRomotApi(String telephone,String code) throws Exception{
		InputStream stream = this.getClass().getResourceAsStream("/template/send_message_tmplate.xml");
		String template = StringHelper.streamToString(stream, "utf-8");
		logger.info("template is "+template);
		String xml = template.replace("${mobileNum}", telephone);
		xml = xml.replace("${checkCode}", code);
		//Request.Post("http://sms.9hgame.com/JHSMSService.asmx");
		 
		//Request.Post("http://sms.9hgame.com/JHSMSService.asmx").bodyString(xml, null).execute().returnContent();
		
	
		logger.info(xml);
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod("http://sms.9hgame.com/JHSMSService.asmx"); 
		post.addRequestHeader("Content-Type","text/xml; charset=utf-8");//在头文件中设置转码
		byte[] bytes = xml.getBytes("utf-8");
		InputStream inputStream = new ByteArrayInputStream(bytes, 0, bytes.length);
		RequestEntity requestEntity = new InputStreamRequestEntity(inputStream,bytes.length, "text/xml; charset=utf-8");
		post.setRequestEntity(requestEntity);
		httpClient.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		logger.info("statusCode:"+statusCode);
		for(Header h : headers){
			//log.info(h);
		}
		String result = new String(post.getResponseBodyAsString().getBytes("utf8")); 
		logger.info("send message result: "+result);
		//System.out.println(result);
	}
}
