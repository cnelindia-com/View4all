package com.example.viewall.models.watchmarker;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("ip")
	private String ip;

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_id")
	private String contactId;

	@SerializedName("video_id")
	private String videoId;

	public String getIp(){
		return ip;
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

	public String getVideoId(){
		return videoId;
	}
}