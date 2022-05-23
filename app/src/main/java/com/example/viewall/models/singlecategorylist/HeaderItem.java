package com.example.viewall.models.singlecategorylist;

import com.google.gson.annotations.SerializedName;

public class HeaderItem{

	@SerializedName("deleted")
	private String deleted;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("name")
	private String name;

	@SerializedName("action")
	private String action;

	@SerializedName("date_entered")
	private String dateEntered;

	@SerializedName("description")
	private Description description;

	@SerializedName("modified_user_id")
	private String modifiedUserId;

	@SerializedName("id")
	private String id;

	@SerializedName("assigned_user_id")
	private String assignedUserId;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("slug")
	private String slug;

	@SerializedName("status")
	private String status;

	public String getDeleted(){
		return deleted;
	}

	public String getDateModified(){
		return dateModified;
	}

	public String getName(){
		return name;
	}

	public String getAction(){
		return action;
	}

	public String getDateEntered(){
		return dateEntered;
	}

	public Description getDescription(){
		return description;
	}

	public String getModifiedUserId(){
		return modifiedUserId;
	}

	public String getId(){
		return id;
	}

	public String getAssignedUserId(){
		return assignedUserId;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public String getSlug(){
		return slug;
	}

	public String getStatus(){
		return status;
	}
}