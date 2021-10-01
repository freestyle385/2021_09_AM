package com.sbs.java.mm.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sbs.java.am.Config;
import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {

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
			String inputLoginId = request.getParameter("loginId");
			String inputLoginPw = request.getParameter("loginPw");

			// 아이디 존재 여부 확인
			SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", inputLoginId);
			// loginId와 동일한 아이디가 있다면, 'SELECT COUNT(*) > 0' 식이 참이므로 1(true), 없다면 0(false)

			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(con, sql);

			if (isLoginIdDup == false) {
				response.getWriter().append(String
						.format("<script> alert('%s(은)는 존재하지 않는 아이디입니다.'); history.back(); </script>", inputLoginId));
				// history.back() : 이전으로 돌아가기
				return;
			}

			// 비밀번호 일치 여부 확인
			sql = SecSql.from("SELECT *");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", inputLoginId);

			Map<String, Object> MemberMap = DBUtil.selectRow(con, sql);

			String loginId = (String) MemberMap.get("loginId");
			String loginPw = (String) MemberMap.get("loginPw");
			String userName = (String) MemberMap.get("name");

			if (inputLoginPw.equals(loginPw) == false) {
				response.getWriter()
						.append(String.format("<script> alert('비밀번호가 일치하지 않습니다.'); history.back(); </script>"));
				// history.back() : 이전으로 돌아가기
				return;
			}

			// 세션 생성
			HttpSession session = request.getSession();
			session.setAttribute("sessionLoginId", loginId);
			session.setAttribute("sessionUserName", userName);
			
			
			
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