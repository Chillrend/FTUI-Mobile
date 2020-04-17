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
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingRequest {

    ProgressDialog progressDialog;
    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public RatingRequest(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void postRating(final LoginCallBack listener){

        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.rating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Berhasil!")){

                        listener.onRequestSuccess("Success");
                    }else {
                        listener.onRequestSuccess("Error");
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

                params.put("idkeluhan", Helper.idKeluhanDetail+"");
                params.put("nim", SharePref.getmInstance(context).getNim());
                params.put("rating", Helper.rating+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
