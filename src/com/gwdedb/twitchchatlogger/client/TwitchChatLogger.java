package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TwitchChatLogger implements EntryPoint {

	public void onModuleLoad() {
			ChatSearchPanel chatSearch = new ChatSearchPanel();
			chatSearch.init();
	}
}
