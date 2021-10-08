package com.sbs.java.am.service;

import java.sql.Connection;
import java.util.Map;

import com.sbs.java.am.dao.MemberDao;
import com.sbs.java.am.dto.Member;

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

	public Member getMemberByLoginId(String loginId, String loginPw) {
		return memberDao.getMemberByLoginId(loginId, loginPw);
	}

	public Member getMemberById(int memberId) {
		return memberDao.getMemberById(memberId);
	}
	
	public Member getLoginedMemberById(int loginedMemberId) {
		return memberDao.getLoginedMemberById(loginedMemberId);
	}

}
