package com.rsypj.ftuimobile.connection;

import android.app.ProgressDialog;
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
import com.rsypj.ftuimobile.connection.listener.ListPendingPostCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.connection.listener.TimelineCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ListPendingPostModel;
import com.rsypj.ftuimobile.model.TimelineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimelineRequest {

    ProgressDialog progressDialog;
    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    public TimelineRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestTimelineData(final TimelineCallBack listener){
        final ArrayList<TimelineModel> timelineModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.keluhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPage = jsob.getString("next_page_url");
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

    public void requestTimelineDataLoadMore(final TimelineCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<TimelineModel> timelineModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPage, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPage = jsob.getString("next_page_url");
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
        exec();
    }


    public void requestPendingPost(final ListPendingPostCallBack listener){
        final ArrayList<ListPendingPostModel> pendingPost = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.manager, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPagePendingPost = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPost.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPost);
                        }else {
                            listener.onDataIsEmpty();
                        }
                    }
                    else {
                        listener.onDataIsEmpty();
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

    public void requestPendingPostLoadMore(final ListPendingPostCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<ListPendingPostModel> pendingPostModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPagePendingPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPagePendingPost = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPostModels.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPostModels);
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
        exec();
    }

    public void updatePost(final LoginCallBack listener){
        progressDialog = ProgressDialog.show(context, "Update Data", "Please wait while updateing the data", false);
        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.manager + "/" + URL.update + Helper.idKeluhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
//                        JSONObject job = object.getJSONObject("status");

                        listener.onRequestSuccess("Success");
                    }else {
//                        listener.onError("Error");
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
                listener.onError(errorMessage);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("is_approved", Helper.is_approved+"");
                return params;
            }
        };
        exec();
    }

    public void requestPendingForSurveyor(final ListPendingPostCallBack listener){
        final ArrayList<ListPendingPostModel> pendingPost = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.surveyor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageSurveyor = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPost.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPost);
                        }else {
                            listener.onDataIsEmpty();
                        }
                    }else {
                        listener.onDataIsEmpty();
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

    public void requestPendingPostSurveyorLoadMore(final ListPendingPostCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<ListPendingPostModel> pendingPostModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageSurveyor, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageSurveyor = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPostModels.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPostModels);
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
        exec();
    }

    public void requestCompleteKeluhan(final ListPendingPostCallBack listener){
        final ArrayList<ListPendingPostModel> pendingPost = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.dekanComplete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
                        URL.nextPageDekanComplete = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPost.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPost);
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

    public void requestDekanCompleteLoadMore(final ListPendingPostCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<ListPendingPostModel> pendingPostModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageDekanComplete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageDekanComplete = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPostModels.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPostModels);
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
        exec();
    }

    public void requestAllKeluhan(final ListPendingPostCallBack listener){
        final ArrayList<ListPendingPostModel> pendingPost = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.rektorat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
                        URL.nextPageRektorat = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPost.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPost);
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

    public void requestAllCompleteLoadMore(final ListPendingPostCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<ListPendingPostModel> pendingPostModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageRektorat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageRektorat = jsob.getString("next_page_url");
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
                                String created_time = xx.getString("created_at");
                                String updated_time = xx.getString("updated_at");

                                pendingPostModels.add(new ListPendingPostModel(idKeluhan, nim, desc, foto, timePost, status, name, kategori, icon, location, created_time, updated_time));
                            }
                            listener.onDataSetResutl(pendingPostModels);
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
        exec();
    }

    private void exec(){
        requestQueue.add(stringRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                if (progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }
}
