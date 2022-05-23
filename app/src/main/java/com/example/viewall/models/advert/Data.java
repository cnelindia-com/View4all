package com.example.viewall.models.advert;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("action")
	private String action;

	@SerializedName("video")
	private String video;

	@SerializedName("id")
	private String id;

	public String getAction(){
		return action;
	}

	public String getVideo(){
		return video;
	}

	public String getId(){
		return id;
	}
}