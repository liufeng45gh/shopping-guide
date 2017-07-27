package com.lucifer.model;

/**
 * Created by Administrator on 2016/6/26.
 */
public class AccessToken {

    private Long userId;

    private String token;

    private String code;

    private Integer codeLogin;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCodeLogin() {
        return codeLogin;
    }

    public void setCodeLogin(Integer codeLogin) {
        this.codeLogin = codeLogin;
    }
}
