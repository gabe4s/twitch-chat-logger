package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	ArrayList<String> getAllChannels() throws Exception;
	ArrayList<ChatLog> getChatLogDataFromSearchCriteria(String startDate, String endDate, String channel, String searchText) throws Exception;
	LinkedHashMap<String, String> getDaysAndCountForChannel(String channel) throws Exception;
	ArrayList<ChatLog> getChatLogsForChannelAndDate(String channel, String date) throws Exception;
}
