package com.example.viewall.models.databasemodels;

public class VideoModel {

    int _id;
    String name;
    String videoUrl;
    String videoId;
    String videotime;
    String catname;

    public VideoModel(String name, String videoUrl, String videoId, String videotime, String catname) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.videoId = videoId;
        this.videotime = videotime;
        this.catname = catname;
    }

    public VideoModel(String name, String videoUrl, String videoId, String videotime) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.videoId = videoId;
        this.videotime = videotime;
    }

    public VideoModel(String name, String videoUrl, String videoId) {
        this.name = name;
        this.videoUrl = videoUrl;
        this.videoId = videoId;
    }

    public VideoModel() {
    }

    public VideoModel(String name, String videoUrl) {
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public VideoModel(int _id, String name, String videoUrl) {
        this._id = _id;
        this.name = name;
        this.videoUrl = videoUrl;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
