package model;

import java.io.Serializable;

public class User implements Serializable {
	private int userId;
	private String loginid;
	private String name;
	private String password;

	public User() {
	}

	public User(String loginid, String password) {
		super();
		this.loginid = loginid;
		this.password = password;
	}

	public User(int userId, String loginid, String name, String password) {
		this.userId = userId;
		this.loginid = loginid;
		this.name = name;
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}