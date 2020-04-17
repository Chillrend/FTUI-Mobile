package com.rsypj.ftuimobile.helper;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.volley.VolleyUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.File;
import java.io.InputStream;

public class CustomGlide implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        RequestQueue queue = new RequestQueue( // params hardcoded from Volley.newRequestQueue()
                new DiskBasedCache(new File(context.getCacheDir(), "volley")),
                new BasicNetwork(new HurlStack())) {
            @Override public <T> Request<T> add(Request<T> request) {
                request.setRetryPolicy(new DefaultRetryPolicy(12000, 1, 1));
                return super.add(request);
            }
        };
        queue.start();
        glide.register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(queue));
    }
}
