package com.lucifer.model;

/**
 * Created by liufx on 16/1/11.
 */
public class SearchParam {

    private String account;

    private String phone;

    private String nickName;

    private String level;

    private String sex;

    private String birthday;

    private String city;

    private Integer checkRank;
    
    private Integer page;
    
    private Integer count;

    private String roleId;

    private String status;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCheckRank() {
        return checkRank;
    }

    public void setCheckRank(Integer checkRank) {
        this.checkRank = checkRank;
    }

	public Integer getPage() {
		if (null == page) {
			return 1;
		}
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getCount() {
		if (null == count) {
			return 20;
		}
		return count;
	}

	public void setCount(Integer count) {
		
		this.count = count;
	}
	
	public Integer getOffset(){
		Integer offset = (this.getPage()-1)*this.getCount();
		return offset;
	}

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
