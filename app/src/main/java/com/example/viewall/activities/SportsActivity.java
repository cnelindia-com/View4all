package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.SingleCategoryAdapter;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.channel.ChannelResponse;
import com.example.viewall.models.channel1.Channel1Response;
import com.example.viewall.models.singlecategorylist.DataItem;
import com.example.viewall.models.singlecategorylist.HeaderItem;
import com.example.viewall.models.singlecategorylist.SingleCategoryResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.SharePrefrancClass;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsActivity extends AppCompatActivity {

    LinearLayout idiskLayoutId, soccerlifeLayoutId, soccerLayoutId, cricketLayoutId;
    ImageView img1, imgBack, imgCatHeaderId, toolbarImgId;
    TextView categoryNameId;
    String strCatName, strCatId;
    Intent getData;
    RecyclerView singleCategoryRec;
    ProgressDialog progressDialog;
    ArrayList<DataItem> listData;
    ArrayList<HeaderItem> listHeader;

    /*int myImageList[] = {R.drawable.addicon, R.drawable.banner2, R.drawable.banner1};*/
    SingleCategoryAdapter singleCategoryAdapter;
    SliderView imageSliderCat;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.black));

        //Saving the current page name in the prefrence
        /*SharePrefrancClass.getInstance(SportsActivity.this).savePref("fromActivity",
                "http://dev.view4all.tv/channel/770ac6a1-2cdc-4235-8058-74d618aaaf2f/");*/

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        idiskLayoutId = findViewById(R.id.idiskLayoutId);
        soccerlifeLayoutId = findViewById(R.id.soccerlifeLayoutId);
        soccerLayoutId = findViewById(R.id.soccerLayoutId);
        cricketLayoutId = findViewById(R.id.cricketLayoutId);
        img1 = findViewById(R.id.img1);
        imgBack = findViewById(R.id.imgBack);
        categoryNameId = findViewById(R.id.categoryNameId);

        imgCatHeaderId = findViewById(R.id.imgCatHeaderId);

        singleCategoryRec = findViewById(R.id.singleCategoryRec);

        getData = getIntent();
        strCatName = getData.getStringExtra("catName");
        strCatId = getData.getStringExtra("catId");

        categoryNameId.setText(strCatName);

        imageSliderCat = findViewById(R.id.imageSliderCat);
        toolbarImgId = findViewById(R.id.toolbarImgId);
        /*imageSliderCat.setSliderAdapter(new HomeAddSliderAdapter(myImageList, SportsActivity.this));
        imageSliderCat.startAutoCycle();*/

        //Calling api
        callSingleCategoryList(/*strCatId*/
        SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome"));

        //Calling banner api.
        callBannerListApi();

        //Calling channel api
        callChannel("");

        toolbarImgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SportsActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, HomeActivity.class));
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        idiskLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        soccerlifeLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        soccerLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });

        cricketLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SportsActivity.this, VideoShowActivity.class));
            }
        });
    }

    private void callChannel1Api(String imageName) {
        /*String bannerUrl = "";
        String tempStr = "";
        for (int i=0; i<bannerList.size(); i++){
            tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
            bannerUrl = bannerUrl + ", " + tempStr;
            *//*bannerUrl = bannerUrl + ", " + bannerList.get(i).getImageUrl();*//*
        }
        Log.d("ChannelBANNERURL", bannerUrl);*/

        Call<Channel1Response> call = RetrofitClient.getInstance().getMyApi().channel1(imageName,
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("phone_number"),
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Channel1Response>() {
            @Override
            public void onResponse(Call<Channel1Response> call, Response<Channel1Response> response) {
                if (response.body() != null) {
                    /*Toast.makeText(HomeActivity.this, "Index status:" + response.body().getStatus(), Toast.LENGTH_SHORT).show();*/
                    Log.d("channel1res", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Channel1Response> call, Throwable t) {
                Log.d("channel1fail", t.getMessage());
            }
        });
    }

    private void callBannerListApi(){
        progressDialog.show();

        Call<BannerResponse> call = RetrofitClient.getInstance().getMyApi().bannerList(
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    bannerList = new ArrayList<>();
                    bannerList.addAll(response.body().getData());
                    imageSliderCat.setSliderAdapter(new HomeAddSliderAdapter(bannerList, SportsActivity.this));
                    imageSliderCat.startAutoCycle();

                    //Code for hit api in for loop
                    String bannerUrl = "";
                    String tempStr = "";
                    for (int i = 0; i < bannerList.size(); i++) {
                        tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
                        /*bannerUrl = bannerUrl + ", " + tempStr;*/
                        //Calling channel1 api
                        callChannel1Api(tempStr);
                    }


                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSingleCategoryList(String categoryId) {
        progressDialog.show();

        Call<SingleCategoryResponse> call = RetrofitClient.getInstance().getMyApi().singleCategory(/*strCatId*/
                /*categoryId*/ SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome"),
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<SingleCategoryResponse>() {
            @Override
            public void onResponse(Call<SingleCategoryResponse> call, Response<SingleCategoryResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    listData = new ArrayList<>();
                    listData.addAll(response.body().getData());

                    listHeader = new ArrayList<>();
                    listHeader.addAll(response.body().getHeader());

                    //Show header image
                    Glide.with(imgCatHeaderId)
                            .load(response.body().getHeader().get(0).getDescription().getValue())
                            .into(imgCatHeaderId);

                    singleCategoryAdapter = new SingleCategoryAdapter(SportsActivity.this, listData, listHeader);
                    singleCategoryRec.setLayoutManager(new LinearLayoutManager(SportsActivity.this, RecyclerView.VERTICAL, false));
                    singleCategoryRec.setAdapter(singleCategoryAdapter);

                    /*Toast.makeText(SportsActivity.this, response.body().getData().get(0).getName(), Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onFailure(Call<SingleCategoryResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SportsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        callSingleCategoryList(SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome" ));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        callSingleCategoryList(SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome" ));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Saving the current page name in the prefrence
        /*SharePrefrancClass.getInstance(SportsActivity.this).savePref("fromActivity",
                "http://dev.view4all.tv/channel/770ac6a1-2cdc-4235-8058-74d618aaaf2f/");*/

        SharePrefrancClass.getInstance(SportsActivity.this).savePref("fromActivity",
                "http://dev.view4all.tv/channel/"+ listData.get(0).getChannelId() + "/");
    }

    private void callChannel(String channelId) {
        Call<ChannelResponse> call = RetrofitClient.getInstance().getMyApi().channelRes(
                /*channelId*/ SharePrefrancClass.getInstance(SportsActivity.this).getPref("catIdFromHome"),
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("phone_number"),
                SharePrefrancClass.getInstance(SportsActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<ChannelResponse>() {
            @Override
            public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {
                if (response.body() != null){
                    Log.d("channelres", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<ChannelResponse> call, Throwable t) {
                Log.d("channelfail", t.getMessage());
            }
        });
    }
}