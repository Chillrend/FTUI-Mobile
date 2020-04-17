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
import com.rsypj.ftuimobile.connection.listener.PhoneCallBack;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.NoTelphoneModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PhoneRequest {

    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public PhoneRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestPhoneData(final PhoneCallBack listener){
        final ArrayList<NoTelphoneModel> noTelphoneModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.phone, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject jab = arr.getJSONObject(i);

                                String name = jab.getString("name");
                                String nomortelfone = jab.getString("phone");

                                noTelphoneModels.add(new NoTelphoneModel(name,"tel:"+nomortelfone));
                            }
                            listener.onDataSetResult(noTelphoneModels);
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
