package com.lucifer.dao;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/26.
 */
@Service
public class SmsDao  extends IBatisBaseDao {

    public String getCheckCode(String telephone){
        return sqlSession.selectOne("getCheckCode",telephone);
    }

    public Boolean checkCode(String telephone, String code){
        String db_code = this.getCheckCode(telephone);
        if (code.equals(db_code)) {
           return  true;
        }
        return  false;
    }
}
