package com.gwdedb.twitchchatlogger.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

public class ChatSearchPanel extends BasePanel {
	
	private final LoadingPopup loadingPopup = new LoadingPopup();

	public ChatSearchPanel() {
		super();
	}
	
	public void init() {
		loadingPopup.show();
		getGreetingService().getAllChannels( new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				loadingPopup.hide();
				Window.alert("Could not get channels. Please try again...");
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				loadingPopup.hide();
				drawUI(result);
			}});
	}

	private void drawUI(ArrayList<String> channelList) {
		Label channelHeader = new Label("Channel");
		channelHeader.addStyleName("headerLabel");
		final ListBox channelListBox = new ListBox();
		channelListBox.setStyleName("inputBox");
		channelListBox.addItem("ANY", "");
		getMainVerticalPanel().add(channelHeader);
		for(final String channel : channelList) {
			channelListBox.addItem(channel);
			Anchor anchor = new Anchor(channel);
			anchor.addStyleName("marginLeft20");
			anchor.setHref("/channel/" + channel);
			getMainVerticalPanel().add(anchor);
		}
		CellTable.Resources tableRes = GWT.create(TableRes.class);
		final CellTable<ChatLog> cellTable = new CellTable<ChatLog>(25, tableRes);
		cellTable.addStyleName("cellTable");
		cellTable.redrawFooters();
		final ListDataProvider<ChatLog> dataProvider = new ListDataProvider<ChatLog>();
		dataProvider.addDataDisplay(cellTable);
	    final SimplePager pager = new SimplePager();
	    pager.setDisplay(cellTable);
	    
		FlexTable searchFlexTable = new FlexTable();
		searchFlexTable.addStyleName("searchFlexTable");
		Label startDateLabel = new Label("Start Date");
		startDateLabel.addStyleName("tableLabel");
		Label endDateLabel = new Label("End Date");
		endDateLabel.addStyleName("tableLabel");
		Label channelLabel = new Label("Channel");
		channelLabel.addStyleName("tableLabel");
		Label searchTextLabel = new Label("Search Text");
		searchTextLabel.addStyleName("tableLabel");
		
		final DateBox startDatePicker = new DateBox();
		startDatePicker.setStyleName("inputBox");
		final DateBox endDatePicker = new DateBox();
		endDatePicker.setStyleName("inputBox");
		
		final TextBox searchTextTextbox = new TextBox();
		searchTextTextbox.setStyleName("inputBox");
		
		startDatePicker.setValue(new Date());
		endDatePicker.setValue(new Date());
		
		startDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		endDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("yyyy-MM-dd")));
		
		int row=0;
		int column=0;
		
		searchFlexTable.setWidget(row, column++, startDateLabel);
		searchFlexTable.setWidget(row, column++, endDateLabel);
		searchFlexTable.setWidget(row, column++, channelLabel);
		searchFlexTable.setWidget(row, column++, searchTextLabel);
		
		row++;
		column=0;
		
		searchFlexTable.setWidget(row, column++, startDatePicker);
		searchFlexTable.setWidget(row, column++, endDatePicker);
		searchFlexTable.setWidget(row, column++, channelListBox);
		searchFlexTable.setWidget(row, column++, searchTextTextbox);
		
		Button searchButton = new Button("Search");
		searchButton.setStyleName("searchButton");
		searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				loadingPopup.show();
			    getGreetingService().getChatLogDataFromSearchCriteria(
			    		startDatePicker.getTextBox().getText(), 
			    		endDatePicker.getTextBox().getText(), 
			    		channelListBox.getSelectedValue(), 
			    		searchTextTextbox.getValue(), 
			    		new AsyncCallback<ArrayList<ChatLog>>() {

					@Override
					public void onFailure(Throwable caught) {
						loadingPopup.hide();
						Window.alert("Could not get chat logs at this time. Please try again..." + caught.getMessage());
					}

					@Override
					public void onSuccess(ArrayList<ChatLog> result) {
			    		loadingPopup.hide();
					    dataProvider.getList().clear();
					    dataProvider.getList().addAll(result);
					    dataProvider.flush();
					    dataProvider.refresh();
					    cellTable.redraw();
					}});
			}
		});
		
		searchFlexTable.setWidget(row, column++, searchButton);
		
	    TextColumn<ChatLog> channelColumn = new TextColumn<ChatLog>() {
	        @Override
	        public String getValue(ChatLog chatLog) {
	          return chatLog.getChannel();
	        }
	      };
	    cellTable.addColumn(channelColumn, "Channel");
	      
	    TextColumn<ChatLog> timestampColumn = new TextColumn<ChatLog>() {
	        @Override
	        public String getValue(ChatLog chatLog) {
	          return chatLog.getTimestamp();
	        }
	      };
	    cellTable.addColumn(timestampColumn, "Time");
	    
	    TextColumn<ChatLog> senderColumn = new TextColumn<ChatLog>() {
	        @Override
	        public String getValue(ChatLog chatLog) {
	          return chatLog.getSender();
	        }
	      };
	    cellTable.addColumn(senderColumn, "Sender");
	    
	    TextColumn<ChatLog> textColumn = new TextColumn<ChatLog>() {
	        @Override
	        public String getValue(ChatLog chatLog) {
	          return chatLog.getText();
	        }
	      };
	    cellTable.addColumn(textColumn, "Text");
	    
		getMainVerticalPanel().add(searchFlexTable);
		getMainVerticalPanel().add(cellTable);
		getMainVerticalPanel().add(pager);
		
	}

}
