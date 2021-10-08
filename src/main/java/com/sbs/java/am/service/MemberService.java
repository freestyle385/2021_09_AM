package com.sbs.java.am.service;

import java.sql.Connection;
import java.util.Map;

import com.sbs.java.am.dao.MemberDao;

public class MemberService {

	private Connection con;
	private MemberDao memberDao;

	public MemberService(Connection con) {
		this.con = con;
		this.memberDao = new MemberDao(con);
	}

	public boolean isAvailableLoginId(String loginId) {
		return memberDao.isAvailableLoginId(loginId);
	}

	public void doJoin(String loginId, String loginPw, String userName) {
		memberDao.doJoin(loginId, loginPw, userName);
	}

	public Map<String, Object> getMemberRow(String loginId, String loginPw) {
		return memberDao.getMemberRow(loginId, loginPw);
	}

}
