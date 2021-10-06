package com.sbs.java.am.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

public class ArticleDao {
	
	private Connection con;

	public ArticleDao(Connection con) {
		this.con = con;
	}
	
	public int getTotalCount(Connection con) {

		SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
		sql.append("FROM article");
		
		int totalCount = DBUtil.selectRowIntValue(con, sql);
		
		return totalCount;
	}

	public List<Map<String, Object>> getArticleRows(Connection con, int limitFrom, int itemsInAPage) {

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("ORDER BY id DESC");
		sql.append("LIMIT ?, ?", limitFrom, itemsInAPage);
		
		List<Map<String, Object>> articleRows = DBUtil.selectRows(con, sql);
		
		return articleRows;
	}

	public int doWrite(Connection con, String title, String body, int loginedMemberId) {
		
		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append(", memberId = ?", loginedMemberId);
		
		return DBUtil.insert(con, sql);
	}

}
