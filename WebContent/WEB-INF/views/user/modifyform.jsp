<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.*" %>
<%
	UserVO vo = (UserVO) request.getAttribute("userVo");
%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/user.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>
		
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>
		
		<div id="wrapper">
			<div id="content">
				<div id="user">
	
					<form id="join-form" name="joinForm" method="get" action="/mysite/user">
					<input type="hidden" name="a" value="modify">
						
						<label class="block-label" for="name">이름</label>
						<input id="name" name="name" type="text" value="<%=vo.getName()%>" />
	
						<label class="block-label" for="email">이메일</label>
						<strong><%=vo.getEmail()%></strong>
						
						<label class="block-label">패스워드</label>
						<input name="password" type="password" value="<%=vo.getPassword()%>" />
						
						<fieldset>
							<legend>성별</legend>
							<% if(vo.getGender().equals("male")){ %>
							<label>여</label> <input type="radio" name="gender" value="female" >
							<label>남</label> <input type="radio" name="gender" value="male" checked="checked">
							<%} else { %>
							<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
							<label>남</label> <input type="radio" name="gender" value="male">
							<%} %>
						</fieldset>
						
						<input type="submit" value="수정완료">
						
					</form>
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>
		
	</div> <!-- /container -->

</body>
</html>
