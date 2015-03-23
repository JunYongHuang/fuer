package com.cf.fuer.vote;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6961520654289491891L;

	@Override
	/**
	 * 接收http://www.goalgoal.com.cn/vote/?subject_id=1跳转到vote.php
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath()+"/vote.php");
	}

}
