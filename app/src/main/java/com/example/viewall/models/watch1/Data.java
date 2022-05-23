package com.example.viewall.models.watch1;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_id")
	private String contactId;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("video_id")
	private String videoId;

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

	public String getVideoId(){
		return videoId;
	}
}