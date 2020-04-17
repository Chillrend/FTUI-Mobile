package com.rsypj.ftuimobile.connection;

import android.content.Context;
import android.util.Log;

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
import com.rsypj.ftuimobile.connection.listener.ChartCallBack;
import com.rsypj.ftuimobile.connection.listener.KeluhanDetailCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.PieChartModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KeluhanDetailRequest {

    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    int idKeluhanDetail;
    String name;
    String foto;
    String location;
    String desc;
    String rating = "1";
    double totRating;
    int jumUser;
    double hasilRating;

    public KeluhanDetailRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestDetailKeluhan(final KeluhanDetailCallBack listener){

        stringRequest = new StringRequest(Request.Method.GET, URL.index+ URL.rating + "/" + Helper.idKeluhan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    JSONObject xx = job.getJSONObject("value");

                    idKeluhanDetail = xx.getInt("id_keluhandetail");
                    name = xx.getString("name");
                    foto = xx.getString("foto");
                    location = xx.getString("lokasi");
                    desc = xx.getString("deskripsi");
//                    totRating = xx.getInt("rating");
                    totRating = job.getDouble("rating");

                    listener.onDataSetResult(idKeluhanDetail, name, foto, location, desc, totRating+"");
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
        });
        requestQueue.add(stringRequest);
    }

    public void onRequestChartData(final ChartCallBack listener){
        final ArrayList<PieChartModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.chart, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONArray array = object.getJSONArray("value");
                        if (array.length() > 0){
                            Log.d("ARRAY", array.length()+"");
                            for (int i = 0; i < array.length(); i++){
                                JSONObject job = array.getJSONObject(i);

                                int status = job.getInt("name");
                                String name = "";
                                if (status == 0){
                                    name = "Publish";
                                }else if (status == 1){
                                    name = "Postpone";
                                }else if (status == 2){
                                    name = "Complete";
                                }
                                //int name = job.getInt("name");

                                float persentase = Float.valueOf(job.getString("persen"));
                                int jumlah = job.getInt("jumlah");
                                data.add(new PieChartModel(i, name, persentase, jumlah));
                            }
                            listener.onDataSetResult(data);
                        }else {
                            listener.onDataIsEmpty();
                        }
                    }else {

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
