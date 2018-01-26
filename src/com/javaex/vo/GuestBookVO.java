package com.javaex.vo;

public class GuestBookVO {

	private int no;
	private String name;
	private String password;
	private String content;
	private String regDate;
	
	public GuestBookVO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public GuestBookVO(String name, String password, String content) {
		super();
		this.name = name;
		this.password = password;
		this.content = content;
	}



	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return regDate;
	}

	public void setDate(String date) {
		this.regDate = date;
	}



	@Override
	public String toString() {
		return "GuestBookVO [no=" + no + ", name=" + name + ", password=" + password + ", content=" + content
				+ ", date=" + regDate + "]";
	}
	
	
	
	
}
