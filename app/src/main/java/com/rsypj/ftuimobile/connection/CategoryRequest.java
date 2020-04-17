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
import com.rsypj.ftuimobile.connection.listener.CategoryCallBack;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.CategoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CategoryRequest {

    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    public CategoryRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestCategoryData(final CategoryCallBack listener){
        final ArrayList<CategoryModel> categoryModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.kategori, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                int idKategori = xx.getInt("id_kategori");
                                String kategori = xx.getString("kategori");
                                String icon = xx.getString("icon");

                                categoryModels.add(new CategoryModel(idKategori, kategori, icon));
                                listener.onDataSetResutl(categoryModels);
                            }
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
