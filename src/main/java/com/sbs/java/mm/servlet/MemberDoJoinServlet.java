package com.sbs.java.mm.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sbs.java.am.Config;
import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// 커넥터 드라이버 활성화
		String driverName = Config.getDBDriverClassName();

		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			System.err.printf("[ClassNotFoundException 예외, %s]\n", e.getMessage());
			response.getWriter().append("DB 드라이버 클래스 로딩 실패");
			return;
		}

		// DB 연결
		Connection con = null;

		try {
			con = DriverManager.getConnection(Config.getDBurl(), Config.getDBId(), Config.getDBPw());
			String loginId = request.getParameter("loginId");
			String loginPw = request.getParameter("loginPw");
			String loginPwConfirm = request.getParameter("loginPwConfirm");
			String userName = request.getParameter("userName");

			SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);
			// loginId와 동일한 아이디가 있다면, 'SELECT COUNT(*) > 0' 식이 참이므로 1(true), 없다면 0(false)

			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(con, sql);

			if (isLoginIdDup) {
				response.getWriter().append(String.format(
						"<script> alert('%s(은)는 이미 사용 중인 아이디입니다.'); location.replace('join'); </script>", loginId));
				return;
			}

			sql = SecSql.from("INSERT INTO `member`");
			sql.append("SET regDate = NOW()");
			sql.append(", loginId = ?", loginId);
			sql.append(", loginPw = ?", loginPw);
			sql.append(", `name` = ?", userName);

			DBUtil.insert(con, sql);
			response.getWriter().append(String
					.format("<script> alert('%s님, 환영합니다!'); location.replace('../home/main'); </script>", userName));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}