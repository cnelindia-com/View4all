package com.example.viewall.models.track;

import com.google.gson.annotations.SerializedName;

public class TrackResponse{

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