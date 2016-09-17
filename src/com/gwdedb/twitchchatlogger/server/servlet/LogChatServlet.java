package com.gwdedb.twitchchatlogger.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwdedb.twitchchatlogger.server.ConfigValues;
import com.gwdedb.twitchchatlogger.server.GreetingServiceImpl;

public class LogChatServlet extends HttpServlet
{

	GreetingServiceImpl service = new GreetingServiceImpl();
	private static final long serialVersionUID = 1L;
	private static final String AUTHENTICATION_TOKEN = ConfigValues.getProperty("AUTHENTICATION_TOKEN");
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String channel = request.getParameter("channel");
        String text = request.getParameter("text");
        String timestamp = request.getParameter("msg_time");
        String sender = request.getParameter("sender");
        String token = request.getParameter("token");
        System.out.println("DEBUG: Post received");
        if(AUTHENTICATION_TOKEN.equals(token)) {
        	try {
				getService().savePostToDatabase(channel, text, timestamp, sender);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }

    private GreetingServiceImpl getService() {
    	return service;
    }
}
