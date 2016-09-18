package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void getAllChannels(AsyncCallback<ArrayList<String>> callback);
	void getChatLogDataFromSearchCriteria(AsyncCallback<ArrayList<ChatLog>> callback);
}
