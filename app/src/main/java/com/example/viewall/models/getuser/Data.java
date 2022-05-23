package com.example.viewall.models.getuser;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("phone")
	private String phone;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public String getPhone(){
		return phone;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}