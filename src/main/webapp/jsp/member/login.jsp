<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 로그인</title>
</head>
<body>
	<h1>회원 로그인</h1>
	<script>
		var LoginForm_submitDone = false;
		// 입력 여부 체크, 비밀번호 일치 여부 체크
		// 다중 클릭으로 인해, 대량의 동일한 submit이 이루어지지 않도록 submit 통제(JoinForm_submitDone)
		function LoginForm_submit(form) {
			if (LoginForm_submitDone) {
				alert('처리 중입니다.');
				return;
			}

			form.loginId.value = form.loginId.value.trim();

			if (form.loginId.value.length == 0) {
				alert('아이디를 입력해주세요.');
				form.loginId.focus();
				return;
			}

			form.loginPw.value = form.loginPw.value.trim();

			if (form.loginPw.value.length == 0) {
				alert('비밀번호를 입력해주세요.');
				form.loginPw.focus();
				return;
			}

			form.submit();
			LoginForm_submitDone = true;
		}
	</script>

	<form action="/AM/s/member/doLogin" method="POST"
		onsubmit="JoinForm_submit(this); return false;">
		<!-- submit 버튼의 form 전송기능을 막아놓고(return false;) JoinForm_submit 메소드 실행 -->
		<!-- get보다 post가 보안성, 생성 가능 분량 면에서 효율적 -->

		<div>
			아이디 : <input placeholder="아이디를 입력해주세요." name="loginId" type="text" />
		</div>
		<!-- autocomplete="off" : 자동 검색 끄기-->
		<!-- autocomplete이 켜져있을 경우, history.back()을 하면 기 입력된 내용이 저장되어 있음-->
		<div>
			비밀번호 : <input placeholder="비밀번호를 입력해주세요." name="loginPw"
				type="password" />
		</div>
		<div>
			<button type="submit">로그인</button>
			<!-- <input type="submit" value="작성" /> 도 동일한 기능-->
			<button type="button">
				<!-- button type 입력 필수(디폴트인 form 전송을 막기 위해) -->
				<a href="/AM/home/main">취소</a>
			</button>

		</div>

	</form>

	<style type="text/css">
a {
	color: inherit;
	text-decoration: none;
}
</style>
</body>
</html>