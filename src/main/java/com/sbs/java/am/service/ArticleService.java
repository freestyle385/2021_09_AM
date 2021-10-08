package com.sbs.java.am.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sbs.java.am.dao.ArticleDao;
import com.sbs.java.am.dto.Article;

public class ArticleService {
	private Connection con;
	private ArticleDao articleDao;

	public ArticleService(Connection con) {
		this.con = con;
		this.articleDao = new ArticleDao(con);
	}

	public int getItemsInAPage() {
		return 10;
	}

	public int getForPrintListTotalPage(int page) {

		int itemsInAPage = getItemsInAPage();
		// 페이지별 노출되는 게시물 개수

		int totalCount = articleDao.getTotalCount();

		// 게시물의 총 개수
		int totalPage = (int) Math.ceil((double) totalCount / itemsInAPage);
		// 페이지의 총 개수(게시물의 총 개수 / 페이지 별 게시물 개수)

		return totalPage;
	}

	public List<Article> getForPrintArticles(int page) {
		int itemsInAPage = getItemsInAPage();
		// 페이지별 노출되는 게시물 개수
		int limitFrom = (page - 1) * itemsInAPage;
		// 해당 페이지에서의 리스팅 시작점

		List<Article> articles = articleDao.getArticles(limitFrom, itemsInAPage);

		return articles;
	}

	public Article getForPrintArticle(int id) {

		return articleDao.getArticle(id);
	}

	public int doWrite(String title, String body, int loginedMemberId) {
		return articleDao.doWrite(title, body, loginedMemberId);
	}

	public void doModify(String title, String body, int id) {
		articleDao.doModify(title, body, id);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

}
