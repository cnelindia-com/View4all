package com.example.viewall.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.activities.DownloanActivity;
import com.example.viewall.activities.VideoShowActivity;
import com.example.viewall.activities.VideoShowActivityOffline;
import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.example.viewall.models.databasemodels.VideoModel;
import com.example.viewall.utils.DatabaseHandler;

import java.io.File;
import java.util.List;

public class OfflineVideoAdapter extends RecyclerView.Adapter<OfflineVideoAdapter.OfflineViewHolder> {

    Context context;
    List<VideoModel> list;
    List<AddVideoModel> adList;
    AlertDialog.Builder builder;
    DatabaseHandler databaseHandler;

    public OfflineVideoAdapter(Context context, List<VideoModel> list) {
        this.context = context;
        this.list = list;
        this.databaseHandler = new DatabaseHandler(context);
    }

    /*public OfflineVideoAdapter(Context context, List<VideoModel> list, List<AddVideoModel> adList) {
        this.context = context;
        this.list = list;
        this.adList = adList;

        databaseHandler = new DatabaseHandler(context);
    }*/

    /*public OfflineVideoAdapter(Context context, List<VideoModel> list) {
        this.context = context;
        this.list = list;
    }*/

    @NonNull
    @Override
    public OfflineVideoAdapter.OfflineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.offline_list_item, parent, false);
        return new OfflineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineVideoAdapter.OfflineViewHolder holder, int position) {
        VideoModel videoModel = list.get(position);
//        AddVideoModel adVideoModel = adList.get(position);

        if (position == 0) {
            holder.catNameTxtId.setVisibility(View.VISIBLE);
            holder.catNameTxtId.setText(videoModel.getCatname());
        }

        if (position > 0) {
            if (videoModel.getCatname().equals(list.get(position - 1).getCatname())) {

            } else {
                holder.catNameTxtId.setVisibility(View.VISIBLE);
                holder.catNameTxtId.setText(videoModel.getCatname());
            }
        }



        holder.videoNameId.setText(videoModel.getName());


        holder.rootLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(context, "Work in progress.", Toast.LENGTH_SHORT).show();*/
                Intent intentOffline = new Intent(context, VideoShowActivityOffline.class);
                intentOffline.putExtra("videoNameOffline", videoModel.getName());
                intentOffline.putExtra("videoUrlOffline", videoModel.getVideoUrl());
                intentOffline.putExtra("videoIdOffline", videoModel.getVideoId());
                intentOffline.putExtra("videoTimeOffline", videoModel.getVideotime());
                /*intentOffline.putExtra("adVideoUrlOffline", adVideoModel.getAddvideoUrl());
                intentOffline.putExtra("adVideoNameOffline", adVideoModel.getAddname());*/

                context.startActivity(intentOffline);
            }
        });

        //Delete button click listener
        holder.deleteOfflineButtonId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Delete button clicked.", Toast.LENGTH_SHORT).show();

                builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you want to Delete \" "+ videoModel.getName() + " \" Video?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public  void onClick(DialogInterface dialog, int i) {
                        databaseHandler.removeVideo(videoModel.getVideoId());
                        //Code for delete the video from internal storage
                        File file = new File(videoModel.getVideoUrl());
                        boolean deleted = file.delete();
                        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

                        Intent refreshIntent = new Intent(context, DownloanActivity.class);
                        /*refreshIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                        refreshIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(refreshIntent);
//                        notifyDataSetChanged();

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class OfflineViewHolder extends RecyclerView.ViewHolder {

        LinearLayout rootLayoutId;
        TextView videoNameId, catNameTxtId;
        ImageView deleteOfflineButtonId;

        public OfflineViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayoutId = itemView.findViewById(R.id.rootLayoutId);
            videoNameId = itemView.findViewById(R.id.videoNameId);
            catNameTxtId = itemView.findViewById(R.id.catNameTxtId);
            deleteOfflineButtonId = itemView.findViewById(R.id.deleteOfflineButtonId);
        }
    }
}
