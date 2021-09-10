<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
int dan = 5;
%>
<%-- 자바 문법이 들어가는 부분을 <% %> 안에서 표현 --%>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구구단 출력</title>
</head>
<body>
	<div>== <%=dan%>단 ==</div>
	
	<%for (int i = 1; i <= 9; i++){%>
	<div><%=dan%> * <%=i%> = <%=dan * i%></div>
	<%};%>
</body>
</html>