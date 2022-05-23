package com.example.viewall.models.singlecategorylist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SingleCategoryResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("header")
	private List<HeaderItem> header;

	@SerializedName("status")
	private String status;

	public List<DataItem> getData(){
		return data;
	}

	public List<HeaderItem> getHeader(){
		return header;
	}

	public String getStatus(){
		return status;
	}
}