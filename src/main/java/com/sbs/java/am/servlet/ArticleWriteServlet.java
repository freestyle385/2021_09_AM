package com.sbs.java.am.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/article/write")
public class ArticleWriteServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		// 세션 정보 받아오기 및 로그인 상태 확인
		HttpSession session = request.getSession();
		String sessionLoginId = (String) session.getAttribute("sessionLoginId");

		if (sessionLoginId == null) {
			response.getWriter().append(String.format("<script> alert('로그인 후 이용해주세요.'); history.back(); </script>"));
			// history.back() : 이전으로 돌아가기
			return;
		}

		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
