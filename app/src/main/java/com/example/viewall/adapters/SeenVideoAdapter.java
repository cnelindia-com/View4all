package com.example.viewall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.models.seenvideolist.DataItem;

import java.util.ArrayList;

public class SeenVideoAdapter extends RecyclerView.Adapter<SeenVideoAdapter.SeenVidoeViewHolder> {

    Context context;
    ArrayList<DataItem> list;

    public SeenVideoAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SeenVideoAdapter.SeenVidoeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.watch_video_list_item, parent, false);
        return new SeenVidoeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SeenVideoAdapter.SeenVidoeViewHolder holder, int position) {
        DataItem dataItem = list.get(position);

        holder.videoNameId.setText(dataItem.getDescription().getName());

        Glide.with(context)
                .load(dataItem.getImageUrl())
                .placeholder(R.drawable.mainlogo)
                .into(holder.imageShowId);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SeenVidoeViewHolder extends RecyclerView.ViewHolder {

        ImageView imageShowId, imageDownloadId;
        TextView videoNameId;

        public SeenVidoeViewHolder(@NonNull View itemView) {
            super(itemView);

            imageShowId = itemView.findViewById(R.id.imageShowId);
            imageDownloadId = itemView.findViewById(R.id.imageDownloadId);
            videoNameId = itemView.findViewById(R.id.videoNameId);
        }
    }
}
