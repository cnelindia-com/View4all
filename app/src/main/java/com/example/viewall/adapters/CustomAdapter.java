package com.example.viewall.adapters;

import android.content.ClipData;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    /*@Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // get current item to be displayed
        *//*ClipData.Item currentItem = (ClipData.Item) getItem(position);

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.text_view_item_name);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.text_view_item_description);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(currentItem.getItemName());
        textViewItemDescription.setText(currentItem.getItemDescription());

        // returns the view for the current row
        return convertView;*//*
    }*/
}
