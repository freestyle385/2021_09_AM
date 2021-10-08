package com.sbs.java.am.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
	public int id;
	public LocalDateTime regDate;
	public String loginId;
	public String loginPw;
	public String name;

	public Member() {

	}

	public Member(Map<String, Object> row) {
		this.id = (int) row.get("id");
		this.regDate = (LocalDateTime) row.get("regDate");
		this.loginId = (String) row.get("loginId");
		this.loginPw = (String) row.get("loginPw");
		this.name = (String) row.get("name");
	}
}
