<%@ page import="java.util.Map"%>
<%@ page import="com.sbs.java.am.dto.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Article article = (Article) request.getAttribute("article");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=article.id%>번 게시물 수정</title>
</head>
<body>
	<h1><%=article.id%>번 게시물 수정
	</h1>

	<form action="/AM/s/article/doModify" method="post">
		<!-- get보다 post가 보안성, 생성 가능 분량 면에서 효율적 -->
		<%-- <input  name="id" value="<%=Integer.parseInt(request.getParameter("id"))%>" type="hidden"/> --%>
		<input type="hidden" name="id" value="${param.id}" />
		<!-- getParameter를 약식으로 사용 가능 -->

		<div>
			번호 :
			<%=article.id%></div>
		<div>
			게시일 :
			<%=article.regDate%></div>


		<div>
			제목 : <input autocomplete="off" placeholder="수정할 제목을 입력해주세요."
				value="<%=article.title%>" name="title" type="text" />
		</div>
		<!-- autocomplete : 자동 검색 여부 설정 -->
		<div>
			내용 :
			<textarea autocomplete="off" placeholder="수정할 내용을 입력해주세요."
				name="body" /><%=article.body%></textarea>

		</div>
		<div>
			<button type="submit">수정</button>
			<a href="/AM/s/article/list">취소</a>
		</div>
	</form>


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