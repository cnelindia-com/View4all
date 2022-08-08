package com.example.viewall.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.HomeCategoryAdapter;
import com.example.viewall.adapters.PopularVideoHomeAdapter;
import com.example.viewall.broadcastrecivers.NetworkReceiver;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.example.viewall.models.databasemodels.TableOfflineModel;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.models.homecategorylist.DataItem;
import com.example.viewall.models.homecategorylist.HomeCategoryResponse;
import com.example.viewall.models.index.IndexResponse;
import com.example.viewall.models.index1.Index1Response;
import com.example.viewall.models.offlineupdate.OfflineDataResponse;
import com.example.viewall.models.popularviedos.PopularVideoResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.SharePrefrancClass;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderView;
import com.tonyodev.fetch2.Download;
import com.tonyodev.fetch2.Error;
import com.tonyodev.fetch2.Fetch;
import com.tonyodev.fetch2.FetchConfiguration;
import com.tonyodev.fetch2.FetchListener;
import com.tonyodev.fetch2.NetworkType;
import com.tonyodev.fetch2.Priority;
import com.tonyodev.fetch2.Request;
import com.tonyodev.fetch2core.DownloadBlock;
import com.tonyodev.fetch2core.Func;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    /*NavigationView navigationView;*/
    TextView tvSports, txtMusic, txtGospel, txtSoap, txtComedy, txtLifestyle, txtCartoons, txtBeauty;
    LinearLayout popularVideoLayoutId;
    ImageView icn_hamburger, img1;
    /*RecyclerView recPopularVideoId;*/
    RecyclerView popularVideoRec, homeCategoryRec;
    PopularVideoHomeAdapter popularVideoHomeAdapter;
    SliderView imageSlider;
    TextView userNameTxtId;
    /*int myImageList[] = {R.drawable.addicon, R.drawable.banner2, R.drawable.banner1};*/

    //List which contain all offline data
    List<TableOfflineModel> listOfflineData;

    ArrayList<DataItem> listCategory;
    HomeCategoryAdapter homeCategoryAdapter;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;
    ArrayList<com.example.viewall.models.popularviedos.DataItem> popularVideoList;

    ProgressDialog progressDialog;

    DatabaseHandler databaseHandler;

    private static final int REQ_CODE = 1;

    //Creating reference variable of fetch
    private Fetch fetch;

    String bannerNameForDownload, bannerUrlForDownload;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Toast.makeText(HomeActivity.this, SharePrefrancClass.getInstance(HomeActivity.this).getPref("baseurl"), Toast.LENGTH_SHORT).show();
        //This is the latest code 08/08/2022

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

//        Toast.makeText(HomeActivity.this, "MAC "+getMacAddress(), Toast.LENGTH_SHORT).show();

        //Checking app is connected to net or not connected returns true if connected
        /*if (isNetworkConnected()){
            Toast.makeText(HomeActivity.this, "Connected to Internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(HomeActivity.this, "you are offline", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, DownloanActivity.class));
        }*/

        //Saving the current page name in the prefrence
        /*SharePrefrancClass.getInstance(HomeActivity.this).savePref("fromActivity",
                "index_app_page");*/

        //Configuring fetch
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .setDownloadConcurrentLimit(5)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        //Code for create folder
        File dir = new File(Environment.getExternalStorageDirectory() + "/Download/view4all/");
        dir.mkdirs(); // creates needed dirs

        //code for permission.
        if (ActivityCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            }, REQ_CODE);
        } else {
            /*Toast.makeText(VideoShowActivity.this, "Permission Granted.", Toast.LENGTH_SHORT).show();*/
//                    startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));
            /*callDownload();*/
            //Code for create folder
            /*File dir = new File(Environment.getExternalStorageDirectory() + "/Download/view4all/");
            dir.mkdirs(); // creates needed dirs*/
        }

        databaseHandler = new DatabaseHandler(this);

        //Call get data method for get data from database
        /*databaseHandler.getAllVideoData();
        List<VideoModel> data = databaseHandler.getAllVideoData();*/
        /*Toast.makeText(HomeActivity.this, data.get(0).getVideoUrl(), Toast.LENGTH_SHORT).show();*/

        //Code to get the data from offline table
        listOfflineData = databaseHandler.getAddOffTableData();

        NetworkReceiver networkReceiver = new NetworkReceiver();
        registerReceiver(networkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle(null);
        toolbar.setSubtitle("");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        imageSlider = findViewById(R.id.imageSlider);
        /*imageSlider.setSliderAdapter(new HomeAddSliderAdapter(myImageList, HomeActivity.this));
        imageSlider.startAutoCycle();*/


        userNameTxtId = findViewById(R.id.userNameTxtId);
        userNameTxtId.setText(SharePrefrancClass.getInstance(HomeActivity.this).getPref("fname"));

        /*recPopularVideoId = findViewById(R.id.recPopularVideoId);*/
        popularVideoRec = findViewById(R.id.popularVideoRec);

        homeCategoryRec = findViewById(R.id.homeCategoryRec);

        tvSports = findViewById(R.id.tvSports);
        txtMusic = findViewById(R.id.txtMusic);
        txtGospel = findViewById(R.id.txtGospel);
        txtSoap = findViewById(R.id.txtSoap);
        txtComedy = findViewById(R.id.txtComedy);
        txtLifestyle = findViewById(R.id.txtLifestyle);
        txtCartoons = findViewById(R.id.txtCartoons);
        txtBeauty = findViewById(R.id.txtBeauty);

        popularVideoLayoutId = findViewById(R.id.popularVideoLayoutId);
        icn_hamburger = findViewById(R.id.icn_hamburger);
        img1 = findViewById(R.id.img1);


        /*int popularImages[] = {R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5};*/


        imageSlider.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Toast.makeText(HomeActivity.this, view.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        /*popularVideoHomeAdapter = new PopularVideoHomeAdapter(getApplicationContext(), popularImages);
        popularVideoRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        popularVideoRec.setAdapter(popularVideoHomeAdapter);*/

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, VideoShowActivity.class));
            }
        });

        txtSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtComedy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtLifestyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtCartoons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        tvSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        txtGospel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SportsActivity.class));
            }
        });

        popularVideoLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, VideoShowActivity.class));
            }
        });

        icn_hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of Popupmenu
                PopupMenu popupMenu = new PopupMenu(HomeActivity.this, view);
                //Inflating the Popup using xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.term_item) {
                            startActivity(new Intent(HomeActivity.this, TermConditionsActivity.class));
                        } else if (item.getItemId() == R.id.contact_item) {
                            startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
                        }
                        return true;
                    }
                });
                popupMenu.show(); //showing popup menu
            }

        });

