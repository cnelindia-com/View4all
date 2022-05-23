package com.example.viewall.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.models.bannerlist.DataItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class HomeAddSliderAdapter extends SliderViewAdapter<HomeAddSliderAdapter.HomeAddAdapterVh> {

    int imageArray[];
    ArrayList<DataItem> list;
    Context context;


    /*public HomeAddSliderAdapter(int[] imageArray, Context context) {
        this.imageArray = imageArray;
        this.context = context;
    }*/

    public HomeAddSliderAdapter(ArrayList<DataItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public HomeAddAdapterVh onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new HomeAddAdapterVh(view);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(HomeAddAdapterVh viewHolder, int position) {
        DataItem dataItem = list.get(position);
        Glide.with(viewHolder.itemView)
                .load(dataItem.getImageUrl())
                .fitCenter()
                .into(viewHolder.homeAddSliderId);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public class HomeAddAdapterVh extends ViewHolder {

        View itemView;
        ImageView homeAddSliderId;

        public HomeAddAdapterVh(View itemView) {
            super(itemView);
            homeAddSliderId = itemView.findViewById(R.id.homeAddSliderId);
            this.itemView = itemView;
        }
    }
}
