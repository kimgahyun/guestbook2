package com.bit2016.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bit2016.guestbook.dao.GuestbookDao;
import com.bit2016.guestbook.vo.GuestbookVo;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String actionName = request.getParameter("a");
		if ("form".equals(actionName)) {
			// form 요청에 대한 처리
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/form.jsp");
			rd.forward(request, response);
		} else if ("add".equals(actionName)) {
			// insert 요청에 대한 처리
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setContent(content);
			vo.setPassword(password);

			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);

			response.sendRedirect("/guestbook2/gb");
			
		} else if ("delete".equals(actionName)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");

			GuestbookVo vo = new GuestbookVo();
			vo.setNo(Long.parseLong(no));
			vo.setPassword(password);

			GuestbookDao dao = new GuestbookDao();
			dao.delete(vo);

			response.sendRedirect("/guestbook/gb");
		} else {
			// default action 처리 ( 리스트 처리 )
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();

			// request 범위에 모델데이터 저장
			request.setAttribute("list", list);
			// request.setAttribute( "my-number", new Integer(10) );

			// forwarding( request 연장, request dispatch )
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
