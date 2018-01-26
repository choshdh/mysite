<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>mysite</title>
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import><!-- /header -->	
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import> <!-- navigation -->
		
		<div id="content">
			<div id="board">
			<!-- get 방식일때는 action 에 집어넣은 파라미터 값이 무시된다 -->
			<!-- post 방식일때는 action 과 form 둘다 받을 수 있지만 같은 변수명이있다면 action 에 있는 값만 존재하게 된다. -->
				<form class="board-form" method="post" action="/mysite/board">   
					<input type = "hidden" name = "a" value="writing">
					<input type = "hidden" name = "name" value="">
					<table class="tbl-ex">
						<tr>
							<th colspan="2">글쓰기</th>
						</tr>
						<tr>
							<td class="label">제목</td>
							<td><input type="text" name="title" value=""></td>
						</tr>
						<tr>
							<td class="label">내용</td>
							<td>
								<textarea id="content" name="content"></textarea>
							</td>
						</tr>
					</table>
					<div class="bottom">
						<a href="/mysite/board?a=list">취소</a>
						<input type="submit" value="등록">
					</div>
				</form>				
			</div>
		</div>

		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
		
	</div><!-- /container -->
</body>
</html>