//        setSupportActionBar(binding.appBarHome.toolbar);


        //Calling index api.
        callIndexApi();


        //Calling category list api for get the list in home page
        callCategoryList();

        //Calling banner api.
        callBannerListApi();

        //Calling popular api.
        callPopularVideoApi();

        //OFFLINE
        //Calling api for send offline data to server
        callSendOfflineDataApi(listOfflineData);

    }

    private void callBannerListApi() {
        progressDialog.show();

        Call<BannerResponse> call = RetrofitClient.getInstance().getMyApi().bannerList(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    bannerList = new ArrayList<>();
                    bannerList.addAll(response.body().getData());
                    imageSlider.setSliderAdapter(new HomeAddSliderAdapter(bannerList, HomeActivity.this));
                    imageSlider.startAutoCycle();


                    //Code for hit api in for loop
                    String bannerUrl = "";
                    String tempStr = "";
                    for (int i = 0; i < bannerList.size()/*2*/; i++) {
                        tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
                        /*bannerUrl = bannerUrl + ", " + tempStr;*/
                        //Calling index1 api
                        callIndex1Api(tempStr);

                        bannerNameForDownload = tempStr;
                        bannerUrlForDownload = bannerList.get(i).getImageUrl();

                        //Call the download function for download the banner image
                        callBannerDownload(tempStr, bannerList.get(i).getImageUrl());

                        //Below code for create new folder in the download directory
                        /*String folder_main = "AddBanners";
                        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        String fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + folder_main + "/" + tempStr  *//*+ ".mp4"*//* *//*strVideoName*//*;
                        //Calling method for download banner list image
                        callBannerDownloadNew(new Request(bannerList.get(i).getImageUrl(),
                                        fileToDownload),
                                tempStr, bannerList.get(i).getImageUrl());*/

                    }


                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callPopularVideoApi() {
        progressDialog.show();

        Call<PopularVideoResponse> call = RetrofitClient.getInstance().getMyApi().popularVideos(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<PopularVideoResponse>() {
            @Override
            public void onResponse(Call<PopularVideoResponse> call, Response<PopularVideoResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    popularVideoList = new ArrayList<>();
                    popularVideoList.addAll(response.body().getData());

                    popularVideoHomeAdapter = new PopularVideoHomeAdapter(getApplicationContext(), popularVideoList);
                    popularVideoRec.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false));
                    popularVideoRec.setAdapter(popularVideoHomeAdapter);
                }
            }

            @Override
            public void onFailure(Call<PopularVideoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callIndex1Api(String imageName) {
        /*String bannerUrl = "";
        String tempStr = "";
        for (int i = 0; i < bannerList.size(); i++) {
            tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
            bannerUrl = bannerUrl + ", " + tempStr;
        }
        Log.d("BANNERURL", bannerUrl);*/

        Call<Index1Response> call = RetrofitClient.getInstance().getMyApi().index1(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("phone_number"),
                imageName,
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Index1Response>() {
            @Override
            public void onResponse(Call<Index1Response> call, Response<Index1Response> response) {
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                    Log.d("index1res", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Index1Response> call, Throwable t) {
                /*Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();*/
                Log.d("index1fail", t.getMessage());
            }
        });
    }

    //Test method for send offline data to api
    private void callSendOfflineDataApi(List<TableOfflineModel> list) {
        progressDialog.show();

        Call<OfflineDataResponse> call = RetrofitClient.getInstance().getMyApi().offlineWatch(
                list /*"Mohit rajawat test api test"*/);
        call.enqueue(new Callback<OfflineDataResponse>() {
            @Override
            public void onResponse(Call<OfflineDataResponse> call, Response<OfflineDataResponse> response) {
                if (response.body() != null) {
                    Log.d("offlineapires", response.body().getMessage());
                    /*Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();*/
                    databaseHandler.removeTableData();
                }
            }

            @Override
            public void onFailure(Call<OfflineDataResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("offlineapifail", t.getMessage());
                /*Toast.makeText(HomeActivity.this, "Fail "+ t.getMessage(), Toast.LENGTH_SHORT).show();*/
                databaseHandler.removeTableData();
            }
        });

    }

    private void callIndexApi() {
        progressDialog.show();

        Call<IndexResponse> call = RetrofitClient.getInstance().getMyApi().index(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("phone_number"),
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<IndexResponse>() {
            @Override
            public void onResponse(Call<IndexResponse> call, Response<IndexResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<IndexResponse> call, Throwable t) {
                progressDialog.dismiss();
                /*Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    private void callCategoryList() {
        progressDialog.show();
        Call<HomeCategoryResponse> call = RetrofitClient.getInstance().getMyApi().homeCategory(
                SharePrefrancClass.getInstance(HomeActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<HomeCategoryResponse>() {
            @Override
            public void onResponse(Call<HomeCategoryResponse> call, Response<HomeCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    listCategory = new ArrayList<>();
                    listCategory.addAll(response.body().getData());
                    //Setting the recycler adapter
                    homeCategoryAdapter = new HomeCategoryAdapter(HomeActivity.this, listCategory);
                    //Setting staggered layout in recyceler view
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    //Setting the adapter of recycler view
                    homeCategoryRec.setAdapter(homeCategoryAdapter);
                    // Setting LayoutManager to RecyclerView
                    homeCategoryRec.setLayoutManager(staggeredGridLayoutManager);

                }
            }

            @Override
            public void onFailure(Call<HomeCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.home, menu);*/
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*int id = item.getItemId();
        Menu menu = navigationView.getMenu();
        if (id == R.id.nav_home) {
            Toast.makeText(HomeActivity.this, "Home clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_category) {
            startActivity(new Intent(HomeActivity.this, CategoryActivity.class));
        } else if (id == R.id.nav_tac) {
            Toast.makeText(HomeActivity.this, "Term and conditions", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_contact) {
            Toast.makeText(HomeActivity.this, "Contact Us", Toast.LENGTH_SHORT).show();
        }*/

        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.hamburger) {
            Toast.makeText(HomeActivity.this, "Menu Item", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    /*Toast.makeText(this, "Request permission method", Toast.LENGTH_SHORT).show();*/
                    /*startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));*/
//                    callDownload();
                } else {
                    finishAffinity();
                }
                break;
        }
    }

    private void callBannerDownload(String bannerName, String bannerUrl) {
        //Below code for create new folder in the download directory
        String folder_main = "AddBanners";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        String fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + folder_main + "/" + bannerName  /*+ ".mp4"*/ /*strVideoName*/;

        final Request request = new Request(bannerUrl, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.addListener(new FetchListener() {
            @Override
            public void onAdded(@NonNull Download download) {
                //This method is called first time when download the file
                Log.d("onAdded:", "OnAdded");

            }

            @Override
            public void onQueued(@NonNull Download download, boolean b) {

            }

            @Override
            public void onWaitingNetwork(@NonNull Download download) {

            }

            @Override
            public void onCompleted(@NonNull Download download) {
                //This method called every time when we download the file
                fetch.removeListener(this);
            }

            @Override
            public void onError(@NonNull Download download, @NonNull Error error, @Nullable Throwable throwable) {

            }

            @Override
            public void onDownloadBlockUpdated(@NonNull Download download, @NonNull DownloadBlock downloadBlock, int i) {

            }

            @Override
            public void onStarted(@NonNull Download download, @NonNull List<? extends DownloadBlock> list, int i) {

            }

            @Override
            public void onProgress(@NonNull Download download, long l, long l1) {

            }

            @Override
            public void onPaused(@NonNull Download download) {

            }

            @Override
            public void onResumed(@NonNull Download download) {

            }

            @Override
            public void onCancelled(@NonNull Download download) {

            }

            @Override
            public void onRemoved(@NonNull Download download) {

            }

            @Override
            public void onDeleted(@NonNull Download download) {

            }
        });

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                /*Toast.makeText(HomeActivity.this, "Successful", Toast.LENGTH_SHORT).show();*/
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload));*/
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload, strVideoId,
                        strVideoTime));*/
                databaseHandler.addBannerData(new TableBannerModel(bannerName,
                        fileToDownload));
                //Below code for check duplicate entry in the bannertable
                /*databaseHandler.checkDuplicate("tablebanner", bannerName, fileToDownload);*/
                Log.d("onResult:", "OnResult");
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                /*Toast.makeText(HomeActivity.this, result.toString(), Toast.LENGTH_SHORT).show();*/
                Log.d("onError:", "OnError");
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        //Saving the current page name in the prefrence
        SharePrefrancClass.getInstance(HomeActivity.this).savePref("fromActivity",
                "http://dev.view4all.tv/index");
    }

}