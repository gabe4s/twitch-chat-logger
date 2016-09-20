package com.gwdedb.twitchchatlogger.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChannelServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String entireURL = request.getRequestURL().toString();
    	String requestPath = request.getRequestURI();
    	String channel = request.getPathInfo().substring(1, request.getPathInfo().length());
    	String baseURL = entireURL.substring(0, entireURL.indexOf(requestPath));
    	String redirectURL = baseURL + "?channel=" + channel;
    	response.sendRedirect(redirectURL);
    }
	
}
