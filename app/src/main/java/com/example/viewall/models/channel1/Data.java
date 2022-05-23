package com.example.viewall.models.channel1;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("action")
	private String action;

	@SerializedName("banner")
	private String banner;

	@SerializedName("id")
	private String id;

	@SerializedName("contact_id")
	private String contactId;

	public String getAction(){
		return action;
	}

	public String getBanner(){
		return banner;
	}

	public String getId(){
		return id;
	}

	public String getContactId(){
		return contactId;
	}
}