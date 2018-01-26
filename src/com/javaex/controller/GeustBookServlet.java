package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDAO;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVO;


@WebServlet("/gb")
public class GeustBookServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		
		if(actionName.equals("list")){
			System.out.println("list 진입");
			GuestBookDAO dao = new GuestBookDAO();
			List<GuestBookVO> l = dao.selectAllGuestBook();
			request.setAttribute("l", l);
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/list.jsp");
		}else if(actionName.equals("deleteform")) {
			System.out.println("deleteform 진입");
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteform.jsp");
		}else if(actionName.equals("deletefail")) {
			System.out.println("deletefail 진입");
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deletefail.html");
		}else {
			System.out.println("잘못된 접근입니다.");
		}
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String actionName = request.getParameter("a");
		
		if(actionName.equals("add")) {
			System.out.println("add 진입");
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestBookDAO dao = new GuestBookDAO();
			GuestBookVO vo = new GuestBookVO();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);
			dao.insertGuestBook(vo);
			
			WebUtil.redirect(request, response, "/mysite/gb?a=list");
		}else if(actionName.equals("delete")) {
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			GuestBookDAO dao = new GuestBookDAO();
			int result = dao.deleteGuestBook(no, password);
			if(result == 1) {
				WebUtil.redirect(request, response, "/mysite/gb?a=list");
			}else {
				WebUtil.redirect(request,response,"/mysite/gb?a=deletefail");
			}
			
		}else {
			System.out.println("잘못된 접근입니다.");
		}
	}

}
