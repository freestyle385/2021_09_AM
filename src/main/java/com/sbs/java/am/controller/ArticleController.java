package com.sbs.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.dto.Article;
import com.sbs.java.am.dto.Member;
import com.sbs.java.am.service.ArticleService;
import com.sbs.java.am.service.MemberService;

public class ArticleController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private ArticleService articleService;
	private MemberService memberService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;

		articleService = new ArticleService(con);
		memberService = new MemberService(con);
	}

	public void showList() throws ServletException, IOException {
		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int totalPage = articleService.getForPrintListTotalPage(page);
		List<Article> articles = articleService.getForPrintArticles(page);
		
		request.setAttribute("articles", articles);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void showDetail() throws ServletException, IOException {
		int loginedMemberId = setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}

		// 게시물 불러오기
		int id = Integer.parseInt(request.getParameter("id"));
		
		Article article = articleService.getForPrintArticle(id);
		
		if (article == null) {
			response.getWriter().append(
					String.format("<script> alert('해당 게시물은 존재하지 않습니다.'); location.replace('list'); </script>"));
			return;
		}
		
		// 게시물의 memberId로 memberRow 불러오기(작성자 이름 불러오기)
		int memberId = article.memberId;
		Member member = memberService.getMemberById(memberId);
		
		request.setAttribute("article", article);
		request.setAttribute("member", member);
		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}

	public void write() throws ServletException, IOException {
		int loginedMemberId = setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

	public void doWrite() throws IOException {
		int loginedMemberId = setLoginedMemberInfo();

		String title = request.getParameter("title");
		String body = request.getParameter("body");

		int id = articleService.doWrite(title, body, loginedMemberId);

		response.getWriter().append(String
				.format("<script> alert('%d번 글이 생성되었습니다.'); location.replace('list'); </script>", id));
	}

	public void modify() throws IOException, ServletException {
		int loginedMemberId = setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}

		// 게시물 불러오기
		int id = Integer.parseInt(request.getParameter("id"));
		
		Article article = articleService.getForPrintArticle(id);
		
		if (article == null) {
			response.getWriter().append(
					String.format("<script> alert('해당 게시물은 존재하지 않습니다.'); location.replace('list'); </script>"));
			return;
		}
		
		if (loginedMemberId != article.memberId) {
			response.getWriter().append(
					String.format("<script> alert('수정 권한이 없습니다.'); location.replace('list'); </script>"));
			return;
		}
		
		request.setAttribute("article", article);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void doModify() throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String body = request.getParameter("body");

		articleService.doModify(title, body, id);

		response.getWriter().append(String.format(
				"<script> alert('%d번 글이 수정되었습니다.'); location.replace('detail?id=%d'); </script>", id, id));
	}

	public void doDelete() throws IOException {
		int loginedMemberId = setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}

		int id = Integer.parseInt(request.getParameter("id"));
		
		Article article = articleService.getForPrintArticle(id);
		
		if (article == null) {
			response.getWriter().append(
					String.format("<script> alert('해당 게시물은 존재하지 않습니다.'); location.replace('list'); </script>"));
			return;
		}
		
		if (loginedMemberId != article.memberId) {
			response.getWriter().append(
					String.format("<script> alert('삭제 권한이 없습니다.'); location.replace('list'); </script>"));
			return;
		}
		
		articleService.doDelete(id);

		response.getWriter().append(String
				.format("<script> alert('%d번 글이 삭제되었습니다.'); location.replace('list'); </script>", id));
	}

	public int setLoginedMemberInfo() throws IOException {
		// 로그인 상태 확인 및 topBar의 로그인 회원 정보 생성
		HttpSession session = request.getSession();

		boolean isLogined = false;
		int loginedMemberId = -1;
		Member loginedMember = null;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			isLogined = true;

			loginedMember = memberService.getLoginedMemberById(loginedMemberId);
		}

		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("loginedMember", loginedMember);

		return loginedMemberId;
	}

}
