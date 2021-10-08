package com.sbs.java.am.dao;

import java.sql.Connection;
import java.util.Map;

import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

public class MemberDao {

	private Connection con;

	public MemberDao(Connection con) {
		this.con = con;
	}

	public boolean isAvailableLoginId(String loginId) {
		
		SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		// loginId와 동일한 아이디가 있다면, 'SELECT COUNT(*) > 0' 식이 참이므로 1(true), 없다면 0(false)

		boolean isAvailableLoginId = DBUtil.selectRowBooleanValue(con, sql);
		
		return isAvailableLoginId;
	}

	public void doJoin(String loginId, String loginPw, String userName) {
		
		SecSql sql = SecSql.from("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?", userName);

		DBUtil.insert(con, sql);
	}

	public Map<String, Object> getMemberRowByLoginId(String loginId, String loginPw) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE loginId = ?", loginId);

		Map<String, Object> memberRow = DBUtil.selectRow(con, sql);
		return memberRow;
	}

	public Map<String, Object> getMemberRowByMemberId(int memberId) {
		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM `member`");
		sql.append("WHERE id = ?", memberId);

		Map<String, Object> memberRow = DBUtil.selectRow(con, sql);
		return memberRow;
	}

}
