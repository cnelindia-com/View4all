package com.example.viewall.models.register;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse{

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