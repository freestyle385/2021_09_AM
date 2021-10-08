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
		// 로그인 상태 확인 및 topBar의 로그인 회원 정보 생성
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

	public void doWrite() {
		// TODO Auto-generated method stub

	}

	public void doModify() {
		// TODO Auto-generated method stub

	}

	public void doDelete() {
		// TODO Auto-generated method stub

	}

	private int setLoginedMemberInfo() throws IOException {
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
