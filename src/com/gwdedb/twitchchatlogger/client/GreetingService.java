package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {

	ArrayList<String> getAllChannels() throws Exception;
	ArrayList<ChatLog> getChatLogDataFromSearchCriteria(Date startDate, Date endDate, String channel, String searchText) throws Exception;
}
