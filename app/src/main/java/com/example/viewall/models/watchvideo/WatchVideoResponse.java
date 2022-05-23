package com.example.viewall.models.watchvideo;

import com.google.gson.annotations.SerializedName;

public class WatchVideoResponse{

	@SerializedName("Message")
	private String message;

	@SerializedName("status")
	private String status;

	public String getMessage(){
		return message;
	}

	public String getStatus(){
		return status;
	}
}