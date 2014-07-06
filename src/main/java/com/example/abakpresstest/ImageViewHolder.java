package com.example.abakpresstest;

import android.content.Context;
import android.widget.ImageView;

public class ImageViewHolder {
    public ImageView imageView;
    public String url;
    public Context context;
    public ImageViewHolder(ImageView imageView, String url, Context context){
        this.imageView = imageView;
        this.url = url;
        this.context = context;
    }
}
