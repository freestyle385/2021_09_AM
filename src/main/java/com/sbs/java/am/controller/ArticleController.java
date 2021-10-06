package com.sbs.java.am.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

public class ArticleController {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection con;
	
	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection con) {
		this.request = request;
		this.response = response;
		this.con = con;
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

		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");

		int totalCount = DBUtil.selectRowIntValue(con, sql);
		// 게시물의 총 개수
		int totalPage = (int) Math.ceil((double) totalCount / itemsInAPage);
		// 페이지의 총 개수(게시물의 총 개수 / 페이지 별 게시물 개수)

		sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);

		List<Map<String, Object>> articleRows = DBUtil.selectRows(con, sql);
		request.setAttribute("articleRows", articleRows);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

}
