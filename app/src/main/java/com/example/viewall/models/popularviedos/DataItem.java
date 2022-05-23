package com.example.viewall.models.popularviedos;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("description")
	private Description description;

	@SerializedName("active_date")
	private String activeDate;

	@SerializedName("modified_user_id")
	private String modifiedUserId;

	@SerializedName("assigned_user_id")
	private String assignedUserId;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("url_video")
	private String urlVideo;

	@SerializedName("document_name")
	private String documentName;

	@SerializedName("subcategory_id")
	private String subcategoryId;

	@SerializedName("deleted")
	private String deleted;

	@SerializedName("filename")
	private String filename;

	@SerializedName("file_ext")
	private String fileExt;

	@SerializedName("status_id")
	private String statusId;

	@SerializedName("account_id")
	private String accountId;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("name")
	private String name;

	@SerializedName("date_entered")
	private String dateEntered;

	@SerializedName("exp_date")
	private Object expDate;

	@SerializedName("id")
	private String id;

	@SerializedName("file_mime_type")
	private String fileMimeType;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("slug")
	private String slug;

	@SerializedName("status")
	private String status;

	public String getImageUrl(){
		return imageUrl;
	}

	public Description getDescription(){
		return description;
	}

	public String getActiveDate(){
		return activeDate;
	}

	public String getModifiedUserId(){
		return modifiedUserId;
	}

	public String getAssignedUserId(){
		return assignedUserId;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public String getUrlVideo(){
		return urlVideo;
	}

	public String getDocumentName(){
		return documentName;
	}

	public String getSubcategoryId(){
		return subcategoryId;
	}

	public String getDeleted(){
		return deleted;
	}

	public String getFilename(){
		return filename;
	}

	public String getFileExt(){
		return fileExt;
	}

	public String getStatusId(){
		return statusId;
	}

	public String getAccountId(){
		return accountId;
	}

	public String getDateModified(){
		return dateModified;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public String getName(){
		return name;
	}

	public String getDateEntered(){
		return dateEntered;
	}

	public Object getExpDate(){
		return expDate;
	}

	public String getId(){
		return id;
	}

	public String getFileMimeType(){
		return fileMimeType;
	}

	public String getChannelId(){
		return channelId;
	}

	public String getSlug(){
		return slug;
	}

	public String getStatus(){
		return status;
	}
}