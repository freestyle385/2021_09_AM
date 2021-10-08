<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=(int) articleRow.get("id")%>번 게시물 상세 조회</title>
</head>
<body>
	<h1><%=(int) articleRow.get("id")%>번 게시물 상세 조회
	</h1>
	
	<%@ include file="../part/topBar.jspf" %>
	
	<div>
		번호 :
		<%=(int) articleRow.get("id")%>
	</div>
	<div>
		게시일 :
		<%=articleRow.get("regDate")%></div>
	<div>
		제목 :
		<%=articleRow.get("title")%></div>
	<div>
		내용 :
		<%=articleRow.get("body")%></div>
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