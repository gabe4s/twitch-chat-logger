package com.gwdedb.twitchchatlogger.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ChatLog implements IsSerializable{

	private String channel;
	private String text;
	private String timestamp;
	private String sender;
	
	private ChatLog() {}
	
	public ChatLog(String channel, String text, String timestamp, String sender) {
		setChannel(channel);
		setText(text);
		setTimestamp(timestamp);
		setSender(sender);
	}

	public String getChannel() {
		return channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getSender() {
		return sender;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}
}
