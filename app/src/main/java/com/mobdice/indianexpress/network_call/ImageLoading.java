package com.mobdice.indianexpress.network_call;

import android.content.Context;
import android.widget.ImageView;

import com.mobdice.indianexpress.R;

public class ImageLoading {
    public static void loadImage(String url, ImageView imageView,Context context)
    {
        GlideApp
                .with(context)
                .load(url)
                .placeholder(R.drawable.ie_image_default)
                .into(imageView);
    }
}
