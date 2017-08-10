package com.gangnam4bungate.nuviseoul.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by hschoi on 2017. 8. 06..
 */

public class CommonGoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {
    protected GoogleMap mMap;
    @Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
   */
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //onCreateView(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        LatLng seoul = new LatLng(37.52, 127.0);
        mMap.addMarker(new MarkerOptions().position(seoul).title("Marker in Seoul"));
        //1. 좌표 설정
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

        //2 좌표와 카메라 zoom 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,13));
    /*-------------------------------------------------------------------------------------------------*/
        //1 Map Click
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                String textTitle = "[Map Click] latitude ="
                        + arg0.latitude + ", longitude ="
                        + arg0.longitude;
          /*  Toast.makeText(getApplicationContext(), textTitle, Toast.LENGTH_LONG)
                    .show();*/
                //Marker 추가
                LatLng latLng=new LatLng(arg0.latitude,arg0.longitude);
                mMap.addMarker(new MarkerOptions().position(latLng).title(textTitle));
            }
        });

        //2. 클릭을 오래 했을때
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            public void onMapLongClick(LatLng point) {
                String text = "[Map LongClick] latitude ="
                        + point.latitude + ", longitude ="
                        + point.longitude;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
            }
        });
    /*-------------------------------------------------------------------------------------------------*/
        //2.Map marker Click
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                String text = "[Map Marker Click] latitude ="
                        + marker.getPosition().latitude + ", longitude ="
                        + marker.getPosition().longitude;
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                        .show();
                return false;
            }
        });
    /*-------------------------------------------------------------------------------------------------*/
    }
}