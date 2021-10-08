package com.sbs.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.service.ArticleService;
import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

public class ArticleController {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;

		articleService = new ArticleService(con);
	}

	public void showList() throws ServletException, IOException {
		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int totalPage = articleService.getForPrintListTotalPage(page);
		List<Map<String, Object>> articleRows = articleService.getForPrintArticleRows(page);

		request.setAttribute("articleRows", articleRows);
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

		Map<String, Object> articleRow = articleService.getForPrintArticleRow(id);
		request.setAttribute("articleRow", articleRow);
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

		response.getWriter()
				.append(String.format("<script> alert('%d번 글이 생성되었습니다.'); location.replace('list'); </script>", id));
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

		Map<String, Object> articleRow = articleService.getForPrintArticleRow(id);
		request.setAttribute("articleRow", articleRow);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void doModify() throws IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String body = request.getParameter("body");

		articleService.doModify(title, body, id);

		response.getWriter().append(String
				.format("<script> alert('%d번 글이 수정되었습니다.'); location.replace('detail?id=%d'); </script>", id, id));
	}

	public void doDelete() throws IOException {
		int loginedMemberId = setLoginedMemberInfo();

		if (loginedMemberId == -1) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		articleService.doDelete(id);
		
		response.getWriter().append(
				String.format("<script> alert('%d번 글이 삭제되었습니다.'); location.replace('list'); </script>", id));
	}

	private int setLoginedMemberInfo() throws IOException {
		// 로그인 상태 확인 및 topBar의 로그인 회원 정보 생성
		HttpSession session = request.getSession();

		boolean isLogined = false;
		int loginedMemberId = -1;
		Map<String, Object> loginedMemberRow = null;

		if (session.getAttribute("loginedMemberId") != null) {
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			isLogined = true;

			loginedMemberRow = articleService.getLoginedMemberRow(loginedMemberId);
		}

		request.setAttribute("isLogined", isLogined);
		request.setAttribute("loginedMemberId", loginedMemberId);
		request.setAttribute("loginedMemberRow", loginedMemberRow);

		return loginedMemberId;
	}

}
