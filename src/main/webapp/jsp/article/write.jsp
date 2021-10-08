<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>
	<h1>게시물 작성</h1>

	<form action="/AM/s/article/doWrite" method="POST">
	<!-- get보다 post가 보안성, 생성 가능 분량 면에서 효율적 -->

		<div>
			제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title"
				type="text" />
		</div>
		<!-- autocomplete : 자동 검색 여부 설정 -->
		<div>
			내용 :
			<textarea autocomplete="off" placeholder="내용을 입력해주세요." name="body" /></textarea>
		</div>
		<div>
			<button type="submit">작성</button>
			<!-- <input type="submit" value="작성" /> 도 동일한 기능-->
			<a href="/AM/s/article/list">리스트</a>
		</div>
		
	</form>

</body>
</html>