package com.example.viewall.models.watch4;

import com.google.gson.annotations.SerializedName;

public class Watch4Response{

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