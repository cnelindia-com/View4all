package com.example.viewall.models.getuser;

import com.google.gson.annotations.SerializedName;

public class GetUserResponse{

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