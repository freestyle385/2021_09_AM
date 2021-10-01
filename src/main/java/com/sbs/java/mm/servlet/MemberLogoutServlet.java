package com.sbs.java.mm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/logout")
public class MemberLogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 세션 정보 받아오기 및 로그인 상태 확인
		HttpSession session = request.getSession();
		String sessionLoginId = (String) session.getAttribute("sessionLoginId");
		String sessionUserName = (String) session.getAttribute("sessionUserName");
		
		if (sessionLoginId == null) {
			response.getWriter().append(String.format(
					"<script> alert('로그인 상태가 아닙니다.'); history.back(); </script>"));
			// history.back() : 이전으로 돌아가기
			return;
		}
		
		// 세션 종료
		response.getWriter().append(String.format(
				"<script> alert('%s님이 로그아웃하셨습니다.'); history.back(); </script>", sessionUserName));
		session.invalidate();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
