package com.javaex.vo;

public class BoardVO {

	private int no;
	private String title;
	private String content;
	private String regDate;
	private int hit;
	private int userNo;
	private String name;
	private String email;
	private String password;
	private String gender;
	
	public BoardVO() {
		// TODO Auto-generated constructor stub
	}
	
	

	public BoardVO(int no, String title, String content, String regDate, int hit, int userNo) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.hit = hit;
		this.userNo = userNo;
	}



	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", title=" + title + ", content=" + content + ", regDate=" + regDate + ", hit="
				+ hit + ", userNo=" + userNo + ", name=" + name + ", email=" + email + ", password=" + password
				+ ", gender=" + gender + "]";
	}

	
	
}
