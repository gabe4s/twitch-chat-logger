package com.gwdedb.twitchchatlogger.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwdedb.twitchchatlogger.client.GreetingService;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	
	private  Connection getNewConnection() throws Exception {
	    Context ctx = new InitialContext();
	    DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/twitchloggerDS");
	    Connection conn = ds.getConnection();
 
        return conn;
	}


	public void savePostToDatabase(String channel, String text, String timestamp, String sender) throws Exception {
		String sql = "INSERT INTO chat_logs (channel, text, timestamp, sender) VALUES (?, ?, ?, ?)";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, channel);
		ps.setString(2, text);
		ps.setString(3, timestamp);
		ps.setString(4, sender);
		ps.executeUpdate();
		
		conn.close();
	}
	
	@Override
	public ArrayList<String> getAllChannels() throws Exception {
		ArrayList<String> channels = new ArrayList<String>();
		String sql = "SELECT channel FROM chat_logs GROUP BY channel";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			channels.add(rs.getString("channel"));
		}
		conn.close();

		return channels;
	}
	
	@Override
	public ArrayList<ChatLog> getChatLogDataFromSearchCriteria(String startDate, String endDate, String channel, String searchText) throws Exception {
		ArrayList<ChatLog> chatLogs = new ArrayList<ChatLog>();
		
		String sql = "SELECT * FROM chat_logs WHERE ((timestamp>=? AND timestamp<=?) OR timestamp like ?)";
		
		if(channel != null && channel.trim().length() > 0) {
			sql += " AND channel=?";
		}
		
		if(searchText != null && searchText.trim().length() > 0) {
			sql += " AND LOWER(text) like LOWER(?)";
		}
		
		sql += " ORDER BY timestamp";
		
		int psIndex = 1;
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(psIndex++, startDate);
		ps.setString(psIndex++, endDate);
		ps.setString(psIndex++, endDate + "%");
		
		if(channel != null && channel.trim().length() > 0) {
			ps.setString(psIndex++, channel);
		}
		
		if(searchText != null && searchText.trim().length() > 0) {
			ps.setString(psIndex++, "%" + searchText + "%");
		}
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			chatLogs.add(new ChatLog(rs.getString("channel"), rs.getString("text"), rs.getString("timestamp"), rs.getString("sender")));
		}
		conn.close();

		return chatLogs;
	}
	
	@Override
	public LinkedHashMap<String, String> getDaysAndCountForChannel(String channel) throws Exception {
		LinkedHashMap<String, String> dayCountMap = new LinkedHashMap<String, String>();
		String sql = "SELECT count(*) as count, timestamp FROM chat_logs WHERE channel=? GROUP BY CAST(timestamp AS DATE) ORDER BY timestamp DESC";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, channel);
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			String day = rs.getString("timestamp").split(" ")[0];
			dayCountMap.put(day, rs.getString("count"));
		}
		conn.close();

		return dayCountMap;
	}
	
	@Override
	public ArrayList<ChatLog> getChatLogsForChannelAndDate(String channel, String date) throws Exception {
		ArrayList<ChatLog> chatLogs = new ArrayList<ChatLog>();
		String sql = "SELECT * FROM chat_logs WHERE channel=? AND timestamp LIKE ? ORDER BY timestamp";
		Connection conn = getNewConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, channel);
		ps.setString(2, date + "%");
		
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			chatLogs.add(new ChatLog(rs.getString("channel"), rs.getString("text"), rs.getString("timestamp").split(" ")[1], rs.getString("sender")));
		}
		conn.close();
		
		return chatLogs;
	}
}
