package com.lucifer.utils;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ifun on 14-6-13.
 */
public class QQInfo {

    private String avatar;

    private String nick;

    private Integer gender;
    
    static final Logger logger = LoggerFactory.getLogger(QQInfo.class);

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Integer getGender(){ return this.gender; }

    public static QQInfo getQQInfo(String access_token, String openid) throws IOException, JSONException {

        String url="https://graph.qq.com/user/get_user_info?access_token=#{access_token}&oauth_consumer_key=#{oauth_consumer_key}&openid=#{openid}";
        String oauth_consumer_key = "app_ID";
        logger.info("oauth_consumer_key: "+oauth_consumer_key);
        url = url.replace("#{access_token}",access_token);
        url = url.replace("#{oauth_consumer_key}",oauth_consumer_key);
        url = url.replace("#{openid}",openid);

        HttpClient httpClient = new HttpClient();
        GetMethod get = new GetMethod(url);
        httpClient.executeMethod(get);
        Header[] headers = get.getResponseHeaders();
        int statusCode = get.getStatusCode();
        logger.info("statusCode:"+statusCode);
        for(Header h : headers){
            //log.info(h);
        }
        String result = new String(get.getResponseBodyAsString().getBytes("utf8"));
        logger.info("result is :"+ result);
        JSONObject userInfo = new JSONObject(result);
        QQInfo qqInfo = new QQInfo();
        qqInfo.nick =  userInfo.getString("nickname");
        qqInfo.avatar = userInfo.getString("figureurl_qq_2");
        if(StringHelper.isEmpty(qqInfo.avatar)){
            qqInfo.avatar =  userInfo.getString("figureurl_qq_1");
        }
        String gander =  userInfo.getString("gender");
        if("女".equals(gander)){
            qqInfo.gender = 2;
        }else{
            qqInfo.gender = 1;
        }
        return qqInfo;
    }
}
