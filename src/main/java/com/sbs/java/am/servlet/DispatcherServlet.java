package com.sbs.java.am.servlet;

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
import com.sbs.java.am.controller.ArticleController;
import com.sbs.java.am.controller.MemberController;
import com.sbs.java.am.exception.SQLErrorException;
import com.sbs.java.am.util.DBUtil;
import com.sbs.java.am.util.SecSql;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {

	private ArticleController articleController;

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

			// topBar (모든 요청 진입 전 실행)
			articleController = new ArticleController(request, response, con);
			articleController.setLoginedMemberInfo();

			String requestUri = request.getRequestURI();
			String[] requestUriBits = requestUri.split("/");

			if (requestUriBits.length < 5) {
				response.getWriter().append(
						String.format("<script> alert('잘못된 접근입니다.'); history.back(); </script>"));
				return;
			}

			String controllerName = requestUriBits[3];
			String actionMethodName = requestUriBits[4];

			if (controllerName.equals("article")) {
				ArticleController controller = new ArticleController(request, response, con);

				if (actionMethodName.equals("list")) {
					controller.showList();
				} else if (actionMethodName.equals("detail")) {
					controller.showDetail();
				} else if (actionMethodName.equals("write")) {
					controller.write();
				} else if (actionMethodName.equals("doWrite")) {
					controller.doWrite();
				} else if (actionMethodName.equals("modify")) {
					controller.modify();
				} else if (actionMethodName.equals("doModify")) {
					controller.doModify();
				} else if (actionMethodName.equals("doDelete")) {
					controller.doDelete();
				} else {
					response.getWriter().append(
							String.format("<script> alert('잘못된 접근입니다.'); history.back(); </script>"));
					return;
				}
			} else if (controllerName.equals("member")) {
				MemberController controller = new MemberController(request, response, con);

				if (actionMethodName.equals("join")) {
					controller.join();
				} else if (actionMethodName.equals("login")) {
					controller.login();
				} else if (actionMethodName.equals("doLogout")) {
					controller.doLogout();
				} else if (actionMethodName.equals("doJoin")) {
					controller.doJoin();
				} else if (actionMethodName.equals("doLogin")) {
					controller.doLogin();
				} else {
					response.getWriter().append(
							String.format("<script> alert('잘못된 접근입니다.'); history.back(); </script>"));
					return;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SQLErrorException e) {
			e.getOrigin().printStackTrace();
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