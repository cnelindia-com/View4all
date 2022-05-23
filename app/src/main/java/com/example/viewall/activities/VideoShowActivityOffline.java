package com.example.viewall.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewall.R;
import com.example.viewall.adapters.HomeAddSliderAdapter;
import com.example.viewall.adapters.OfflineSliderAdapter;
import com.example.viewall.models.databasemodels.BannerOfflineModel;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.example.viewall.models.databasemodels.TableOfflineModel;
import com.example.viewall.utils.CustomVideoView;
import com.example.viewall.utils.DatabaseHandler;
import com.example.viewall.utils.ScalableVideoView;
import com.example.viewall.utils.SharePrefrancClass;
import com.smarteist.autoimageslider.SliderView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VideoShowActivityOffline extends AppCompatActivity {

    TextView txtVideoName;
    ProgressBar progressbarVideo;
    String strVideoNameOff, strVideoUrlOff, strVideoIdOff, strAdVideoUrlOff, strAdVideoNameOff,
            strVideoTimeOff;

    DatabaseHandler databaseHandler;
    List<TableBannerModel> offlineBannerData;
    SliderView imageSlider;
    ImageView imgBack, toolbarImgId, icn_hamburger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show_offline);

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        imgBack = findViewById(R.id.imgBack);
        toolbarImgId = findViewById(R.id.toolbarImgId);
        icn_hamburger = findViewById(R.id.icn_hamburger);

        progressbarVideo = findViewById(R.id.progressbarVideo);
        txtVideoName = findViewById(R.id.txtVideoName);
        imageSlider = findViewById(R.id.imageSlider);

        databaseHandler = new DatabaseHandler(this);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoShowActivityOffline.this, DownloanActivity.class));
            }
        });

        toolbarImgId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VideoShowActivityOffline.this, DownloanActivity.class));
            }
        });

        icn_hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of Popupmenu
                PopupMenu popupMenu = new PopupMenu(VideoShowActivityOffline.this, view);
                //Inflating the Popup using xml file
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                //registering popup with OnMenuItemClickListener
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.term_item) {
                            startActivity(new Intent(VideoShowActivityOffline.this, TermConditionsActivity.class));
                        } else if (item.getItemId() == R.id.contact_item) {
                            startActivity(new Intent(VideoShowActivityOffline.this, ContactUsActivity.class));
                        }
                        return true;
                    }
                });
                popupMenu.show(); //showing popup menu
            }
        });

        Intent intent = getIntent();
        strVideoUrlOff = intent.getStringExtra("videoUrlOffline");
        strVideoIdOff = intent.getStringExtra("videoIdOffline");
        strAdVideoUrlOff = intent.getStringExtra("adVideoUrlOffline");
        strAdVideoNameOff = intent.getStringExtra("adVideoNameOffline");
        strVideoTimeOff = intent.getStringExtra("videoTimeOffline");
        txtVideoName.setText(intent.getStringExtra("videoNameOffline"));

        //Call get data from the tablebanner
        offlineBannerData = databaseHandler.getBannerData();

        //Code for insert banner data into the table
        for (int i=0; i<offlineBannerData.size(); i++) {
            databaseHandler.addOfflineData(new TableOfflineModel("",
                    "",
                    "",
                    SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                            .getPref("phone_number"), "", "",
                    "", "", "", "banner",
                    offlineBannerData.get(i).getName(), "Offline Downloads"));
        }

        /*Toast.makeText(VideoShowActivityOffline.this, intent.getStringExtra("videoNameOffline")
                + "\n" + intent.getStringExtra("videoUrlOffline"), Toast.LENGTH_SHORT).show();*/

        //Call here the method for run the video
        runVideo(intent.getStringExtra("adVideoUrlOffline"),
                intent.getStringExtra("videoUrlOffline"));

        //Code for set the slider to the adapter
        imageSlider.setSliderAdapter(new OfflineSliderAdapter(offlineBannerData, VideoShowActivityOffline.this));
        imageSlider.startAutoCycle();
    }

    private void runVideo(String adUrl, String url) {
        try {
//            String link="http://s1133.photobucket.com/albums/m590/Anniebabycupcakez/?action=view&amp; current=1376992942447_242.mp4";
            /*String link = url;*/
            /*ScalableVideoView videoView = findViewById(R.id.VideoView);*/
            CustomVideoView videoView = findViewById(R.id.VideoView);

            progressbarVideo.setVisibility(View.VISIBLE);

            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            /*Uri video = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4");*/
//            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
            Uri video = Uri.parse(adUrl);
            videoView.setMediaController(null);
            videoView.setVideoURI(video);
            videoView.start();


            //Calling watch1 api when ad video start
            //Code for get the current data and time
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
            String date = sdf.format(new Date());
            //Advt start update table offline
            databaseHandler.addOfflineData(new TableOfflineModel("ad_play",
                    "",
                    "",
                    SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                            .getPref("phone_number"), date, "",
                    "", strAdVideoNameOff, "", "", "",
                    "Offline Downloads"));

            //Calling watch2 api when ad video start

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    /*Toast.makeText(VideoShowActivity.this, "Complete", Toast.LENGTH_SHORT).show();*/

                    //Calling watch3 api, when ad video end
                    //Code for get the current data and time
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                    String date = sdf.format(new Date());
                    databaseHandler.addOfflineData(new TableOfflineModel("",
                            "",
                            "",
                            SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                    .getPref("phone_number"), date, "",
                            "ad_end", strAdVideoNameOff, "", "", "",
                            "Offline Downloads"));


                    Uri video = Uri.parse(url);
                    videoView.setMediaController(mediaController);
                    videoView.setVideoURI(video);
                    videoView.start();

                    videoView.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
                        @Override
                        public void onPlay() {
                            /*Toast.makeText(VideoShowActivity.this, "OnPlay Called", Toast.LENGTH_SHORT).show();*/
                            databaseHandler.addOfflineData(new TableOfflineModel("",
                                    "video_start",
                                    strVideoIdOff,
                                    SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                            .getPref("phone_number"), date, "",
                                    "", "", "",
                                    "", "", "Offline Downloads"));
                        }

                        @Override
                        public void onPause() {
                            /*Toast.makeText(VideoShowActivity.this, "OnPause Called", Toast.LENGTH_SHORT).show();*/
                        }
                    });

                    //Saving offline data information here
                    /*databaseHandler.addOfflineData(new TableOfflineModel("advert", "video",
                            strVideoIdOff,
                            SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                    .getPref("phone_number"), date));*/

                    //Calling watch4 api, when video start

                    //Code for convert the hhmmss in seconds
                    String timeInSeconds = strVideoTimeOff; //mm:ss
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

                            if (videoView.isPlaying()){
                                //Calling the marker api, after one minutes
                                /*callWatchMarker();*/
                                databaseHandler.addOfflineData(new TableOfflineModel("",
                                        "",
                                        strVideoIdOff,
                                        SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                                .getPref("phone_number"), date, "",
                                        "", "",
                                        "marker", "", "",
                                        "Offline Downloads"));
                            }
                        }

                        @Override
                        public void onFinish() {
                            /*Toast.makeText(VideoShowActivity.this, "onFinish", Toast.LENGTH_SHORT).show();*/
                        }
                    }.start();

                    databaseHandler.addOfflineData(new TableOfflineModel("",
                            "video_start",
                            strVideoIdOff,
                            SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                    .getPref("phone_number"), date, "",
                            "", "", "", "", "",
                            "Offline Downloads"));




                    videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            /*Toast.makeText(VideoShowActivity.this, "Finished", Toast.LENGTH_SHORT).show();*/

                            //Calling watch5 api, when video end
                            databaseHandler.addOfflineData(new TableOfflineModel("",
                                    "",
                                    strVideoIdOff,
                                    SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                            .getPref("phone_number"), date, "video_end",
                                    "", "", "",
                                    "", "",
                                    "Offline Downloads"));

                            //Trying to saving only date in database
                            /*databaseHandler.addOfflineData(new TableOfflineModel("", "",
                                    "", "", date, "end"));*/

                            //Code for get the current data and time
                            @SuppressLint("SimpleDateFormat")
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
                            String date = sdf.format(new Date());

                            //Saving offline data information here
                            /*databaseHandler.addOfflineData(new TableOfflineModel("ad_play",
                                    "video_play",
                                    strVideoIdOff,
                                    SharePrefrancClass.getInstance(VideoShowActivityOffline.this)
                                            .getPref("phone_number"), date, "video_end",
                                    "ad_end", strAdVideoNameOff));*/
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
}