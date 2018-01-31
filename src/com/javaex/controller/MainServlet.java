package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUtil;


@WebServlet("/main")
public class MainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("main 진입");
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("페이지를 요청한 ip 주소 : " + request.getLocalAddr());
		System.out.println("접근 포트 : " + request.getLocalPort());
		System.out.println("ip 주소 지역 위치 : " + request.getLocale());
		
		WebUtil.forward(request, response, "/WEB-INF/views/main/index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
