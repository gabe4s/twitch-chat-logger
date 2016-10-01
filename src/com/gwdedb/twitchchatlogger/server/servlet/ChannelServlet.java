package com.gwdedb.twitchchatlogger.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwdedb.twitchchatlogger.server.GreetingServiceImpl;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

public class ChannelServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	private GreetingServiceImpl gsi = new GreetingServiceImpl();
	
	protected String getHtmlForChannelPage(String channel) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append("Logs - " + channel);
		sb.append("</title>\n");
		sb.append("<style>\n");
		sb.append("a:visited {color: blue;}\n");
		sb.append("h2 {color: #45565C;}\n");
		sb.append("p {margin:0px 0px 0px 10px;padding:0;font-size:14px;font-weight:normal;border-collapse:collapse;border-spacing:0px;}\n");
		sb.append("anchor {}\n");
		sb.append("</style>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h3>[<a href=\"/\">Home</a>]</h3>");
		sb.append("<h2>");
		sb.append("Logs by day for " + channel);
		sb.append("</h2>\n");
		try {
			LinkedHashMap<String, String> dateCount = gsi.getDaysAndCountForChannel(channel);
			for(Map.Entry<String, String> entry : dateCount.entrySet()) {
			    String date = entry.getKey();
			    String count = entry.getValue();
			    sb.append("<p><a class=\"anchor\" href=\"/channel/" + channel + "/" + date + "\">" + date + "</a> : " + count + "</p>\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</body>\n");
		sb.append("</html>\n");
		return sb.toString();
	}
	
	protected String getHtmlForChatLogsPage(String channel, String day) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<title>");
		sb.append("Logs - " + channel + " - " + day);
		sb.append("</title>\n");
		sb.append("<style>\n");
		sb.append("body {background-color:#ffffff;}\n");
		sb.append("h2 {color: #45565C;}\n");
		sb.append("a:visited {color: blue;}\n");
		sb.append(":target { background-color: #ffa; -webkit-transition: all 1s linear; }");
		sb.append("table,tr,td {margin:0;padding:0;font-size:14px;font-weight:normal;border-collapse:collapse;border-spacing:0px;}\n");
		sb.append("table {margin:0px 0px 0px 10px;}\n");
		sb.append(".col1 {width:300px;}\n");
		sb.append("</style>\n");
		sb.append("</head>\n");
		sb.append("<body>\n");
		sb.append("<h3>[<a href=\"/\">Home</a>]</h3>");
		sb.append("<h2>");
		sb.append("Logs for <a href=\"/channel/monotonetim\">" + channel + "</a> on " + day);
		sb.append("</h2>\n");
		sb.append("<table>\n");
		try {
			ArrayList<ChatLog> chatLogs = gsi.getChatLogsForChannelAndDate(channel, day);
			int count = 0;
			for(ChatLog chatLog : chatLogs) {
				sb.append("<tr id=\""+count+"\"><td class=\"col1\"><a href=\"#"+count+"\">[" + chatLog.getTimestamp() + "]</a>&nbsp;" + chatLog.getSender() + "</td><td class=\"col2\">" + chatLog.getText() + "</td></tr>\n");
				count += 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("</table>\n");
		sb.append("</body>\n");
		sb.append("</html>");
		return sb.toString();
	}

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    	String entireURL = request.getRequestURL().toString();
    	String requestPath = request.getRequestURI();
    	String path = request.getPathInfo().substring(1, request.getPathInfo().length());
    	String[] splitPath = path.split("/");
    	String html = "";
    	String channel;
    	String day;
    	
    	switch (splitPath.length) {
    	case 0:
    	case 1:
    		if (splitPath[0].length() == 0) {
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
