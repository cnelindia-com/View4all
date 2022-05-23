package com.example.viewall.models.channel;

import com.google.gson.annotations.SerializedName;

public class ChannelResponse{

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