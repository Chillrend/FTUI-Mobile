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
import com.rsypj.ftuimobile.connection.listener.ChatCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.ChatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.rsypj.ftuimobile.helper.CalenderHelper.getCalender;

public class ChatRequest {

    Context context;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    public ChatRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void requestChatDepan(final ChatCallBack listener){
        final ArrayList<ChatModel> chatModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.chat + URL.list + SharePref.getmInstance(context).getNim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Sukses!")){
                        JSONArray arr = job.getJSONArray("value");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String id = xx.getString("id");
                                String owner_id = xx.getString("owner_id");
                                String recipient_id = xx.getString("recipient_id");
                                String name = xx.getString("name");
                                String content = xx.getString("content");
                                long created = xx.getLong("created");

                                chatModels.add(new ChatModel(id, owner_id, recipient_id, name, content, created));
                            }
                            listener.onDataSetResutl(chatModels);
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

    public void requestChatDalem(final ChatCallBack listener){
        final ArrayList<ChatModel> chatModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.chat + URL.detail + SharePref.getmInstance(context).getNim() + "/" + Helper.sender, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Sukses!")){
                        JSONArray arr = job.getJSONArray("value");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String id = xx.getString("id");
                                String owner_id = xx.getString("owner_id");
                                String recipient_id = xx.getString("recipient_id");
                                String name = xx.getString("name");
                                String content = xx.getString("content");
                                String created = xx.getString("created");

                                chatModels.add(new ChatModel(id, owner_id, recipient_id, name, content, Long.parseLong(created)));
                            }
                            listener.onDataSetResutl(chatModels);
                        }
                    }else if (job.getString("message").equals("Gagal!")){
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

    public void postChat(final LoginCallBack listener){

        stringRequest = new StringRequest(Request.Method.POST, URL.postChat, new Response.Listener<String>() {
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

                params.put("pengirim", SharePref.getmInstance(context).getNim());
                params.put("penerima", Helper.sender);
                params.put("pesan", Helper.pesan);
                params.put("dibuat", getCalender()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
