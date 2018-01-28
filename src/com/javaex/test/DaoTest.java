package com.javaex.test;

import com.javaex.dao.BoardDAO;
import com.javaex.dao.UserDAO;
import com.javaex.vo.BoardVO;
import com.javaex.vo.UserVO;

public class DaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserDAO udao = new UserDAO();
//		udao.insert(new UserVO(2, "김수한무 거북이와 두루미 삼천갑자 동방삭", "su", "1234", "male"));
		udao.delete(26, "1234");

//		BoardDAO bdao = new BoardDAO();
//		bdao.insert(new BoardVO(200,"오이쿠","입력","sysdate",0,26));
		
		
	}

}
