package com.example.viewall.models.register;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("o_phone")
	private String oPhone;

	@SerializedName("o_first_name")
	private String oFirstName;

	@SerializedName("action")
	private String action;

	@SerializedName("o_custom_2")
	private String oCustom2;

	@SerializedName("pindex")
	private String pindex;

	@SerializedName("id")
	private String id;

	@SerializedName("o_last_name")
	private String oLastName;

	@SerializedName("o_custom_1")
	private String oCustom1;

	@SerializedName("email")
	private String email;

	public String getOPhone(){
		return oPhone;
	}

	public String getOFirstName(){
		return oFirstName;
	}

	public String getAction(){
		return action;
	}

	public String getOCustom2(){
		return oCustom2;
	}

	public String getPindex(){
		return pindex;
	}

	public String getId(){
		return id;
	}

	public String getOLastName(){
		return oLastName;
	}

	public String getOCustom1(){
		return oCustom1;
	}

	public String getEmail(){
		return email;
	}
}