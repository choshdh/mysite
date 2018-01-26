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
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th width="10%">번호</th>
						<th width="20%">제목</th>
						<th width="20%">글쓴이</th>
						<th width="10%">조회수</th>
						<th width="25%">작성일</th>
						<th width="15%">&nbsp;</th>
					</tr>	
					<!-- jstl -->
					<c:forEach items="${requestScope.l }" var="bvo">
						<tr>
							<td>${bvo.no }</td>
							<td><a href="/mysite/board?a=view&bno=${bvo.no }">${bvo.title }</a></td>
							<td>${bvo.name }</td>
							<td>${bvo.hit }</td>
							<td>${bvo.regDate }</td>
							<c:choose>
								<c:when test="${sessionScope.authUser.no == bvo.userNo }"> <!-- 로그인 사용자의 번호와 게시물 작성자 번호가 같다면 -->
									<td><p align="center"><a href="/mysite/board?a=delete&bno=${bvo.no }" class="del">삭제</a></p></td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						
						
						<!-- status의 count 값은 1부터 시작하지만 -->
						<!-- status의 index 값은 begin값이 시작값으로 정해진다고한다. -->
					 	<!-- begin 값으로 보여줄 최소 페이지값 을 넣고 end 값으로 보여줄 최대 페이지 값을 넣어 반복 횟수를 지정했다.-->
				 		<c:forEach begin="${requestScope.showMinPage }" end="${requestScope.showMaxPage }" varStatus="status">
				 			
				 			<c:if test="${status.index==requestScope.showMinPage }">
				 				<li><a href="/mysite/board?a=list&selectpage=${requestScope.selectPage-requestScope.movePage}">◀</a></li>
				 			</c:if>
				 		
				 			<c:choose>	
				 				<c:when test="${status.index<=requestScope.pageCount }"><!-- 인덱스 번호가 DB에서 꺼내온 데이터로 만든 최대 페이지 숫자보다 작거나 같을때  -->
				 					<c:choose>
										<c:when test="${status.index==requestScope.selectPage}"><!-- 인덱스 번호가 선택한 페이지라면 select 옵션 추가 -->		
											<li class="selected">${status.index}</li>
										</c:when>
										<c:otherwise><!-- 나머지 경우엔 페이지 이동 할 수 있도록 a 태그로 작성 -->
											<li><a href="/mysite/board?a=list&selectpage=${status.index}">${status.index}</a></li>
										</c:otherwise>
									</c:choose>
				 				</c:when>
				 				
				 				
				 				<c:otherwise><!-- 인덱스 번호가 최대 페이지 숫자보다 클때 -->
				 					<li>${status.index}</li>
				 				</c:otherwise>
				 			</c:choose>
				 			
				 			<c:if test="${status.index==requestScope.showMaxPage }">
				 				<li><a href="/mysite/board?a=list&selectpage=${requestScope.selectPage+requestScope.movePage}">▶</a></li>
				 			</c:if>
				 			
						</c:forEach>
						
					</ul>
				</div>				
				<div class="bottom">
					<c:if test="${!empty authUser }">
						<a href="/mysite/board?a=write" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import><!-- /footer -->
		
	</div><!-- /container -->
</body>
</html>