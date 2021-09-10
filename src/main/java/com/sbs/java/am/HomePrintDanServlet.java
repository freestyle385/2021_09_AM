package com.sbs.java.am;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home/printDan")
public class HomePrintDanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");

		String inputedDan = request.getParameter("dan");

		if (inputedDan == null) {
			inputedDan = "1";
		}

		String inputedLimit = request.getParameter("limit");

		if (inputedLimit == null) {
			inputedLimit = "9";
		}

		String inputedColor = request.getParameter("color");

		if (inputedColor == null) {
			inputedColor = "black";
		}

		String inputedBgColor = request.getParameter("background-color");

		if (inputedBgColor == null) {
			inputedBgColor = "white";
		}

		int dan = Integer.parseInt(inputedDan);
		int limit = Integer.parseInt(inputedLimit);
		String color = inputedColor;
		String bgcolor = inputedBgColor;

		response.getWriter().append(String.format("<div style=\"color:%s;background-color:%s;\">%d단</div><br />", color, bgcolor, dan));

		for (int i = 1; i <= limit; i++) {
			response.getWriter()
					.append(String.format("<div style=\"color:%s;background-color:%s;\">%d * %d = %d</div><br />", color, bgcolor, dan, i, dan * i));

		}
	}

}
