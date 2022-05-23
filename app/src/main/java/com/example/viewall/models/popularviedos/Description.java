package com.example.viewall.models.popularviedos;

import com.google.gson.annotations.SerializedName;

public class Description{

	@SerializedName("image")
	private String image;

	@SerializedName("item")
	private Object item;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("nid")
	private String nid;

	@SerializedName("show")
	private Object show;

	@SerializedName("description")
	private Object description;

	@SerializedName("pid")
	private Object pid;

	@SerializedName("type")
	private Object type;

	@SerializedName("sorder")
	private Object sorder;

	@SerializedName("name")
	private String name;

	@SerializedName("other5")
	private Object other5;

	@SerializedName("other4")
	private Object other4;

	@SerializedName("id")
	private String id;

	@SerializedName("time")
	private String time;

	@SerializedName("category")
	private String category;

	@SerializedName("subcategory")
	private Object subcategory;

	@SerializedName("other1")
	private String other1;

	@SerializedName("device")
	private Object device;

	@SerializedName("value")
	private String value;

	@SerializedName("other3")
	private Object other3;

	@SerializedName("other2")
	private Object other2;

	@SerializedName("info")
	private Object info;

	public String getImage(){
		return image;
	}

	public Object getItem(){
		return item;
	}

	public String getThumb(){
		return thumb;
	}

	public String getNid(){
		return nid;
	}

	public Object getShow(){
		return show;
	}

	public Object getDescription(){
		return description;
	}

	public Object getPid(){
		return pid;
	}

	public Object getType(){
		return type;
	}

	public Object getSorder(){
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

	public String getTime(){
		return time;
	}

	public String getCategory(){
		return category;
	}

	public Object getSubcategory(){
		return subcategory;
	}

	public String getOther1(){
		return other1;
	}

	public Object getDevice(){
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

	public Object getInfo(){
		return info;
	}
}