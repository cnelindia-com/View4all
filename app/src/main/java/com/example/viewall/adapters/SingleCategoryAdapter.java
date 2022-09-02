package com.example.viewall.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.activities.VideoShowActivity;
import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.models.singlecategorylist.DataItem;
import com.example.viewall.models.singlecategorylist.HeaderItem;
import com.example.viewall.utils.DatabaseHandler;
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

public class SingleCategoryAdapter extends RecyclerView.Adapter<SingleCategoryAdapter.SingleCatHolder> {

    Context context;
    ArrayList<DataItem> list;
    ArrayList<HeaderItem> listHeader;

    String fileToDownload;
    String strVideoUrlForDownload, strVideoName, strAdVideoUrlForDownload, strAdVideoNameToStore,
    strChannelName;

    DatabaseHandler databaseHandler;

    //Creating reference variable of fetch
    private Fetch fetch;

    String strDbVideoName, strVideoId, strVideoTime;

    public SingleCategoryAdapter(Context context, ArrayList<DataItem> list, ArrayList<HeaderItem> listHeader) {
        this.context = context;
        this.list = list;
        this.listHeader = listHeader;

        //Configuring fetch
        FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(context)
                .setDownloadConcurrentLimit(3)
                .build();

        fetch = Fetch.Impl.getInstance(fetchConfiguration);

        databaseHandler = new DatabaseHandler(context);
    }

    /*public SingleCategoryAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }*/

    @NonNull
    @Override
    public SingleCatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category_item, parent, false);
        return new SingleCatHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleCatHolder holder, int position) {
        DataItem dataItem = list.get(position);
        HeaderItem headerItem = listHeader.get(0);

        holder.videoNameId.setText(dataItem.getDescription().getName());



        Glide.with(context)
                .load(dataItem.getImageUrl())
                .placeholder(R.drawable.mainlogo)
                .into(holder.imageShowId);

        holder.rootLayoutSingleId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,VideoShowActivity.class);
                intent.putExtra("storedVideoId", dataItem.getId());
                intent.putExtra("storedChannelName", headerItem.getName());
                context.startActivity(intent);
            }
        });

        holder.imageDownloadId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(context, "VIDEO URL : " + dataItem.getUrlVideo(), Toast.LENGTH_SHORT).show();*/
                strDbVideoName = dataItem.getDescription().getName();
                strVideoId = dataItem.getId();
                strVideoTime = dataItem.getTime();
                strVideoUrlForDownload = dataItem.getUrlVideo();
                strAdVideoUrlForDownload = dataItem.getAddUrlVideo();
                strChannelName = headerItem.getName();

                strVideoName = dataItem.getUrlVideo()
                        .replace("http://appdev.view4all.tv/content/", "");

                strAdVideoNameToStore = dataItem.getAddUrlVideo()
                        .replace("http://appdev.view4all.tv/content/", "");

                //Call advert download method here
                callDownloadAdvt();

                //Call download method here
                /*callDownload(dataItem.getDescription().getName(), dataItem.getId(),
                        dataItem.getTime());*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingleCatHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayoutSingleId;
        TextView videoNameId;
        ImageView imageShowId, imageDownloadId;

        public SingleCatHolder(@NonNull View itemView) {
            super(itemView);

            videoNameId = itemView.findViewById(R.id.videoNameId);
            imageShowId = itemView.findViewById(R.id.imageShowId);
            imageDownloadId = itemView.findViewById(R.id.imageDownloadId);
            rootLayoutSingleId = itemView.findViewById(R.id.rootLayoutSingleId);
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

        fileToDownload = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + folder_main + "/" + strAdVideoNameToStore  /*+ ".mp4"*/ /*strVideoName*/;
        /*fileToDownload = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + strAddVideoNameToStore  *//*+ ".mp4"*//* *//*strVideoName*//*;*/


        final Request request = new Request(strAdVideoUrlForDownload, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                /*Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();*/
                //Code for save data in the database
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload));*/
                databaseHandler.addDataToAd(new AddVideoModel(fileToDownload, strAdVideoNameToStore, strVideoId));


                //Call download method here
                callDownload("", "", "");
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Method for call the download function
    private void callDownload(String dbVideoName, String videoId, String videoTime) {

        /*fileToDownload = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/view4all/" + strVideoName;*/

        fileToDownload = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + strDbVideoName/*strDbVideoName*/ + ".mp4" /*strVideoName*/;

//        databaseHandler.addData(new VideoModel("videoname", fileToDownload));

        final Request request = new Request(strVideoUrlForDownload, fileToDownload);
        request.setPriority(Priority.HIGH);
        request.setNetworkType(NetworkType.ALL);
        request.addHeader("clientKey", "SD78DF93_3947&MVNGHE1WONG");

        fetch.addListener(fetchListener);

        fetch.enqueue(request, new Func<Request>() {
            @Override
            public void call(@NonNull Request result) {
                Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();
                /*databaseHandler.addData(new VideoModel(strDbVideoName, fileToDownload, strVideoId,
                        strVideoTime));*/
            }
        }, new Func<Error>() {
            @Override
            public void call(@NonNull Error result) {
                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    FetchListener fetchListener = new FetchListener() {
        @Override
        public void onAdded(@NonNull Download download) {
            //This method is called first time when download the file
            Toast.makeText(context, "First download", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Download finished", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, String.valueOf(l/1000) + " Seconds Remaining", Toast.LENGTH_SHORT).show();
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
}
