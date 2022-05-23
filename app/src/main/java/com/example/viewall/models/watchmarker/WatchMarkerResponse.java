package com.example.viewall.models.watchmarker;

import com.google.gson.annotations.SerializedName;

public class WatchMarkerResponse{

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