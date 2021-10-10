package com.sbs.java.am.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.dto.Member;
import com.sbs.java.am.service.MemberService;

public class MemberController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private MemberService memberService;
	private ArticleController articleController;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;
		this.memberService = new MemberService(con);
		this.articleController = new ArticleController(request, response, con);
	}

	public void join() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
	}

	public void login() throws ServletException, IOException {
		int loginedMemberId = articleController.setLoginedMemberInfo();

		if (loginedMemberId != -1) {
			response.getWriter().append(
					String.format("<script> alert('이미 로그인 중입니다.'); history.back(); </script>"));
			return;
		}
		
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
	}

	public void doJoin() throws IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String userName = request.getParameter("userName");

		boolean isAvailableLoginId = memberService.isAvailableLoginId(loginId);

		if (isAvailableLoginId) {
			response.getWriter().append(
					String.format("<script> alert('%s(은)는 이미 사용 중인 아이디입니다.'); history.back(); </script>", loginId));
			// history.back() : 이전으로 돌아가기
			return;
		}

		memberService.doJoin(loginId, loginPw, userName);

		response.getWriter().append(
				String.format("<script> alert('%s님, 환영합니다!'); location.replace('/AM/home/main'); </script>", userName));
	}

	public void doLogin() throws IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			response.getWriter().append(
					String.format("<script> alert('%s (은)는 존재하지 않는 회원입니다.'); history.back(); </script>", loginId));
			return;
		}

		if (member.loginPw.equals(loginPw) == false) {
			response.getWriter().append(String.format("<script> alert('비밀번호가 일치하지 않습니다.'); history.back(); </script>"));
			return;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginedMemberId", member.id);

		response.getWriter().append(
				String.format("<script> alert('%s님, 환영합니다!'); location.replace('/AM/home/main'); </script>", loginId));
	}

	public void doLogout() throws IOException {
		int loginedMemberId = articleController.setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 상태가 아닙니다.'); history.back(); </script>"));
			return;
		}
		
		HttpSession session = request.getSession();
		session.removeAttribute("loginedMemberId");

		response.getWriter()
				.append(String.format("<script> alert('로그아웃 되었습니다.'); location.replace('/AM/home/main'); </script>"));
		return;
	}

}
