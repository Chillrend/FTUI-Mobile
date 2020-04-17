package com.rsypj.ftuimobile.connection;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rsypj.ftuimobile.connection.listener.TimelineCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.TimelineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationRequest {

    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    public NotificationRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestNotificationUserPost(final TimelineCallBack listener){
        final ArrayList<TimelineModel> timelineModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.keluhan + "/" + SharePref.getmInstance(context).getNim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageNotification = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
//                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                int idKeluhan = xx.getInt("id_keluhan");
                                String nim = xx.getString("NIM");
                                int idCat = xx.getInt("id_kategori");
                                String desc = xx.getString("deskripsi");
                                String foto = xx.getString("foto");
                                String timePost = xx.getString("time_post");
                                int status = xx.getInt("is_approved");
                                String name = xx.getString("name");
                                String kategori = xx.getString("kategori");
                                String icon = xx.getString("icon");
                                String location = xx.getString("lokasi");

                                timelineModels.add(new TimelineModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location));
                            }
                            listener.onDataSetResutl(timelineModels);
                        }else {
                            listener.onDataIsEmpty();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "";

                if (error instanceof TimeoutError) {
                    errorMessage = "Timeout. Check your connection";
                } else if (error instanceof NoConnectionError) {
                    errorMessage = "Please turn on your connectivity";
                } else if (error instanceof AuthFailureError) {
                    errorMessage = "Authentication Error";
                } else if (error instanceof ServerError) {
                    errorMessage = "Server Error";
                } else if (error instanceof NetworkError) {
                    errorMessage = "Network Error";
                } else if (error instanceof ParseError) {
                    errorMessage = "Parse Error";
                }

                listener.onRequestError(errorMessage);
            }
        });
        requestQueue.add(stringRequest);
    }

    public void requestNotificationUserPostLoadMore(final TimelineCallBack listener){
        final ArrayList<TimelineModel> timelineModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageNotification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageNotification = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
//                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                int idKeluhan = xx.getInt("id_keluhan");
                                String nim = xx.getString("NIM");
                                int idCat = xx.getInt("id_kategori");
                                String desc = xx.getString("deskripsi");
                                String foto = xx.getString("foto");
                                String timePost = xx.getString("time_post");
                                int status = xx.getInt("is_approved");
                                String name = xx.getString("name");
                                String kategori = xx.getString("kategori");
                                String icon = xx.getString("icon");
                                String location = xx.getString("lokasi");

                                timelineModels.add(new TimelineModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location));
                            }
                            listener.onDataSetResutl(timelineModels);
                        }else {
                            listener.onDataIsEmpty();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String errorMessage = "";

                if (error instanceof TimeoutError) {
                    errorMessage = "Timeout. Check your connection";
                } else if (error instanceof NoConnectionError) {
                    errorMessage = "Please turn on your connectivity";
                } else if (error instanceof AuthFailureError) {
                    errorMessage = "Authentication Error";
                } else if (error instanceof ServerError) {
                    errorMessage = "Server Error";
                } else if (error instanceof NetworkError) {
                    errorMessage = "Network Error";
                } else if (error instanceof ParseError) {
                    errorMessage = "Parse Error";
                }

                listener.onRequestError(errorMessage);
            }
        });
        requestQueue.add(stringRequest);
    }
}
