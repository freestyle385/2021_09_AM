<%@ page import="java.util.Map"%>
<%@ page import="com.sbs.java.am.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Article article = (Article) request.getAttribute("article");
Map<String, Object> memberRow = (Map<String, Object>) request.getAttribute("memberRow");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=article.id%>번 게시물 상세 조회</title>
</head>
<body>
	<h1><%=article.id%>번 게시물 상세 조회
	</h1>
	
	<%@ include file="../part/topBar.jspf" %>
	
	<div>
		번호 :
		<%=article.id%>
	</div>
	<div>
		게시일 :
		<%=article.regDate%></div>
	<div>
		작성자 :
		<%=memberRow.get("name")%></div>
	<div>
		제목 :
		<%=article.title%></div>
	<div>
		내용 :
		<%=article.body%></div>
	<div>
		<a href="/AM/s/article/modify?id=${param.id}">수정</a> 
		<a href="/AM/s/article/doDelete?id=${param.id}">삭제</a> 
	</div>


	<style type="text/css">
a {
	color: blue;
	text-decoration: none;
}

a:hover {
	color: blue;
	text-decoration: underline;
}
</style>

</body>
</html>