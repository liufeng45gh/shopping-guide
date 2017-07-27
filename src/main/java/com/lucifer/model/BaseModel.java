package com.lucifer.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class BaseModel {
	
	// 创建时间（记录）
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	protected  Date createdAt;
	
	// 更新时间（记录）
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	
	protected Date updatedAt;
	

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	

}
