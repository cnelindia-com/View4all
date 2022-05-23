package com.example.viewall.models.offlineupdate;

import com.google.gson.annotations.SerializedName;

public class OfflineDataResponse{

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