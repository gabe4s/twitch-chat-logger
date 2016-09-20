package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

public class ChannelAndDateTextLogsPanel extends BasePanel {

	public ChannelAndDateTextLogsPanel() {
		super();
	}
	
	public void init(final String channel, final String date) {
		final LoadingPopup loadingPopup = new LoadingPopup();
		loadingPopup.show();
		getGreetingService().getChatLogsForChannelAndDate(channel, date, new AsyncCallback<ArrayList<ChatLog>>() {

			@Override
			public void onFailure(Throwable caught) {
				loadingPopup.hide();
				Window.alert("Could not get chat logs at this time. Please try again...");
			}

			@Override
			public void onSuccess(ArrayList<ChatLog> result) {
				loadingPopup.hide();
				drawUI(channel, date, result);
			}});
	}
	
	private void drawUI(String channel, String date, ArrayList<ChatLog> chatLogs) {
		Anchor homeAnchor = new Anchor("Home");
		homeAnchor.setStyleName("homeAnchor");
		homeAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				String redirectURL = GWT.getHostPageBaseURL();
				Window.Location.replace(redirectURL);
			}
		});
		getMainVerticalPanel().add(homeAnchor);
		Label headerLabel = new Label("Logs for " + channel + " on " + date);
		headerLabel.addStyleName("headerLabel");
		getMainVerticalPanel().add(headerLabel);
		int row=0;
		int column=0;
		FlexTable flexTable = new FlexTable();
		for(ChatLog chatLog : chatLogs) {
			Label text = new Label(chatLog.getText());
			Label timestampSender = new Label("[" + chatLog.getTimestamp() + "]  " + chatLog.getSender());
			timestampSender.addStyleName("marginLeft20");
			timestampSender.addStyleName("timestampSenderLabel");
			
			flexTable.setWidget(row, column++,timestampSender);
			flexTable.setWidget(row, column, text);
			
			row++;
			column=0;
		}
		
		getMainVerticalPanel().add(flexTable);

	}
}
