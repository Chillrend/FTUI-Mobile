package com.rsypj.ftuimobile.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rsypj.ftuimobile.R;
import com.rsypj.ftuimobile.base.SmartSipBaseActivity;

/**
 * Author By dhadotid
 * Date 11/07/2018.
 */
public class MapsActivity extends SmartSipBaseActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    ImageView ivBack;

    @Override
    public int getLayoutID() {
        return R.layout.activity_maps;
    }

    @Override
    public void initItem() {
        hideActionBar();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps_fragment);
        mapFragment.getMapAsync(this);

        ivBack = findViewById(R.id.maps_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng ftui = new LatLng(-6.3623951, 106.8240763);
        mMap.addMarker(new MarkerOptions().position(ftui).title("Fakultas Teknik Universitas Indonesia"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(ftui));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.3623951, 106.8240763), 14f));
    }
}
