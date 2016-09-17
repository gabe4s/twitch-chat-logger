package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TwitchChatLogger implements EntryPoint {

	public void onModuleLoad() {
		ChatSearch chatSearch = new ChatSearch();
		chatSearch.init();

	}
}
