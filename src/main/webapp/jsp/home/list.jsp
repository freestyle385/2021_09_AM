<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 리스트</title>
</head>
<body>
	<h1>게시물 리스트 v1</h1> 
	<!-- 일반 for문 버전 -->	
	<ul>
		<%
		for (int i = 0; i < articleRows.size(); i++) {
			Map<String, Object> articleRow = articleRows.get(i);
		%>

		<li><%=(int) articleRow.get("id")%>번,<%=articleRow.get("regDate")%>,
			<%=(String) articleRow.get("title")%></li>

		<%
		}
		%>
	</ul>

	<h1>게시물 리스트 v2</h1>
	<!-- 향상된 for문 버전 -->
	<ul>
		<%
		for (Map<String, Object> articleRow : articleRows) {
		%>

		<li><%=(int) articleRow.get("id")%>번,<%=articleRow.get("regDate")%>,
			<%=(String) articleRow.get("title")%></li>

		<%
		}
		%>
	</ul>
</body>
</html>