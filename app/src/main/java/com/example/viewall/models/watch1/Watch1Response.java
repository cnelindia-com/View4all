package com.example.viewall.models.watch1;

import com.google.gson.annotations.SerializedName;

public class Watch1Response{

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