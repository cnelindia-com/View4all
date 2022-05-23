package com.example.viewall.models.index;

import com.google.gson.annotations.SerializedName;

public class IndexResponse{

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