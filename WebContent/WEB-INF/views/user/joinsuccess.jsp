<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "com.javaex.vo.*" %>
<%
	UserVO vo = (UserVO) session.getAttribute("authUser");
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
	
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include><!-- /header -->
		
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include><!-- navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="user">
					
					<p class="jr-success">
						회원가입을 축하합니다.
						<br><br>
						<a href="/mysite/user?a=loginform">로그인하기</a>
					</p>
					
				</div><!-- /user -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include><!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>