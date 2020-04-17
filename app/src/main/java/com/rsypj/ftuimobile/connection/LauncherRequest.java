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
import com.rsypj.ftuimobile.connection.listener.LauncherCallBack;
import com.rsypj.ftuimobile.connection.listener.PDFCallBack;
import com.rsypj.ftuimobile.connection.listener.PDFDetailCallBack;
import com.rsypj.ftuimobile.helper.Helper;
import com.rsypj.ftuimobile.helper.URL;
import com.rsypj.ftuimobile.model.DetailPDFModel;
import com.rsypj.ftuimobile.model.LauncherModel;
import com.rsypj.ftuimobile.model.ListBookModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Author By dhadotid
 * Date 1/07/2018.
 */
public class LauncherRequest {

    Context context;
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public LauncherRequest(Context context) {
        this.context = context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void onRequestDataLauncher(final LauncherCallBack listener){
        final ArrayList<LauncherModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.launcher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONArray array = object.getJSONArray("values");
                        if (array.length() > 0){
                            for (int i = 0; i < array.length(); i++){
                                JSONObject job = array.getJSONObject(i);

                                int id = job.getInt("id_laucher");
                                String judul = job.getString("judul");
                                String deskripsi = job.getString("deskripsi");
                                String gambar = job.getString("gambar");

                                data.add(new LauncherModel(id, judul, deskripsi, gambar));
                            }
                            listener.onDataSetResult(data);
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

                listener.onErrorRequest(errorMessage);
            }
        });
        requestQueue.add(stringRequest);
    }

    public void onRequestDataPDF(final PDFCallBack listener){
        final ArrayList<ListBookModel> data = new ArrayList<>();

        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.pdf + Helper.idlauncher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONArray array = object.getJSONArray("values");
                        if (array.length() > 0){
                            for (int i = 0; i < array.length(); i++){
                                JSONObject job = array.getJSONObject(i);

                                int id = job.getInt("id_pdf");
                                int idlauncher = job.getInt("id_laucher");
                                String nama = job.getString("nama");
                                String gambar = job.getString("gambar");

                                data.add(new ListBookModel(id, idlauncher, nama, gambar));
                            }
                            listener.onDataSetResult(data);
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

    public void onRequestDetailPDF(final PDFDetailCallBack listener){
        final ArrayList<DetailPDFModel> data = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.GET, URL.index + URL.pdfdetail + Helper.idpdf, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (object.getString("message").equals("Success!")){
                        JSONArray array = object.getJSONArray("values");
                        if (array.length() > 0){
                            for (int i = 0; i < array.length(); i++){
                                JSONObject job = array.getJSONObject(i);

                                String idpdfdet = job.getString("id_pdfdetail");
                                String id_pdf = job.getString("id_pdf");
                                String nama = job.getString("nama");
                                String gambar = job.getString("gambar");
                                String link = job.getString("link");

                                data.add(new DetailPDFModel(idpdfdet, id_pdf, nama, gambar, link));
                            }
                            listener.onDataSetResult(data);
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
}
