package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getAllChannels(AsyncCallback<ArrayList<String>> callback);
	void getChatLogDataFromSearchCriteria(String startDate, String endDate, String channel, String searchText, AsyncCallback<ArrayList<ChatLog>> callback);
	void getDaysAndCountForChannel(String channel, AsyncCallback<LinkedHashMap<String, String>> callback);
	void getChatLogsForChannelAndDate(String channel, String date, AsyncCallback<ArrayList<ChatLog>> callback);
}
