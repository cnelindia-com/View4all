package com.example.viewall.models.bannerlist;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image_url")
	private String imageUrl;

	public String getImageUrl(){
		return imageUrl;
	}
}