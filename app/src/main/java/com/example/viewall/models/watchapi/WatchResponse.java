package com.example.viewall.models.watchapi;

import com.google.gson.annotations.SerializedName;

public class WatchResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private String status;

	public Data getData(){
		return data;
	}

	public String getStatus(){
		return status;
	}
}