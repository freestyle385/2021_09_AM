package com.sbs.java.am.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sbs.java.am.container.Container;
import com.sbs.java.am.dao.ArticleDao;

public class ArticleService {

	private ArticleDao articleDao;
	private Connection con;
	
	public ArticleService(Connection con) {
		this.con = con;
		articleDao = Container.articleDao(con);
	}
	
	public int getTotalCount(Connection con) {
		return articleDao.getTotalCount(con);
	}

	public List<Map<String, Object>> getArticleRows(Connection con, int limitFrom, int itemsInAPage) {
		return articleDao.getArticleRows(con, limitFrom, itemsInAPage);
	}

	public int doWrite(Connection con, String title, String body, int loginedMemberId) {
		return articleDao.doWrite(con, title, body, loginedMemberId);
	}

}
