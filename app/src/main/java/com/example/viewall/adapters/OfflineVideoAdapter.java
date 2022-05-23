package com.example.viewall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.activities.VideoShowActivity;
import com.example.viewall.activities.VideoShowActivityOffline;
import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.example.viewall.models.databasemodels.VideoModel;

import java.util.List;

public class OfflineVideoAdapter extends RecyclerView.Adapter<OfflineVideoAdapter.OfflineViewHolder> {

    Context context;
    List<VideoModel> list;
    List<AddVideoModel> adList;

    public OfflineVideoAdapter(Context context, List<VideoModel> list, List<AddVideoModel> adList) {
        this.context = context;
        this.list = list;
        this.adList = adList;
    }

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
        AddVideoModel adVideoModel = adList.get(position);



        holder.videoNameId.setText(videoModel.getName());
        holder.catNameTxtId.setText(videoModel.getCatname());

        holder.rootLayoutId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast.makeText(context, "Work in progress.", Toast.LENGTH_SHORT).show();*/
                Intent intentOffline = new Intent(context, VideoShowActivityOffline.class);
                intentOffline.putExtra("videoNameOffline", videoModel.getName());
                intentOffline.putExtra("videoUrlOffline", videoModel.getVideoUrl());
                intentOffline.putExtra("videoIdOffline", videoModel.getVideoId());
                intentOffline.putExtra("videoTimeOffline", videoModel.getVideotime());
                intentOffline.putExtra("adVideoUrlOffline", adVideoModel.getAddvideoUrl());
                intentOffline.putExtra("adVideoNameOffline", adVideoModel.getAddname());

                context.startActivity(intentOffline);
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

        public OfflineViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayoutId = itemView.findViewById(R.id.rootLayoutId);
            videoNameId = itemView.findViewById(R.id.videoNameId);
            catNameTxtId = itemView.findViewById(R.id.catNameTxtId);
        }
    }
}
