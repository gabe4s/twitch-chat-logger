package com.gwdedb.twitchchatlogger.client;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingPopup extends PopupPanel {

	public LoadingPopup() {
	    setWidget(new Image("loading.gif"));
	    setGlassEnabled(true);
	    center();
	}
}
