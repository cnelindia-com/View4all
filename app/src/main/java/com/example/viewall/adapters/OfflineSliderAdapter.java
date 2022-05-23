package com.example.viewall.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.viewall.R;
import com.example.viewall.models.databasemodels.TableBannerModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class OfflineSliderAdapter extends SliderViewAdapter<OfflineSliderAdapter.OfflineSliderVh> {

    List<TableBannerModel> offlineBannerData;
    Context context;

    public OfflineSliderAdapter(List<TableBannerModel> offlineBannerData, Context context) {
        this.offlineBannerData = offlineBannerData;
        this.context = context;
    }

    @Override
    public OfflineSliderAdapter.OfflineSliderVh onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new OfflineSliderVh(view);
    }

    @Override
    public void onBindViewHolder(OfflineSliderAdapter.OfflineSliderVh viewHolder, int position) {
        TableBannerModel tableBannerModel = offlineBannerData.get(position);
        Glide.with(viewHolder.itemView)
                .load(tableBannerModel.getBannerUrl())
                .fitCenter()
                .into(viewHolder.homeAddSliderId);
    }

    @Override
    public int getCount() {
        return offlineBannerData.size();
    }

    public class OfflineSliderVh extends ViewHolder {

        View itemView;
        ImageView homeAddSliderId;

        public OfflineSliderVh(View itemView) {
            super(itemView);
            homeAddSliderId = itemView.findViewById(R.id.homeAddSliderId);
            this.itemView = itemView;
        }
    }
}
