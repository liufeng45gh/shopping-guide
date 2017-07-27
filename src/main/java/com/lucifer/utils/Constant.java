package com.lucifer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Constant {

	public static final int PAFENO = 1;
	
	public static final int PAGESIZE = 20;


	
	public static Date firstOnlineDate = null;

	public static final String KEY_RESULT_MESSAGE = "KEY_RESULT_MESSAGE";

	//生图验证码
	public static final String KAPTCHA_SESSION_KEY = "KAPTCHA_SESSION_KEY";

	//cms user key
	public static final String KEY_CMS_USER = "KEY_CMS_USER";

	//输出结果颜色
	public static final String KEY_RESULT_MESSAGE_COLOR = "KEY_RESULT_MESSAGE_COLOR";
	
	static{
		try {
			firstOnlineDate = new SimpleDateFormat("yyyy-MM-dd").parse("2015-11-9");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final String defaultAvatar = "/web/images/default-avatar.jpg";


	public static final String CACHE_KEY_PRAISE_APPRECIATE_PRE = "VOTE:PERSISTENCE:PRAISE:APPRECIATE:";

	public static final String CACHE_KEY_PERSISTENCE_TOKEN_PRE = "VOTE:PERSISTENCE:TOKEN:";

	public static final String CACHE_KEY_PERSISTENCE_READ_APPRECIATE = "VOTE:PERSISTENCE:READ:APPRECIATE:";

	public static final String CACHE_KEY_PERSISTENCE_MESSAGE_NEW_COUNT = "VOTE:PERSISTENCE:MESSAGE:NEW:COUNT:";

	public static final String CACHE_KEY_GET_APPRECIATE = "VOTE:CACHE:GET:APPRECIATE:";

	public static final String CACHE_KEY_ALL_APPRECIATE_COUNT = "VOTE:CACHE:ALL:APPRECIATE:COUNT";

	public static final String CACHE_KEY_ALL_VOTE_COUNT = "VOTE:CACHE:ALL:VOTE:COUNT";

	public static final String CACHE_KEY_GET_MEMBET_BY_ID = "VOTE:CACHE:MEMBER:getMemberById:";

	public static final String CACHE_KEY_INDEX_HTML = "VOTE:CACHE:INDEX:HTML:";

	public static final String CACHE_KEY_REMOVE_ALL = "VOTE:CACHE:*";

	public static final Integer LOGIN_TIME_OUT= 3600 * 24 *10;

	


	public static final String KEY_WX_JSAPI_TICKET = "VOTE:KEY-WX-JSAPI-TICKET";


			
			
	
}
