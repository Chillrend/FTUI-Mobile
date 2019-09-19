package org.ftui.mobile.utils;

import android.widget.ImageView;
import com.esafirm.imagepicker.features.imageloader.ImageLoader;
import com.esafirm.imagepicker.features.imageloader.ImageType;
import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements ImageLoader {
    @Override
    public void loadImage(String path, ImageView imageView, ImageType imageType){
        Picasso.get()
                .load("file://" + path)
                .resize(500,500).centerCrop()
                .into(imageView);
    }
}
