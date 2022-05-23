package com.example.viewall.models.homecategorylist;

import com.google.gson.annotations.SerializedName;

public class Description{

	@SerializedName("item")
	private String item;

	@SerializedName("nid")
	private String nid;

	@SerializedName("pid")
	private String pid;

	@SerializedName("type")
	private String type;

	@SerializedName("sorder")
	private String sorder;

	@SerializedName("name")
	private String name;

	@SerializedName("other5")
	private Object other5;

	@SerializedName("other4")
	private Object other4;

	@SerializedName("id")
	private String id;

	@SerializedName("category")
	private String category;

	@SerializedName("subcategory")
	private String subcategory;

	@SerializedName("other1")
	private Object other1;

	@SerializedName("device")
	private String device;

	@SerializedName("value")
	private String value;

	@SerializedName("other3")
	private Object other3;

	@SerializedName("other2")
	private Object other2;

	public String getItem(){
		return item;
	}

	public String getNid(){
		return nid;
	}

	public String getPid(){
		return pid;
	}

	public String getType(){
		return type;
	}

	public String getSorder(){
		return sorder;
	}

	public String getName(){
		return name;
	}

	public Object getOther5(){
		return other5;
	}

	public Object getOther4(){
		return other4;
	}

	public String getId(){
		return id;
	}

	public String getCategory(){
		return category;
	}

	public String getSubcategory(){
		return subcategory;
	}

	public Object getOther1(){
		return other1;
	}

	public String getDevice(){
		return device;
	}

	public String getValue(){
		return value;
	}

	public Object getOther3(){
		return other3;
	}

	public Object getOther2(){
		return other2;
	}
}