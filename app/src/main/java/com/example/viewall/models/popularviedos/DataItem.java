package com.example.viewall.models.popularviedos;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("description")
	private Description description;

	@SerializedName("active_date")
	private String activeDate;

	@SerializedName("url_video")
	private String urlVideo;

	@SerializedName("document_name")
	private String documentName;

	@SerializedName("subcategory_id")
	private String subcategoryId;

	@SerializedName("status_id")
	private String statusId;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("date_entered")
	private String dateEntered;

	@SerializedName("exp_date")
	private Object expDate;

	@SerializedName("id")
	private String id;

	@SerializedName("slug")
	private String slug;

	@SerializedName("channel_name")
	private String channelName;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("modified_user_id")
	private String modifiedUserId;

	@SerializedName("assigned_user_id")
	private String assignedUserId;

	@SerializedName("created_by")
	private String createdBy;

	@SerializedName("deleted")
	private String deleted;

	@SerializedName("filename")
	private String filename;

	@SerializedName("file_ext")
	private String fileExt;

	@SerializedName("account_id")
	private String accountId;

	@SerializedName("date_modified")
	private String dateModified;

	@SerializedName("name")
	private String name;

	@SerializedName("file_mime_type")
	private String fileMimeType;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("status")
	private String status;

	public Description getDescription(){
		return description;
	}

	public String getActiveDate(){
		return activeDate;
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

	public String getStatusId(){
		return statusId;
	}

	public String getCategoryId(){
		return categoryId;
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

	public String getSlug(){
		return slug;
	}

	public String getChannelName(){
		return channelName;
	}

	public String getImageUrl(){
		return imageUrl;
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

	public String getDeleted(){
		return deleted;
	}

	public String getFilename(){
		return filename;
	}

	public String getFileExt(){
		return fileExt;
	}

	public String getAccountId(){
		return accountId;
	}

	public String getDateModified(){
		return dateModified;
	}

	public String getName(){
		return name;
	}

	public String getFileMimeType(){
		return fileMimeType;
	}

	public String getChannelId(){
		return channelId;
	}

	public String getStatus(){
		return status;
	}
}