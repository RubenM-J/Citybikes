package com.example.citybikes;

import static com.example.citybikes.BikeActivity.EXTRA_STATIONS;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.citybikes.utils.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static ArrayList<Station> markers = new ArrayList<>();
    private boolean permissionDenied = false;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapsfragment);
        mapFragment.getMapAsync(this);

        try {
            Intent intent = getIntent();
            markers = intent.getParcelableArrayListExtra(EXTRA_STATIONS);
        }
        catch (Exception ex){
            Toast.makeText(MapsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

        ImageButton find = findViewById(R.id.findClosest);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,
                        ListActivity.class);
                if (markers != null){
                    markers.clear();
                }
                startActivity(intent);
            }
        });

        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this,
                        Dashboard.class);
                startActivity(intent);
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (map != null) {
                map.setMyLocationEnabled(true);
            }
        } else {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Current location", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.bike);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap bikeMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        map = googleMap;
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        enableMyLocation();

        if (markers != null && !markers.isEmpty()){
            for ( Station station : markers) {
                if (station.latitude != 0 && station.longitude != 0){
                    LatLng loc = new LatLng(station.latitude, station.longitude);
                    map.addMarker(new MarkerOptions()
                            .position(loc)
                            .title(station.name)
                            .snippet("Free: " + station.freeBikes + "|Empty: " + station.emptySlots)
                            .icon(BitmapDescriptorFactory.fromBitmap(bikeMarker)));
                }
            }
            LatLng location = new LatLng(markers.get(1).latitude, markers.get(1).longitude);
            map.moveCamera(CameraUpdateFactory.newLatLng(location));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12));
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            permissionDenied = true;
        }
    }
    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}