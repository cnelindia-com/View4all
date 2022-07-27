package com.example.viewall.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.BannerOfflineModel;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.example.viewall.models.databasemodels.TableOfflineModel;
import com.example.viewall.models.databasemodels.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 19;
    private static final String DATABASE_NAME = "urlListDataBase";
    private static final String TABLE_VIDEOS = "videosurl";
    private static final String TABLE_VIDEOS_ADD = "addvideosurl";
    private static final String TABLE_OFFLINE = "tableoffline";
    private static final String TABLE_BANNER = "tablebanner";
    private static final String TABLE_BANNER_OFFLINE = "tablebanneroffline";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_VIDEO_PATH = "path";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_VIDEOS_TABLE = "CREATE TABLE " + TABLE_VIDEOS +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "name" + " TEXT,"
                + "path" + " TEXT,"
                + "videoid" + " TEXT,"
                + "videotime" + " TEXT,"
                + "catname" + " TEXT"
                + ")";

        String CREATE_VIDEOS_ADD_TABLE = "CREATE TABLE " + TABLE_VIDEOS_ADD +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "adpath" + " TEXT,"
                + "adname" + " TEXT"
                + ")";

        String CREATE_TABLE_OFFLINE = "CREATE TABLE " + TABLE_OFFLINE +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "adstart" + " TEXT,"
                + "videostart" + " TEXT,"
                + "videoid" + " TEXT,"
                + "contactid" + " TEXT,"
                + "datetime" + " TEXT,"
                + "videoend" + " TEXT,"
                + "adend" + " TEXT,"
                + "adname" + " TEXT,"
                + "marker" + " TEXT,"
                + "banneroffline" + " TEXT,"
                + "bannername" + " TEXT,"
                + "referror" + " TEXT,"
                + "page_name" + " TEXT"
                + ")";

        String CREATE_BANNER_TABLE = "CREATE TABLE " + TABLE_BANNER +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "bannerpath" + " TEXT UNIQUE,"
                + "bannername" + " TEXT UNIQUE"
                + ")";

        String CREATE_BANNER_TABLE_OFFLINE = "CREATE TABLE " + TABLE_BANNER_OFFLINE +
                "("
                + "id" + " INTEGER PRIMARY KEY,"
                + "banneroffline" + " TEXT,"
                + "contactid" + " TEXT,"
                + "bannername" + " TEXT"
                + ")";

        //Query for get the channel name
        String answer = "SELECT DISTINCT " + "catname" + " FROM " + TABLE_VIDEOS;

        sqLiteDatabase.execSQL(CREATE_VIDEOS_TABLE);
        sqLiteDatabase.execSQL(CREATE_VIDEOS_ADD_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_OFFLINE);
        sqLiteDatabase.execSQL(CREATE_BANNER_TABLE);
        sqLiteDatabase.execSQL(CREATE_BANNER_TABLE_OFFLINE);

        //Execute query for get the channel name
        sqLiteDatabase.execSQL(answer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_VIDEOS_ADD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANNER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BANNER_OFFLINE);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    //Code to add data in of banner in tablebanneroffline table
    public void addBannerOfflineData(BannerOfflineModel bannerOfflineModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("banneroffline", bannerOfflineModel.getBanneroffline());
        values.put("contactid", bannerOfflineModel.getContactid());
        values.put("bannername", bannerOfflineModel.getBannername());

        //Inserting Row
        db.insert(TABLE_BANNER_OFFLINE, null, values);

        db.close();
    }

    //Code to add new offline data in tableoffline table
    public void addOfflineData(TableOfflineModel tableOfflineModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("adstart", tableOfflineModel.getAdStart());
        values.put("videostart", tableOfflineModel.getVideoStart());
        values.put("videoid", tableOfflineModel.getVideoId());
        values.put("contactid", tableOfflineModel.getContactId());
        values.put("datetime", tableOfflineModel.getDatetime());
        values.put("videoend", tableOfflineModel.getVideoend());
        values.put("adend", tableOfflineModel.getAdend());
        values.put("adname", tableOfflineModel.getAdname());
        values.put("marker", tableOfflineModel.getMarker());
        values.put("banneroffline", tableOfflineModel.getBanneroffline());
        values.put("bannername", tableOfflineModel.getBannername());
        values.put("referror", tableOfflineModel.getReferror());
        values.put("page_name", tableOfflineModel.getPage_name());


        //Inserting Row
        db.insert(TABLE_OFFLINE, null, values);

        db.close();
    }

    //Code to add new data in the tablebanner table
    public void addBannerData (TableBannerModel tableBannerModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("bannerpath", tableBannerModel.getBannerUrl());
        values.put("bannername", tableBannerModel.getName());

        //Inserting Row
        db.insert(TABLE_BANNER, null, values);

        db.close();
    }

    //Code to add new data in the videosurl table
    public void addData(VideoModel videoUrl) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", videoUrl.getName());
        values.put("path", videoUrl.getVideoUrl());
        values.put("videoid", videoUrl.getVideoId());
        values.put("videotime", videoUrl.getVideotime());
        values.put("catname", videoUrl.getCatname());

        //Inserting Row
        db.insert(TABLE_VIDEOS, null, values);
