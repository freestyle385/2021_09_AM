package com.sbs.java.am.container;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.am.controller.ArticleController;
import com.sbs.java.am.dao.ArticleDao;
import com.sbs.java.am.service.ArticleService;

public class Container {

	public static ArticleController articleController(HttpServletRequest request, HttpServletResponse response,
			Connection con) {
		return new ArticleController(request, response, con);
	}

	public static ArticleService articleService(Connection con) {
		return new ArticleService(con);
	}
	
	public static ArticleDao articleDao(Connection con) {
		return new ArticleDao(con);
	}

}
