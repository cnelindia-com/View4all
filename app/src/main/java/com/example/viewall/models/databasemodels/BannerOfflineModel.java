package com.example.viewall.models.databasemodels;

public class BannerOfflineModel {

    int _id;
    String banneroffline;
    String contactid;
    String bannername;

    public BannerOfflineModel() {
    }

    public BannerOfflineModel(String banneroffline, String contactid, String bannername) {
        this.banneroffline = banneroffline;
        this.contactid = contactid;
        this.bannername = bannername;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getBanneroffline() {
        return banneroffline;
    }

    public void setBanneroffline(String banneroffline) {
        this.banneroffline = banneroffline;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getBannername() {
        return bannername;
    }

    public void setBannername(String bannername) {
        this.bannername = bannername;
    }
}
