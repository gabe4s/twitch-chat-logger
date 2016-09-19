package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TwitchChatLogger implements EntryPoint {

	public void onModuleLoad() {
		String channel = Window.Location.getParameter("channel");
		if(channel == null) {
			ChatSearchPanel chatSearch = new ChatSearchPanel();
			chatSearch.init();
		} else {
			String date = Window.Location.getParameter("date");
			if(date == null) {
				ChannelDatesAndCountPanel dayPanel = new ChannelDatesAndCountPanel();
				dayPanel.init(channel);
			} else {
				ChannelAndDateTextLogsPanel channelDatePanel = new ChannelAndDateTextLogsPanel();
				channelDatePanel.init(channel, date);
			}
		}

	}
}
