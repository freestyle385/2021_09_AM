package com.sbs.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.container.Container;
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
		articleService = Container.articleService(con);
	}

	public void actionList() throws ServletException, IOException {

		int page = 1;

		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {

			}
		}

		int itemsInAPage = 30;
		// 페이지별 노출되는 게시물 개수

		int limitFrom = (page - 1) * itemsInAPage;
		// 해당 페이지에서의 리스팅 시작점

		int totalCount = articleService.getTotalCount(con);
		// 게시물의 총 개수

		int totalPage = (int) Math.ceil((double) totalCount / itemsInAPage);
		// 페이지의 총 개수(게시물의 총 개수 / 페이지 별 게시물 개수)

		List<Map<String, Object>> articleRows = articleService.getArticleRows(con, limitFrom, itemsInAPage);

		request.setAttribute("articleRows", articleRows);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);

		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void actionWrite() throws ServletException, IOException {
		
		// 세션 정보 받아오기 및 로그인 상태 확인
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

}
