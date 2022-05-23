package com.example.viewall.models.watch2;

import com.google.gson.annotations.SerializedName;

public class Watch2Response{

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