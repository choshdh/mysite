package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDAO;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVO;


@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		
		if(actionName.equals("joinform")) {
			System.out.println("joinform 진입");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
		}else if(actionName.equals("join")) {
			System.out.println("join 진입");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			UserVO vo = new UserVO();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			UserDAO dao = new UserDAO();
			dao.insert(vo);
			WebUtil.redirect(request, response, "/mysite/user?a=joinsuccess"); //DB 에 insert 를 하는 페이지이기 때문에 forward 를 사용 하면 안된다. 중복요청의 가능성이 생기기 때문에....
		}else if(actionName.equals("joinsuccess")) {
			System.out.println("loginform 진입");
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
		}else if(actionName.equals("loginform")) {
			System.out.println("loginform 진입");
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
		}else if(actionName.equals("login")){
			System.out.println("login 진입");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			System.out.println(email + " " + password);
			
			UserDAO dao = new UserDAO();
			UserVO vo = dao.select(email,password);
			
			if(vo == null) {
				System.out.println("로그인 실패");
				WebUtil.redirect(request, response, "/mysite/user?a=loginform&result=fail");
			}else {
				System.out.println("로그인 성공");
				HttpSession session = request.getSession();
				session.setAttribute("authUser", vo);
				
				WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
			}
		}else if(actionName.equals("logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
			
		}else if(actionName.equals("modifyform")){
			System.out.println("modifyform 진입");
			
			HttpSession session = request.getSession(true);
			UserVO authUser = (UserVO) session.getAttribute("authUser");
			
			if(authUser==null) {
				System.out.println("로그인 되지 않은 상태입니다.");
				WebUtil.redirect(request, response, "/mysite/user?a=loginform");
			}else {
				request.setAttribute("userVo", authUser);  //가지고 있는 세션값을 이용하여 클라이언트 요청 페이지에 채워서 보내준다.
				WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
			}
			
		}else if(actionName.equals("modify")) {
			System.out.println("modify 진입");
			
			HttpSession session = request.getSession(true);
			UserVO authUser = (UserVO) session.getAttribute("authUser");
			
			if(authUser==null) {
				System.out.println("로그인 되지 않은 상태입니다.");
				WebUtil.redirect(request, response, "/mysite/user?a=loginform");
			}else {
				int no = authUser.getNo();
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				String gender = request.getParameter("gender");
				UserVO vo = new UserVO(no,name,null,password,gender);
				UserDAO dao = new UserDAO();
				dao.update(vo);
				
				authUser.setName(name);
				authUser.setPassword(password);
				authUser.setGender(gender);
				
				WebUtil.redirect(request, response, "/mysite/main");
			}
			
		}else {
			System.out.println("잘못된 접근입니다.");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
