package org.ftui.mobile.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import es.dmoral.toasty.Toasty;
import org.ftui.mobile.KeluhanDetail;
import org.ftui.mobile.adapter.BasicComplaintCardViewAdapter;
import org.ftui.mobile.fragment.EKeluhan;
import org.ftui.mobile.model.keluhan.Keluhan;
import org.ftui.mobile.model.keluhan.Metum;
import org.ftui.mobile.model.keluhan.Results;
import org.ftui.mobile.model.keluhan.Ticket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetKeluhanIntoRecyclerView implements BasicComplaintCardViewAdapter.OnKeluhanClicklistener {
    private Metum responseMeta =  null;
    private List<Ticket> keluhan_data_unmodified = null;
    private List<Ticket> keluhan_data = null;
    private String url;
    private String baseImgUrl;
    private String userToken;
    private int nowOnPage;
    private int totalPage;
    private int totalData;
    private RecyclerView rv;
    private ApiService service;
    private ShimmerFrameLayout sv;
    private Context ctx;
    private EndlessRecyclerViewScrollListener scrollListener;
    private BasicComplaintCardViewAdapter adapter;
    private BasicComplaintCardViewAdapter.OnKeluhanClicklistener onKeluhanClicklistener;

    public GetKeluhanIntoRecyclerView(String url, String userToken, RecyclerView rv, ShimmerFrameLayout sv, Context ctx) {
        this.url = url;
        this.userToken = userToken;
        this.rv = rv;
        this.sv = sv;
        this.ctx = ctx;
        this.service = ApiCall.getClient().create(ApiService.class);
        this.nowOnPage = 1;
        this.keluhan_data_unmodified = new ArrayList<>();
        this.onKeluhanClicklistener = this;

    }


    public void loadNextDataFromApi(int page){
        String urls = url + "page=" + page;

        Map<String, Object> notifyApi = getKeluhanToRv(urls, false,false);
        Metum responseMeta = (Metum) notifyApi.get("meta");

    }

    public Map<String,Object> getKeluhanToRv(String urls, Boolean initial, Boolean startNewSearch){

        Call<Keluhan> call = service.getAllKeluhan(urls, "application/json", "Bearer " + userToken);

        call.enqueue(new Callback<Keluhan>() {
            @Override
            public void onResponse(Call<Keluhan> call, Response<Keluhan> response) {
                if(response.errorBody() != null){
                    Toasty.error(ctx, "Tidak dapat mengambil keluhan, silahkan coba lagi").show();
                    Log.d("ERROR", "onResponse: " + response.errorBody().toString());
                    return;
                }
                Keluhan parent_of_parent = response.body();
                responseMeta = parent_of_parent.getMeta().get(0);
                totalPage = Integer.parseInt(responseMeta.getPageTotal());
                totalData = Integer.parseInt(responseMeta.getTotal());
                baseImgUrl = parent_of_parent.getUrlimg();
                Results what_the_fick_is_even_this_for = parent_of_parent.getResults();
                keluhan_data = what_the_fick_is_even_this_for.getTicket();

                if(startNewSearch != null && startNewSearch){
                    nowOnPage = 1;
                    url = urls;
                    keluhan_data_unmodified.clear();
                    keluhan_data_unmodified.addAll(keluhan_data);
                    adapter.notifyDataSetChanged();
                    scrollListener.resetState();
                }else if (startNewSearch != null){
                    keluhan_data_unmodified.addAll(keluhan_data);
                    nowOnPage = Integer.parseInt(responseMeta.getPage());
                    adapter.notifyDataSetChanged();
                }


                if(initial){
                    url = urls;
                    keluhan_data_unmodified = keluhan_data;
                    adapter = new BasicComplaintCardViewAdapter(keluhan_data_unmodified, ctx, baseImgUrl, onKeluhanClicklistener);
                    rv.setAdapter(adapter);
                    LinearLayoutManager llManager = new LinearLayoutManager(ctx);
                    rv.setLayoutManager(llManager);
                    sv.stopShimmer();
                    sv.setVisibility(View.GONE);
                    scrollListener = new EndlessRecyclerViewScrollListener(llManager) {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                            if(nowOnPage < totalPage){
                                int requestNextPage = nowOnPage+1;
                                loadNextDataFromApi(requestNextPage);
                            }
                        }
                    };
                    rv.setOnScrollListener(scrollListener);
                }


            }

            @Override
            public void onFailure(Call<Keluhan> call, Throwable t) {
                Toasty.error(ctx, "Tidak dapat mengambil data keluhan, silahkan coba lagi").show();
                t.printStackTrace();
            }
        });

        Map<String, Object> map = new HashMap<>();
        map.put("meta", responseMeta);
        map.put("data", keluhan_data);
        map.put("baseImgUrl", baseImgUrl);
        return map;
    }

    @Override
    public void onKeluhanClick(int pos) {
        Ticket tit = keluhan_data_unmodified.get(pos);
        Intent i = new Intent(ctx, KeluhanDetail.class);
        i.putExtra("baseImgUrl", baseImgUrl);
        i.putExtra("keluhan_data", tit);
        ctx.startActivity(i);
    }
}
