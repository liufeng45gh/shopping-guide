package com.lucifer.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UserBlock extends BaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String reason;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date startAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date endAt;
	
	private String nickName;
	
	private Integer status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using=DateSerializer.class)
	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonSerialize(using=DateSerializer.class)
	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public Boolean getIsBlock(){
		if (null != startAt) {
			if (new Date().before(startAt)) {
				return false;
			}
		}
		
		if (null != endAt) {
			if (new Date().after(endAt)) {
				return false;
			}
		}
		
		return true;
	}
	
	
		
}
