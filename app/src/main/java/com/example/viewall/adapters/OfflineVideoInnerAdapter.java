package com.example.viewall.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewall.R;
import com.example.viewall.models.databasemodels.AddVideoModel;
import com.example.viewall.models.databasemodels.VideoModel;

import java.util.List;

public class OfflineVideoInnerAdapter extends RecyclerView.Adapter<OfflineVideoInnerAdapter.OfflineViewInnerHolder>{

    Context context;
    List<VideoModel> list;
    List<AddVideoModel> adList;

    public OfflineVideoInnerAdapter(Context context, List<VideoModel> list, List<AddVideoModel> adList) {
        this.context = context;
        this.list = list;
        this.adList = adList;
    }

    @NonNull
    @Override
    public OfflineVideoInnerAdapter.OfflineViewInnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineVideoInnerAdapter.OfflineViewInnerHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OfflineViewInnerHolder extends RecyclerView.ViewHolder {

        TextView txtVideoNameId;

        public OfflineViewInnerHolder(@NonNull View itemView) {
            super(itemView);

            txtVideoNameId = itemView.findViewById(R.id.txtVideoNameId);
        }
    }
}
