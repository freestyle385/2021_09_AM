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
	<h1><%=(int) articleRow.get("id")%>번 게시물 상세 조회</h1>
	
	<div>번호 : <%=(int) articleRow.get("id")%></div>
	<div>게시일 : <%=articleRow.get("regDate")%></div>
	<div>제목 : <%=articleRow.get("title")%></div>
	<div>내용 : <%=articleRow.get("body")%></div>
	<div><a href="list">게시물 리스트로 돌아가기</a></div>
</body>
</html>