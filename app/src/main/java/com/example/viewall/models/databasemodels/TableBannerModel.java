package com.example.viewall.models.databasemodels;

public class TableBannerModel {

    int _id;
    String name;
    String bannerUrl;

    public TableBannerModel() {
    }

    public TableBannerModel(String name, String bannerUrl) {
        this.name = name;
        this.bannerUrl = bannerUrl;
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

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
