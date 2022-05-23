package com.example.viewall.models.watchapi;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("channel_name")
	private String channelName;

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_id")
	private String contactId;

	@SerializedName("channel_id")
	private String channelId;

	public String getChannelName(){
		return channelName;
	}

	public String getAction(){
		return action;
	}

	public String getId(){
		return id;
	}

	public String getContactId(){
		return contactId;
	}

	public String getChannelId(){
		return channelId;
	}
}