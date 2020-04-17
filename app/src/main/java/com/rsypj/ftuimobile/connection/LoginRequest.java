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
import com.rsypj.ftuimobile.connection.listener.GetUserDataCallBack;
import com.rsypj.ftuimobile.connection.listener.LoginCallBack;
import com.rsypj.ftuimobile.connection.listener.UnverifiedUserCallBack;
import com.rsypj.ftuimobile.constant.SharePref;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.UnverifiedUserModel;
import com.rsypj.ftuimobile.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest {

    ProgressDialog progressDialog;
    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public LoginRequest(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);


    }

    public void loginChecker(final LoginCallBack listener){

        progressDialog = ProgressDialog.show(context, "Login", "Please wait while login", false);

        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("status").equals("69")){
                        listener.onRequestSuccess("69");
                    }else if (object.getString("status").equals("201")){
                        listener.onRequestSuccess("Error");
                    }else {
                        JSONObject job = object.getJSONObject("status");
                        UserModel userModel = new UserModel(job.getString("NIM"),
                                job.getString("name"),
                                job.getString("email"),
                                job.getInt("roles"));

                        SharePref.getmInstance(context).userLogin(userModel);

                        listener.onRequestSuccess("Success");
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

                params.put("NIM", Helper.NIM);
                params.put("password", Helper.password);
                return params;
            }
        };
        exec();
    }

    public void updateProfil(final LoginCallBack listener){
        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.updateAccount + SharePref.getmInstance(context).getNim(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getString("message").equals("201")){
                        JSONObject job = object.getJSONObject("value");
                        UserModel userModel = new UserModel(job.getString("NIM"),
                                job.getString("name"),
                                job.getString("email"),
                                job.getInt("roles"));

                        SharePref.getmInstance(context).userLogin(userModel);

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

//                params.put("NIM", Helper.NIM);
                params.put("name", Helper.name);
                params.put("email", Helper.email);
                params.put("roles", Helper.role);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void registerRequest(final LoginCallBack listener){

        progressDialog = ProgressDialog.show(context, "Register", "Please wait while register", false);

        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getString("status").equals("gagal")){
//                        JSONObject job = object.getJSONObject("value");
//                        UserModel userModel = new UserModel(job.getString("NIM"),
//                                job.getString("name"),
//                                job.getString("email"),
//                                job.getInt("roles"));

//                        SharePref.getmInstance(context).userLogin(userModel);

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("NIM", Helper.NIM);
                params.put("name", Helper.name);
                params.put("password", Helper.password);
                params.put("roles", Helper.role);
                params.put("email", Helper.email);
                return params;
            }
        };
        exec();
    }

    public void requestallUser(final GetUserDataCallBack listener){
        final ArrayList<UserModel> userModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.users, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
//                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
//                        URL.nextPageUser = jsob.getString("next_page_url");
                        JSONArray arr = job.getJSONArray("values");
//                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String nim = xx.getString("NIM");
                                String name = xx.getString("name");
                                String email = xx.getString("email");
                                int roles = xx.getInt("roles");

                                userModels.add(new UserModel(nim, name, email, roles));
                            }
                            listener.onDataSetResutl(userModels);
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

    public void requestallUserLoadMore(final GetUserDataCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<UserModel> userModels = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (job.getString("message").equals("Success!")){
                        JSONObject jsob = job.getJSONObject("values");
//                        URL.page = jsob.getInt("current_page") + 1;
                        URL.nextPageUser = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
//                        JSONArray arr = job.getJSONArray("values");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String nim = xx.getString("NIM");
                                String name = xx.getString("name");
                                String email = xx.getString("email");
                                int roles = xx.getInt("roles");

                                userModels.add(new UserModel(nim, name, email, roles));
                            }
                            listener.onDataSetResutl(userModels);
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

    public void onRequestUnverifiedUser(final UnverifiedUserCallBack listener){
        final ArrayList<UnverifiedUserModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.unverifiedUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONObject jsob = object.getJSONObject("values");
                        URL.nextPageVerifiedUser = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String nim = xx.getString("NIM");
                                String nama = xx.getString("name");
                                String email = xx.getString("email");

                                data.add(new UnverifiedUserModel(nim, nama, email));
                            }
                            listener.onDataSetResult(data);
                        }else {
                            listener.onDataNotFound();
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

                listener.onErrorRequest(errorMessage);
            }
        });
        requestQueue.add(stringRequest);
    }

    public void onRequestUnverifiedUserLoadMore(final UnverifiedUserCallBack listener){
        progressDialog = ProgressDialog.show(context, "Load Data", "Please wait while load more the data", false);
        final ArrayList<UnverifiedUserModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.nextPageVerifiedUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONObject jsob = object.getJSONObject("values");
                        URL.nextPageVerifiedUser = jsob.getString("next_page_url");
                        JSONArray arr = jsob.getJSONArray("data");
                        if (arr.length() > 0){
                            for (int i = 0; i < arr.length(); i++){
                                JSONObject xx = arr.getJSONObject(i);

                                String nim = xx.getString("NIM");
                                String nama = xx.getString("name");
                                String email = xx.getString("email");

                                data.add(new UnverifiedUserModel(nim, nama, email));
                            }
                            listener.onDataSetResult(data);
                        }else {
                            listener.onDataNotFound();
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

                listener.onErrorRequest(errorMessage);
            }
        });
        exec();
    }

    public void onSubmitData(final LoginCallBack listener){
        progressDialog = ProgressDialog.show(context, "Submit Data", "Please wait while submit the data", false);
        final ArrayList<UnverifiedUserModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.POST, URL.index + URL.users +"/" + URL.verif, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
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
                params.put("NIM", Helper.NIM);
                params.put("status", "1");
                params.put("email", Helper.email);
                return params;
            }
        };
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
