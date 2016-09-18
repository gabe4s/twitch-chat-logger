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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.ListDataProvider;
import com.gwdedb.twitchchatlogger.shared.ChatLog;

public class ChatSearch {
	
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	VerticalPanel mainVerticalPanel = new VerticalPanel();

	public void init() {
		RootPanel.get("rootPanel").add(mainVerticalPanel);

		final CellTable<ChatLog> cellTable = new CellTable<ChatLog>();
		cellTable.setPageSize(30);
		cellTable.redrawFooters();
		final ListDataProvider<ChatLog> dataProvider = new ListDataProvider<ChatLog>();
		dataProvider.addDataDisplay(cellTable);
	    final SimplePager pager = new SimplePager();
	    pager.setDisplay(cellTable);
	    
		FlexTable searchFlexTable = new FlexTable();
		Label startDateLabel = new Label("Start Date");
		Label endDateLabel = new Label("End Date");
		Label channelLabel = new Label("Channel");
		Label searchTextLabel = new Label("Search Text");
		
		final DateBox startDatePicker = new DateBox();
		final DateBox endDatePicker = new DateBox();
		
		final ListBox channelListBox = new ListBox();
		channelListBox.addItem("ANY", "");
		getGreetingService().getAllChannels( new AsyncCallback<ArrayList<String>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not get channels. Please try again...");
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				for(String channel : result) {
					channelListBox.addItem(channel);
				}
			}});
		
		final TextBox searchTextTextbox = new TextBox();
		
		startDatePicker.setValue(new Date());
		endDatePicker.setValue(new Date());
		
		startDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
		endDatePicker.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
		
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
		searchButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
			    getGreetingService().getChatLogDataFromSearchCriteria(
			    		startDatePicker.getValue(), 
			    		endDatePicker.getValue(), 
			    		channelListBox.getSelectedValue(), 
			    		searchTextTextbox.getValue(), 
			    		new AsyncCallback<ArrayList<ChatLog>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Could not get chat logs at this time. Please try again..." + caught.getMessage());
					}

					@Override
					public void onSuccess(ArrayList<ChatLog> result) {
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
	      
	    TextColumn<ChatLog> textColumn = new TextColumn<ChatLog>() {
	        @Override
	        public String getValue(ChatLog chatLog) {
	          return chatLog.getText();
	        }
	      };
	    cellTable.addColumn(textColumn, "Text");
	    
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
	    

	    
		getMainVerticalPanel().add(searchFlexTable);
		getMainVerticalPanel().add(cellTable);
		getMainVerticalPanel().add(pager);
		
	}

	public GreetingServiceAsync getGreetingService() {
		return greetingService;
	}
	
	private VerticalPanel getMainVerticalPanel() {
		return mainVerticalPanel;
	}
}
