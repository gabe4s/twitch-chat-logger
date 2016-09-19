package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasePanel {

	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final VerticalPanel mainVerticalPanel = new VerticalPanel();
	
	public BasePanel() {
		RootPanel.get("rootPanel").add(getMainVerticalPanel());
		getMainVerticalPanel().addStyleName("mainVerticalPanel");
	}
	
	protected GreetingServiceAsync getGreetingService() {
		return greetingService;
	}
	
	protected VerticalPanel getMainVerticalPanel() {
		return mainVerticalPanel;
	}
}
