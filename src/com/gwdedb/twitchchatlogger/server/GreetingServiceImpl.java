package com.gwdedb.twitchchatlogger.server;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwdedb.twitchchatlogger.client.GreetingService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
	private static Connection conn = null;
	private static Connection getConnection() throws Exception {
		if(conn == null) {
	        Context ctx = new InitialContext();
	        DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/twitchloggerDS");
	        conn = ds.getConnection();
		}
 
        return conn;
	}


	public void savePostToDatabase(String channel, String text, String timestamp, String sender) throws Exception {
		String sql = "INSERT INTO chat_logs (channel, text, timestamp, sender) VALUES (?, ?, ?, ?)";
		PreparedStatement ps = getConnection().prepareStatement(sql);
		ps.setString(1, channel);
		ps.setString(2, text);
		ps.setString(3, timestamp);
		ps.setString(4, sender);
		ps.executeUpdate();
	}
}
