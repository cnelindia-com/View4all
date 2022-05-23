package com.example.viewall.models.advert;

import com.google.gson.annotations.SerializedName;

public class AdvertResponse{

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