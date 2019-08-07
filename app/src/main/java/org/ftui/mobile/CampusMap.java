package org.ftui.mobile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

public class CampusMap extends AppCompatActivity {
    public static final int PERMISSIONS_REQUEST_LOCATION = 99;
    MapView mapView;
    MyLocationNewOverlay marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context ctx = getApplicationContext();

        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        setContentView(R.layout.activity_campus_map);

        checkForLocationPermission();

        mapView = findViewById(R.id.mapView);
        mapView.setMultiTouchControls(true);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        IMapController mapController = mapView.getController();
        mapController.setZoom(19.0);
        GeoPoint startPoint = new GeoPoint(-6.36246, 106.82410);
        mapController.setCenter(startPoint);

        checkForLocationPermissionAndDrawUserLocation();
    }

    public void checkForLocationPermissionAndDrawUserLocation(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            this.marker = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
            this.marker.enableMyLocation();
            mapView.getOverlays().add(this.marker);
        }else{
            Toast.makeText(this, R.string.cant_access_location, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteUserLocationMarker(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            mapView.getOverlays().remove(this.marker);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();

        mapView.onResume();

        deleteUserLocationMarker();
        checkForLocationPermissionAndDrawUserLocation();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    public boolean checkForLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder permissionBuilder = new AlertDialog.Builder(this);
                permissionBuilder.setCancelable(true);
                permissionBuilder.setTitle(R.string.fine_location_permission_title);
                permissionBuilder.setMessage(R.string.fine_location_permission_description);
                permissionBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(CampusMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
                    }
                });

                AlertDialog permissionDialog = permissionBuilder.create();
                permissionDialog.show();
            } else {
                ActivityCompat.requestPermissions(CampusMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }else{
            return true;
        }
    }
}
