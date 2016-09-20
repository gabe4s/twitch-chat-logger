package com.gwdedb.twitchchatlogger.client;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChannelDatesAndCountPanel extends BasePanel {
	
	public ChannelDatesAndCountPanel() {
		super();
	}
	
	public void init(final String channel) {
		final LoadingPopup loadingPopup = new LoadingPopup();
		loadingPopup.show();
		getGreetingService().getDaysAndCountForChannel(channel, new AsyncCallback<LinkedHashMap<String, String>>() {

			@Override
			public void onFailure(Throwable caught) {
				loadingPopup.hide();
				Window.alert("Could not get day data right now. Please try again...");
			}

			@Override
			public void onSuccess(LinkedHashMap<String, String> result) {
				loadingPopup.hide();
				drawUI(channel, result);
			}});
	}
	
	private void drawUI(final String channel, LinkedHashMap<String, String> dayChannelMap) {
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
		Label channelHeader = new Label("Logs For " + channel);
		channelHeader.addStyleName("headerLabel");
		getMainVerticalPanel().add(channelHeader);
		for(Entry<String, String> entry : dayChannelMap.entrySet()) {
		    String day = entry.getKey();
		    String count = entry.getValue();
		    HorizontalPanel hPanel = new HorizontalPanel();
		    final Anchor dayAnchor = new Anchor(day);
		    dayAnchor.addStyleName("marginLeft20");
		    dayAnchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					String redirectURL = GWT.getHostPageBaseURL() + "?channel=" + channel + "&date=" + dayAnchor.getText();
					Window.Location.replace(redirectURL);
				}
			});
		    Label countLabel = new Label(" : " + count);
		    hPanel.add(dayAnchor);
		    hPanel.add(countLabel);
		    getMainVerticalPanel().add(hPanel);
		}
	}
}
