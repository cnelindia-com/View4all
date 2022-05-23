package com.example.viewall.models.seenvideolist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SeenVideoResponse{

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("header")
	private Object header;

	@SerializedName("status")
	private String status;

	public List<DataItem> getData(){
		return data;
	}

	public Object getHeader(){
		return header;
	}

	public String getStatus(){
		return status;
	}
}