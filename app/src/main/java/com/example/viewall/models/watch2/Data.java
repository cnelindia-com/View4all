package com.example.viewall.models.watch2;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("ad_id")
	private String adId;

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_id")
	private String contactId;

	@SerializedName("channel_id")
	private String channelId;

	public String getAdId(){
		return adId;
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