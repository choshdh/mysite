<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/mysite/assets/css/guestbook.css" rel="stylesheet" type="text/css">
	<title>Insert title here</title>
</head>
<body>

	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->	
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import> <!-- navigation -->
		
		<div id="wrapper">
			<div id="content">
				<div id="guestbook">
					
					<form action="/mysite/gb" method="post">
					<input type="hidden" name="a" value="add">
						
						<table>
							<tr>
								<td>이름</td><td><input type="text" name="name" /></td>
								<td>비밀번호</td><td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td colspan=4><textarea name="content" id="content"></textarea></td>
							</tr>
							<tr>
								<td colspan=4 align=right><input type="submit" VALUE=" 확인 " /></td>
							</tr>
						</table>
					</form>
					
					<!-- jstl 사용 -->
					<c:forEach items="${requestScope.l }" var="gvo">
						<ul>
							<li>
								<table>
									<tr>
										<td>[${gvo.no }]</td>
										<td>${gvo.name }</td>
										<td>${gvo.date }</td>
										<td><a href="/mysite/gb?a=deleteform&no=${gvo.no }">삭제</a></td>
									</tr>
									<tr>
										<td colspan=4>
										${gvo.content }
										</td>
									</tr>
								</table>
								<br>
							</li>
						</ul>
					</c:forEach>
					
				</div><!-- /guestbook -->
			</div><!-- /content -->
		</div><!-- /wrapper -->
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
	</div> <!-- /container -->

</body>
</html>