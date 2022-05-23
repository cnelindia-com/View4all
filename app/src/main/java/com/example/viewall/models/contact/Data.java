package com.example.viewall.models.contact;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("user_id")
	private String userId;

	@SerializedName("subject")
	private String subject;

	@SerializedName("name")
	private String name;

	@SerializedName("action")
	private String action;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("message")
	private String message;

	public String getUserId(){
		return userId;
	}

	public String getSubject(){
		return subject;
	}

	public String getName(){
		return name;
	}

	public String getAction(){
		return action;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public String getMessage(){
		return message;
	}
}