package com.gwdedb.twitchchatlogger.server.servlet;

import java.io.IOException;


import javax.servlet.*;
import javax.servlet.http.*;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import java.io.*;

public class ChannelServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected String getHtmlForMainPage() {
		return "<html><head><title>Hello World!</title></head><body><h1>da main page</h1></body></html>";
	}
	
	protected String getHtmlForChannelPage(String channel) {
		return "<html><head><title>Hello World!</title></head><body><h1>here r the days we have logs for " + channel + ": oct 1, 2016</h1></body></html>";
	}
	
	protected String getHtmlForChatLogsPage(String channel, String day) {
		return "<html><head><title>Hello World!</title></head><body><h1>logs for "+channel+" on "+day+": hi.</h1></body></html>";
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    	String entireURL = request.getRequestURL().toString();
    	String requestPath = request.getRequestURI();
    	String path = request.getPathInfo().substring(1, request.getPathInfo().length());
    	String[] splitPath = path.split("/");
    	String html;
    	String channel;
    	String day;
    	
    	switch (splitPath.length) {
    	case 0:
    	case 1:
    		if (splitPath[0].length() == 0) {
	    		html = getHtmlForMainPage();
    		} else {
	    		channel = splitPath[0];
	    		html = getHtmlForChannelPage(channel);
    		}
    		break;
    	case 2:
    		channel = splitPath[0];
    		day = splitPath[1];
    		html = getHtmlForChatLogsPage(channel, day);
    		break;
		default:
			html = "";
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
    	}
    	out.println(html);
    }
	
}
