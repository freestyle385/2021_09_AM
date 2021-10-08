<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.sbs.java.am.dto.Article"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Article> articles = (List<Article>) request.getAttribute("articles");
int currentPage = (int) request.getAttribute("page");
int totalPage = (int) request.getAttribute("totalPage");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>

	<h1>게시물 리스트</h1>
	
	<%@ include file="../part/topBar.jspf" %>
	
	<div>
		<a href="/AM/s/article/write">글쓰기</a>
		<a href="/AM/home/main">홈 화면</a>
	</div>
	
	
	<!-- 향상된 for문 버전 -->
	<table border="1">
		<!-- 	<colgroup>
		<col width="100"/>
		<col width="200"/>
		<col/>
		</colgroup> -->
		<thead>
			<tr>
				<th>번호</th>
				<th>날짜</th>
				<th>제목</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (Article article : articles) {
			%>
			<tr>
				<td><%=article.id%></td>
				<td><%=article.regDate%></td>
				<td><a href="/AM/s/article/detail?id=<%=article.id%>"><%=article.title%></a></td>
				<td><a href="/AM/s/article/modify?id=<%=article.id%>">수정</a></td>
				<td><a href="/AM/s/article/doDelete?id=<%=article.id%>">삭제</a></td>
			</tr>
			<%
			}
			%>
		</tbody>

	</table>

	<style type="text/css">
.page>a.red {
	color: red;
}

a {
	color: inherit;
	text-decoration: none;
}

a:hover {
	color: blue;
	text-decoration: underline;
}
</style>
	
	<div class="page">
		
		<%if (currentPage > 1) { %>
		<a href="/AM/s/article/list?page=1">◀</a>
		<%}%>
		
		<%
		int pageMenuSize = 10;
		int from = currentPage - pageMenuSize;
		
		if (from < 1) {
			from = 1;
		}
		
		int end = currentPage + pageMenuSize;
		
		if (end > totalPage) {
			end = totalPage;
		}
		
		for (int i = from; i <= end; i++) {
		%>
		<a class="<%=currentPage == i ? "red" : ""%>" href="/AM/s/article/list?page=<%=i%>"><%=i%></a>
		<%
		}
		%>
		<%if (currentPage < totalPage) { %>
		<a href="/AM/s/article/list?page=<%=totalPage%>">▶</a>
		<%}%>
		
	</div>

</body>
</html>