//        Log.d("TESTRETURN" , String.valueOf(db.insert(TABLE_VIDEOS, null, values)));


        /*String vName = values.get("VIDEO_NAME").toString();
        String vPath = values.get("KEY_VIDEO_PATH").toString();
        db.execSQL("INSERT INTO" +
                " videosurl "+
                "VALUES("+vName+"," +vPath+");");*/

        db.close();
    }

    //Code to add new data in the addvideosurl table
    public void addDataToAd(AddVideoModel addVideoModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("adpath", addVideoModel.getAddvideoUrl());
        values.put("adname", addVideoModel.getAddname());


        //Inserting Row
        db.insert(TABLE_VIDEOS_ADD, null, values);

        db.close();
    }

    //Code for get the data from tablebanneroffline
    public List<BannerOfflineModel> getBannerOfflineData() {
        List<BannerOfflineModel> bannerOfflineList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BANNER_OFFLINE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                BannerOfflineModel bannerOfflineModel = new BannerOfflineModel();
                bannerOfflineModel.set_id(Integer.parseInt(cursor.getString(0)));
                bannerOfflineModel.setBanneroffline(cursor.getString(1));
                bannerOfflineModel.setContactid(cursor.getString(2));
                bannerOfflineModel.setBannername(cursor.getString(3));

                //Adding model to list
                bannerOfflineList.add(bannerOfflineModel);
            } while (cursor.moveToNext());
        }

        return bannerOfflineList;
    }

    //Code for get the data from offlinetable
    public List<TableOfflineModel> getAddOffTableData() {
        List<TableOfflineModel> offlineModelList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_OFFLINE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                TableOfflineModel tableOfflineModel = new TableOfflineModel();
                tableOfflineModel.set_id(Integer.parseInt(cursor.getString(0)));
                tableOfflineModel.setAdStart(cursor.getString(1));
                tableOfflineModel.setVideoStart(cursor.getString(2));
                tableOfflineModel.setVideoId(cursor.getString(3));
                tableOfflineModel.setContactId(cursor.getString(4));
                tableOfflineModel.setDatetime(cursor.getString(5));
                tableOfflineModel.setVideoend(cursor.getString(6));
                tableOfflineModel.setAdend(cursor.getString(7));
                tableOfflineModel.setAdname(cursor.getString(8));
                tableOfflineModel.setMarker(cursor.getString(9));
                tableOfflineModel.setBanneroffline(cursor.getString(10));
                tableOfflineModel.setBannername(cursor.getString(11));
                tableOfflineModel.setReferror(cursor.getString(12));
                tableOfflineModel.setPage_name(cursor.getString(13));

                //Adding model to list
                offlineModelList.add(tableOfflineModel);
            } while (cursor.moveToNext());
        }
        return offlineModelList;
    }

    //Code for get the list of ad videos url from addvideosurl
    public List<AddVideoModel> getAllAdVideoData() {
        List<AddVideoModel> addVideoData = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_VIDEOS_ADD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AddVideoModel addVideoModel = new AddVideoModel();
                addVideoModel.set_id(Integer.parseInt(cursor.getString(0)));
                addVideoModel.setAddvideoUrl(cursor.getString(1));
                addVideoModel.setAddname(cursor.getString(2));

                //Adding advideomodel to list
                addVideoData.add(addVideoModel);
            } while (cursor.moveToNext());
        }

        return addVideoData;
    }

    //Code for get the banner data
    public List<TableBannerModel> getBannerData() {
        List<TableBannerModel> tableBannerList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_BANNER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                TableBannerModel tableBannerModel = new TableBannerModel();
                tableBannerModel.set_id(Integer.parseInt(cursor.getString(0)));
                tableBannerModel.setBannerUrl(cursor.getString(1));
                tableBannerModel.setName(cursor.getString(2));

                //Adding videomodel to list
                tableBannerList.add(tableBannerModel);
            } while (cursor.moveToNext());
        }
        return tableBannerList;
    }

    //Code for get the cat name list
    public /*List<String>*/ void getCatData() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VIDEOS, new String[] { KEY_ID,
                        KEY_NAME }, KEY_ID + "=?",
                new String[] { String.valueOf(KEY_ID) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        /*Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));*/

        /*String qury = "SELECT * FROM "+  TABLE_VIDEOS +"GROUP" +"BY " + "catname";*/
        String answer = "SELECT DISTINCT " + "catname" + " FROM " + TABLE_VIDEOS;



    }

    //Code for get the list of videos url and name
    public List<VideoModel> getAllVideoData(){
        List<VideoModel> videosData = new ArrayList<>();

        /*String selectQuery = "SELECT * FROM " + TABLE_VIDEOS + " OrderBy" + "catname" + "ASC ";*/
        String selectQuery = "SELECT * FROM " + TABLE_VIDEOS + " ORDER " +  "BY " + "catname " + "ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()){
            do {
                VideoModel videoModel = new VideoModel();
                videoModel.set_id(Integer.parseInt(cursor.getString(0)));
                videoModel.setName(cursor.getString(1));
                videoModel.setVideoUrl(cursor.getString(2));
                videoModel.setVideoId(cursor.getString(3));
                videoModel.setVideotime(cursor.getString(4));
                videoModel.setCatname(cursor.getString(5));

                //Adding videomodel to list
                videosData.add(videoModel);
            } while (cursor.moveToNext());
        }

        return videosData;
    }

    //Code for remove the complete data from the tableoffline
    public void removeTableData () {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from " + TABLE_OFFLINE);
    }

    //Method for check duplicate value in the table
    public boolean checkDuplicate(String TableName,String strBannerPath, String strBannerName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " +TABLE_BANNER+ " WHERE " + "bannerpath" + "=" + strBannerPath+ "," + "bannername" + "=" + strBannerName;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    // code to get the single video url
    /*public VideoModel getVideoUrl() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_VIDEOS, new String[]{KEY_ID, KEY_NAME,
                        KEY_VIDEO_PATH}, KEY_ID + "=?",
                new String[]{String.valueOf(KEY_ID)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        VideoModel videoModel = new VideoModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return videoModel;
    }*/
}
