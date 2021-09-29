<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<h1>회원가입</h1>

	<form action="doJoin" method="POST">
	<!-- get보다 post가 보안성, 생성 가능 분량 면에서 효율적 -->

		<div>
			아이디 : <input autocomplete="off" placeholder="가입하실 아이디를 입력해주세요." name="loginId"
				type="text" />
		</div>
		<!-- autocomplete : 자동 검색 여부 설정 -->
		<div>
			비밀번호 : <input autocomplete="off" placeholder="비밀번호를 입력해주세요." name="loginPw"
				type="text" />
		</div>
		<div>
			비밀번호 확인 : <input autocomplete="off" placeholder="비밀번호를 다시 한 번 입력해주세요." name="loginPwConfirm"
				type="text" />
		</div>
		<div>
			이름 : <input autocomplete="off" placeholder="사용자 이름을 입력해주세요." name="userName"
				type="text" />
		</div>
		<div>
			<button type="submit">가입</button>
			<!-- <input type="submit" value="작성" /> 도 동일한 기능-->
			<a href="../home/main">홈 화면</a>
		</div>
		
	</form>
</body>
</html>