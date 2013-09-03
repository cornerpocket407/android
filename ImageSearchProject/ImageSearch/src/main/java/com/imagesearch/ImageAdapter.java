package com.imagesearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image  .SmartImageView;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<ImageResult> {

    public ImageAdapter(Context context, List<ImageResult> imageResults) {
        super(context, R.layout.image_result, imageResults);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResult item = this.getItem(position);
        SmartImageView ivImage;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ivImage = (SmartImageView) inflater.inflate(R.layout.image_result, parent, false);
        } else {
            ivImage = (SmartImageView) convertView;
            ivImage.setImageResource(android.R.color.transparent);
       }
        ivImage.setImageUrl(item.getThumbUrl());
        return ivImage;
    }
}