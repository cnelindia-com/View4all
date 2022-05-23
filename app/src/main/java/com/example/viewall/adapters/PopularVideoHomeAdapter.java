package com.example.viewall.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.activities.HomeActivity;
import com.example.viewall.activities.VideoShowActivity;
import com.example.viewall.models.popularviedos.DataItem;

import java.util.ArrayList;

public class PopularVideoHomeAdapter extends RecyclerView.Adapter<PopularVideoHomeAdapter.PopularViewHolder> {

    Context context;
    ArrayList<DataItem> list;


    public PopularVideoHomeAdapter(Context context, ArrayList<DataItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popularvideo_item, parent, false);
        return new PopularViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
        DataItem dataItem = list.get(position);

        Glide.with(context)
                .load(dataItem.getImageUrl())
                .placeholder(R.drawable.mainlogo)
                .into(holder.imgVid);

        holder.imgVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoShowActivity.class);
                intent.putExtra("storedVideoId", dataItem.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder {

        ImageView imgVid;

        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);

            imgVid = itemView.findViewById(R.id.imgVid);
        }
    }
}
