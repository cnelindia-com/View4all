package com.example.viewall.models.others;

public class StoredAddPathModel {

    int _id;
    String adPath;
    String adName;
    String videoId;

    public StoredAddPathModel(String adPath, String adName, String videoId) {
        this.adPath = adPath;
        this.adName = adName;
        this.videoId = videoId;
    }

    public StoredAddPathModel() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getAdPath() {
        return adPath;
    }

    public void setAdPath(String adPath) {
        this.adPath = adPath;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
