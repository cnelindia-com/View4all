package com.example.viewall.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.SeenVideoAdapter;
import com.example.viewall.models.advert.AdvertResponse;
import com.example.viewall.models.bannerlist.BannerResponse;
import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.models.seenvideolist.DataItem;
import com.example.viewall.models.seenvideolist.SeenVideoResponse;
import com.example.viewall.models.singlevideo.SingleVideoResponse;
import com.example.viewall.models.track.TrackResponse;
import com.example.viewall.models.videosmodel.VideoResponse;
import com.example.viewall.models.watch1.Watch1Response;
import com.example.viewall.models.watch2.Watch2Response;
import com.example.viewall.models.watch3.Watch3Response;
import com.example.viewall.models.watch4.Watch4Response;
import com.example.viewall.models.watch5.Watch5Response;
import com.example.viewall.models.watchadvert.WatchAdvertResponse;
import com.example.viewall.models.watchapi.WatchResponse;
import com.example.viewall.models.watchmarker.WatchMarkerResponse;
import com.example.viewall.models.watchvideo.WatchVideoResponse;
import com.example.viewall.serviceapi.RetrofitClient;
import com.example.viewall.utils.CustomVideoView;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.ScalableVideoView;
import com.example.viewall.utils.SharePrefrancClass;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoShowActivity extends AppCompatActivity {

    ImageView imgBack, toolbarImgId;
    Intent getData;
    String strVideoId, strChannelName;
    ProgressDialog progressDialog;
    ProgressBar progressbarVideo;
    ImageView imgDownloadId;
    SliderView imageSlider;

    TextView txtVideoName, txtVideoDuration, txtChannelNameId;

    //Creating reference variable of fetch
    private Fetch fetch;

    String strVideoUrlForDownload;
    String strVideoName, strAddVideoNameUrl, strAddVideoNameToStore, strAddVideoId, strVideoTime, strCategoryId;

    String strDbVideoName;
    String strPhoneNumber;

    SeenVideoAdapter seenVideoAdapter;
    ArrayList<DataItem> listData;


    //Below is working code.
    String fileToDownload /*= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/view4all/" + strVideoName*/;

    private static final int REQ_CODE = 1;

    DatabaseHandler databaseHandler;

    ArrayList<com.example.viewall.models.bannerlist.DataItem> bannerList;

    RecyclerView categoryWatchedRec;

    int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        //Saving the current page name in the prefrence
        /*SharePrefrancClass.getInstance(VideoShowActivity.this).savePref("fromActivity",
                "");*/

        imageSlider = findViewById(R.id.imageSlider);

        databaseHandler = new DatabaseHandler(this);

        /*Toast.makeText(VideoShowActivity.this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString(), Toast.LENGTH_SHORT).show();*/

        //Configuring fetch
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(VideoShowActivity.this)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        progressbarVideo = findViewById(R.id.progressbarVideo);

        imgDownloadId = findViewById(R.id.imgDownloadId);

        //Getting value from intent video id
        getData = getIntent();
        strVideoId = getData.getStringExtra("storedVideoId");
        strChannelName = getData.getStringExtra("storedChannelName");

        strPhoneNumber = SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number");

        //Calling banner api.
        callBannerListApi();

        /*Toast.makeText(VideoShowActivity.this, strVideoId, Toast.LENGTH_SHORT).show();*/
        callSingleVideoApi();

        //Call seen video api
        callSeenVideoApi();

        //Calling watch api
        callWatchApi();

        //Call track api
//        callTrackApi();

        txtVideoName = findViewById(R.id.txtVideoName);
        txtVideoDuration = findViewById(R.id.txtVideoDuration);
        txtChannelNameId = findViewById(R.id.txtChannelNameId);

        categoryWatchedRec = findViewById(R.id.categoryWatchedRec);

        txtChannelNameId.setText(strChannelName);

        imgBack = findViewById(R.id.imgBack);
        toolbarImgId = findViewById(R.id.toolbarImgId);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoShowActivity.this, SportsActivity.class));
            }
        });

        toolbarImgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoShowActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        imgDownloadId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //code for permission.
                if (ActivityCompat.checkSelfPermission(VideoShowActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(VideoShowActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(VideoShowActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    }, REQ_CODE);
                } else {
                    /*Toast.makeText(VideoShowActivity.this, "Permission Granted.", Toast.LENGTH_SHORT).show();*/
//                    startActivity(new Intent(VideoShowActivity.this, SyncDataActivity.class));
                    callDownload();
                }

            }
        });
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

                }
                break;
        }
    }

    //Method for call download advt videos
    private void callDownloadAdvt() {
        //Below code for create new folder in the download directory
        String folder_main = "AddVideos";
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
            f.mkdirs();
        }

        fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + folder_main + "/" + strAddVideoNameToStore  /*+ ".mp4"*/ /*strVideoName*/;
        /*fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + strAddVideoNameToStore  *//*+ ".mp4"*//* *//*strVideoName*//*;*/


        final Request request = new Request(strAddVideoNameUrl, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                Toast.makeText(VideoShowActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                //Code for save data in the database
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload));*/
                databaseHandler.addDataToAd(new AddVideoModel(fileToDownload, strAddVideoNameToStore));
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                Toast.makeText(VideoShowActivity.this, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method for call the download function
    private void callDownload() {

        /*fileToDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/view4all/" + strVideoName;*/

        fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + strDbVideoName + ".mp4" /*strVideoName*/;


        final Request request = new Request(strVideoUrlForDownload, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.addListener(fetchListener);

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                Toast.makeText(VideoShowActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload, strVideoId,
                        strVideoTime));*/

            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                Toast.makeText(VideoShowActivity.this, result.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Not usable
    /*private void callVideoApi() {

        Call<VideoResponse> call = RetrofitClient.getInstance().getMyApi().videoApi(strVideoId, strPhoneNumber *//*"3333333333"*//*, "675465463");

        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.body() != null) {
                    Log.d("Videoapires ", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("Videoapifail ", t.getMessage());
            }
        });
    }*/

    //Not usable
    /*private void callAdvertApi(String currentAddVideoUrl) {

        Call<AdvertResponse> call = RetrofitClient.getInstance().getMyApi().advert(currentAddVideoUrl);

        call.enqueue(new Callback<AdvertResponse>() {
            @Override
            public void onResponse(Call<AdvertResponse> call, Response<AdvertResponse> response) {
                if (response.body() != null) {
                    Log.d("advertapiresponse: ", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<AdvertResponse> call, Throwable t) {
                Log.d("advertapierror: ", t.getMessage());
            }
        });
    }*/

    /*private void callTrackApi() {
        progressDialog.show();

        Call<TrackResponse> call = RetrofitClient.getInstance().getMyApi()
                .sendTrack(SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("id"),
                        "video");

        call.enqueue(new Callback<TrackResponse>() {
            @Override
            public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    Toast.makeText(VideoShowActivity.this,
                            "TRACK" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TrackResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, "TRACK" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    private void callWatchApi(/*String catName, String catId*/) {
        Call<WatchResponse> call = RetrofitClient.getInstance().getMyApi().watchApi(/*catName*/strVideoId,
                strPhoneNumber,
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<WatchResponse>() {
            @Override
            public void onResponse(Call<WatchResponse> call, Response<WatchResponse> response) {
                if (response.body() != null) {
                    Log.d("watchapires ", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<WatchResponse> call, Throwable t) {
                Log.d("watchapifail ", t.getMessage());
            }
        });
    }

    private void callWatchAdvert(String imageName) {

        /*String bannerUrl = "";
        String tempStr = "";
        for (int i = 0; i < bannerList.size(); i++) {
            tempStr = bannerList.get(i).getImageUrl().replace("http://dev.view4all.tv/content/", "");
            bannerUrl = bannerUrl + ", " + tempStr;
        }
        Log.d("BANNERURL", bannerUrl);*/

        Call<WatchAdvertResponse> call = RetrofitClient.getInstance().getMyApi().watchAdvert(
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                imageName,
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<WatchAdvertResponse>() {
            @Override
            public void onResponse(Call<WatchAdvertResponse> call, Response<WatchAdvertResponse> response) {
                if (response.body() != null) {
                    Log.d("watchadvertsuccess", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<WatchAdvertResponse> call, Throwable t) {
                Log.d("watchadvertfail", t.getMessage());
            }
        });
    }

    private void callWatchMarker() {
        Call<WatchMarkerResponse> call = RetrofitClient.getInstance().getMyApi().watchMarker("",
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<WatchMarkerResponse>() {
            @Override
            public void onResponse(Call<WatchMarkerResponse> call, Response<WatchMarkerResponse> response) {
                if (response.body() != null) {
                    Log.d("watchmarkersuccess", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<WatchMarkerResponse> call, Throwable t) {
                Log.d("watchmarkerfail", t.getMessage());
            }
        });
    }

    private void callWatch5Api() {
        Call<Watch5Response> call = RetrofitClient.getInstance().getMyApi().watch5("",
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Watch5Response>() {
            @Override
            public void onResponse(Call<Watch5Response> call, Response<Watch5Response> response) {
                if (response.body() != null) {
                    Log.d("watch5success", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Watch5Response> call, Throwable t) {
                Log.d("watch5fail", t.getMessage());
            }
        });
    }

    private void callWatch4Api() {
        Call<Watch4Response> call = RetrofitClient.getInstance().getMyApi().watch4(/*strAddVideoId*/
                strChannelName,
                "",
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Watch4Response>() {
            @Override
            public void onResponse(Call<Watch4Response> call, Response<Watch4Response> response) {
                if (response.body() != null) {
                    /*callWatchMarker();*/
                    Log.d("watch4success", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Watch4Response> call, Throwable t) {
                Log.d("watch4fail", t.getMessage());
            }
        });
    }

    private void callWatch3Api() {
        Call<Watch3Response> call = RetrofitClient.getInstance().getMyApi().watch3(/*strAddVideoId*/
                strAddVideoNameToStore,
                "",
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Watch3Response>() {
            @Override
            public void onResponse(Call<Watch3Response> call, Response<Watch3Response> response) {
                if (response.body() != null) {
                    Log.d("watch3success", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Watch3Response> call, Throwable t) {
                Log.d("watch3fail", t.getMessage());
            }
        });
    }

    private void callWatch2Api() {

        Call<Watch2Response> call = RetrofitClient.getInstance().getMyApi().watch2(/*strAddVideoId*/
                strAddVideoNameToStore,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("catIdFromHome"),
                "",
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Watch2Response>() {
            @Override
            public void onResponse(Call<Watch2Response> call, Response<Watch2Response> response) {
                if (response.body() != null) {
                    Log.d("watch2success", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Watch2Response> call, Throwable t) {
                Log.d("watch2fail", t.getMessage());
            }
        });

    }

    private void callWatch1Api() {
        Call<Watch1Response> call = RetrofitClient.getInstance().getMyApi().watch1(/*strVideoId*/
                strAddVideoNameToStore,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("phone_number"),
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("catIdFromHome"),
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<Watch1Response>() {
            @Override
            public void onResponse(Call<Watch1Response> call, Response<Watch1Response> response) {
                if (response.body() != null) {
                    Log.d("watch1success", response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<Watch1Response> call, Throwable t) {
                Log.d("watch1fail", t.getMessage());
            }
        });
    }

    private void callSingleVideoApi() {
        progressDialog.show();

        Call<SingleVideoResponse> call = RetrofitClient.getInstance().getMyApi().singleVideo(strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<SingleVideoResponse>() {
            @Override
            public void onResponse(Call<SingleVideoResponse> call, Response<SingleVideoResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    //Call api for save watched video
                    callSaveWatchVideoApi();
                    /*Toast.makeText(VideoShowActivity.this, response.body().getData().get(0).getUrlVideo(), Toast.LENGTH_SHORT).show();*/
                    Log.d("dataValue", response.body().getData().get(0).getUrlVideo());

                    txtVideoName.setText(response.body().getData().get(0).getDescription().getName());
                    txtVideoDuration.setText(response.body().getData().get(0).getTime());
                    strDbVideoName = response.body().getData().get(0).getDescription().getName();

                    //Call advert api method
                    /*callAdvertApi(response.body().getData().get(0).getAddUrlVideo());*/

                    //Call video api method
                    /*callVideoApi();*/

                    //Code for get category id
                    strCategoryId = response.body().getData().get(0).getCategoryId();

                    strVideoName = response.body().getData().get(0).getUrlVideo()
                            .replace("http://appdev.view4all.tv/content/", "");

                    strAddVideoNameToStore = response.body().getData().get(0).getAddUrlVideo()
                            .replace("http://appdev.view4all.tv/content/", "");

                    //Add video add name
                    strAddVideoNameUrl = response.body().getData().get(0).getAddUrlVideo();
                    //Add video id
                    strAddVideoId = response.body().getData().get(0).getId();

                    strVideoUrlForDownload = response.body().getData().get(0).getUrlVideo();

                    //Call method for download advert
                    callDownloadAdvt();

                    //Assign time value to string
                    strVideoTime = response.body().getData().get(0).getTime();

                    //Method for try running video
                    runVideo(response.body().getData().get(0).getUrlVideo(),
                            response.body().getData().get(0).getTime());
                }
            }

            @Override
            public void onFailure(Call<SingleVideoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Method for save the videos which have seen
    private void callSaveWatchVideoApi() {

        Call<WatchVideoResponse> call = RetrofitClient.getInstance().getMyApi().saveWatchVideo(SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("id"),
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("catIdFromHome"),
                strVideoId);

        call.enqueue(new Callback<WatchVideoResponse>() {
            @Override
            public void onResponse(Call<WatchVideoResponse> call, Response<WatchVideoResponse> response) {
                if (response.body() != null) {

                }
            }

            @Override
            public void onFailure(Call<WatchVideoResponse> call, Throwable t) {

            }
        });

    }

    //Method for show the seen videos
    private void callSeenVideoApi() {
        progressDialog.show();

        Call<SeenVideoResponse> call = RetrofitClient.getInstance().getMyApi().showWatchVideo(SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("id"),
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("catIdFromHome"),
                strVideoId,
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity"));

        call.enqueue(new Callback<SeenVideoResponse>() {
            @Override
            public void onResponse(Call<SeenVideoResponse> call, Response<SeenVideoResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    listData = new ArrayList<>();
                    listData.addAll(response.body().getData());

                    seenVideoAdapter = new SeenVideoAdapter(VideoShowActivity.this, listData);
                    //Setting staggered layout in recycler view
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
                    // Setting LayoutManager to RecyclerView
                    categoryWatchedRec.setLayoutManager(staggeredGridLayoutManager);
                    categoryWatchedRec.setAdapter(seenVideoAdapter);
                }
            }

            @Override
            public void onFailure(Call<SeenVideoResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void runVideo(String url, String timeFromApi) {
        try {
//            String link="http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp; current=1376992942447_242.mp4";
            String link = url;
            /*ScalableVideoView videoView = findViewById(R.id.VideoView);*/
            CustomVideoView videoView = findViewById(R.id.VideoView);

            progressbarVideo.setVisibility(View.VISIBLE);


            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            /*Uri video = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");*/
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            Uri video = Uri.parse(strAddVideoNameUrl);
            videoView.setMediaController(null);
            videoView.setVideoURI(video);
            videoView.start();


            //Calling watch1 api when ad video start first
            callWatch1Api();

            //Calling watch2 api when ad video start
            callWatch2Api();

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    /*Toast.makeText(VideoShowActivity.this, "Complete", Toast.LENGTH_SHORT).show();*/
                    //Calling watch3 api, when ad video end
                    callWatch3Api();

                    Uri video = Uri.parse(link);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    videoView.start();

                    videoView.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
                        @Override
                        public void onPlay() {
                            if (videoView.isPlaying()) {
                                /*Toast.makeText(VideoShowActivity.this, "IN IF isPlaying", Toast.LENGTH_SHORT).show();*/
                                //Calling watch4 api, when video start
                                callWatch4Api();

                                //Code for convert the hhmmss in seconds
                                String timeInSeconds = timeFromApi; //mm:ss
                                String[] units = timeInSeconds.split(":"); //will break the string up into an array
                                int hours = Integer.parseInt(units[0]);
                                int minutes = Integer.parseInt(units[1]); //first element
                                int seconds = Integer.parseInt(units[2]); //second element
                                int duration = 3600 * hours + 60 * minutes + seconds; //add up our values
                                int val = 10;


                                //Below code for start timer
                                new CountDownTimer(duration * 1000L, 1000 * 60) {

                                    @Override
                                    public void onTick(long l) {
                                        /*Toast.makeText(VideoShowActivity.this, "onTick", Toast.LENGTH_SHORT).show();*/


                                        if (videoView.isPlaying()) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                                if (duration - 5 < l / 1000) {

                                                } else {
                                                    //Calling the marker api, after one minutes
                                                    callWatchMarker();
                                                }
                                            } else {
                                                //Calling the marker api, after one minutes
                                                callWatchMarker();
                                            }


                                        }

                                    }

                                    @Override
                                    public void onFinish() {
                                        /*Toast.makeText(VideoShowActivity.this, "onFinish", Toast.LENGTH_SHORT).show();*/
                                    }
                                }.start();
                            }


                        }

                        @Override
                        public void onPause() {
                            /*Toast.makeText(VideoShowActivity.this, "OnPause Called", Toast.LENGTH_SHORT).show();*/
                        }
                    });

                    //Calling watch4 api, when video start
                    /*callWatch4Api();*/


                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            /*Toast.makeText(VideoShowActivity.this, "Finished", Toast.LENGTH_SHORT).show();*/
                            //Calling watch5 api, when video end
                            callWatch5Api();
                        }
                    });

                }
            });

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                        @Override
                        public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                            progressbarVideo.setVisibility(View.GONE);
                            mediaPlayer.start();
                        }
                    });
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            Toast.makeText(this, "Error connecting", Toast.LENGTH_SHORT).show();
        }
    }

    private void callBannerListApi() {
        progressDialog.show();

        Call<BannerResponse> call = RetrofitClient.getInstance().getMyApi().bannerList(
                SharePrefrancClass.getInstance(VideoShowActivity.this).getPref("fromActivity")
        );

        call.enqueue(new Callback<BannerResponse>() {
            @Override
            public void onResponse(Call<BannerResponse> call, Response<BannerResponse> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    bannerList = new ArrayList<>();
                    bannerList.addAll(response.body().getData());
                    imageSlider.setSliderAdapter(new HomeAddSliderAdapter(bannerList, VideoShowActivity.this));
                    imageSlider.startAutoCycle();

                    //Code for hit api in for loop
                    String bannerUrl = "";
                    String tempStr = "";
                    for (int i = 0; i < bannerList.size(); i++) {
                        tempStr = bannerList.get(i).getImageUrl().replace("http://appdev.view4all.tv/content/", "");
                        /*bannerUrl = bannerUrl + ", " + tempStr;*/
                        //Calling watchadvert api, for send banner image url
                        callWatchAdvert(tempStr);
                    }


                }
            }

            @Override
            public void onFailure(Call<BannerResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VideoShowActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Code for fetchlistener
    FetchListener fetchListener = new FetchListener() {
        @Override
        public void onAdded(@NonNull Download download) {
            //This method is called first time when download the file
            Toast.makeText(VideoShowActivity.this, "First download", Toast.LENGTH_SHORT).show();
            databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload, strVideoId,
                    strVideoTime, strChannelName));
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
            fetch.removeListener(fetchListener);
            Toast.makeText(VideoShowActivity.this, "Download finished", Toast.LENGTH_SHORT).show();

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
            //This method show the progress of download and time
            Toast.makeText(VideoShowActivity.this, String.valueOf(l / 1000) + " Seconds Remaining", Toast.LENGTH_SHORT).show();
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
    };

    @Override
    protected void onPause() {
        super.onPause();
        //Saving the current page name in the prefrence
        SharePrefrancClass.getInstance(VideoShowActivity.this).savePref("fromActivity",
                "http://appdev.view4all.tv/watch/" + strVideoId + "/");
    }
}