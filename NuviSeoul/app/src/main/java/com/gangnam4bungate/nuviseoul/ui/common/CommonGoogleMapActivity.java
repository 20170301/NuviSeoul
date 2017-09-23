package com.gangnam4bungate.nuviseoul.ui.common;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mystory.commonlibrary.map.DirectionFinder;
import com.mystory.commonlibrary.map.DirectionFinderListener;
import com.mystory.commonlibrary.map.Route;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hschoi on 2017. 8. 06..
 */

public class CommonGoogleMapActivity extends FragmentActivity implements OnMapReadyCallback,DirectionFinderListener {
    protected int mZoonSize=14;
    protected int mViewType=0;
    protected GoogleMap mMap;
    protected List<Route> mRoutes=new ArrayList<Route>();
    private LatLng mLastedMarkLatLng;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mRoutes.clear();
        super.onCreate(savedInstanceState);
        //onCreateView(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /* SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
    }
    @Override
    public void onDirectionFinderStart() {
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

        //중복데이터 제거
        boolean bInsert=false;
        for( Route route : routes) {
            bInsert=true;
            for( Route tmpRoute : mRoutes)  {
                if((route.startAddress==tmpRoute.startAddress)
                       &(route.endAddress==tmpRoute.endAddress) ) {
                    bInsert=false;
                    break;
                }
            }
            if(bInsert) {
                mRoutes.add(route);
            }
        }

        LatLng endLocation=null;
        for( Route route : mRoutes) {
            //---------------------------------------------------------------------------------
            /*if((route.startLocation.latitude==route.endLocation.latitude)
                   &(route.startLocation.longitude==route.endLocation.longitude) ) {*/
             if((route.startLocation==null)
                     ||(route.endLocation==null)){
                mMap.addMarker(new MarkerOptions().position(route.startLocation)
                        .title(route.startAddress));
            } else {
                mMap.addMarker(new MarkerOptions().position(route.startLocation)
                        .title(route.startAddress));
                //---------------------------------------------------------------------------------
                mMap.addMarker(new MarkerOptions().position(route.endLocation)
                        .title(route.endAddress));
                //---------------------------------------------------------------------------------
                PolylineOptions polylineOptions=new PolylineOptions()
                        .geodesic(true)
                        .color(Color.BLUE)
                        .width(5)
                        ;
                polylineOptions.add(route.startLocation);

                for(int i=0; i < route.points.size();i++)
                    polylineOptions.add(route.points.get(i));

                polylineOptions.add(route.endLocation);

                mMap.addPolyline(polylineOptions);
                endLocation = route.endLocation;
            }
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLocation,mZoonSize/*16*/));
       /* Iterator<Route> iterator=routes.iterator();
        while(iterator.hasNext()) {
            Route  item=(Route)iterator.next();

            mMap.addPolyline(new PolylineOptions()
                    .color(Color.BLUE)
                    .width(5)
                    .geodesic(true)
                    .add(item.startLocation)
                    .add(item.endLocation)
            );

            Toast.makeText(getApplicationContext(),item.startLocation.toString(), Toast.LENGTH_LONG)
                    .show();
        }*/
       /* String sOrigin="";
        sOrigin.format("<<<%f,%f >>",(float)mLastedMarkLatLng.latitude,(float)mLastedMarkLatLng.longitude);
        String sDestination=latLng.toString();
        Toast.makeText(getApplicationContext(),sOrigin, Toast.LENGTH_LONG)
                .show();*/
    }

    private  void sendRequestWithDirection(LatLng ltOrigin,LatLng ltDestination)
    {
        //String strOrigin="37.509590,127.013767";
        //String strDestination="37.493909,127.014278";
        String strOrigin=ltOrigin.latitude+","+ltOrigin.longitude;
        String strDestination=ltDestination.latitude+","+ltDestination.longitude;
        if(strOrigin.isEmpty())
        {
            Toast.makeText(this,"Please, input origin address.",Toast.LENGTH_SHORT).show();
            return ;
        }

        if(strDestination.isEmpty())
        {
            Toast.makeText(this,"Please, input destination address.",Toast.LENGTH_SHORT).show();
            return ;
        }

        try
        {
            new DirectionFinder(this,strOrigin,strDestination).execute();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        //Camera Zoom
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.552186, 127.0),13));
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

        if(false) {
            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);
            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //LatLng seoul = new LatLng(37.52, 127.0);
            LatLng seoul = new LatLng(37.552186, 127.0);
            mLastedMarkLatLng = seoul;
            mMap.addMarker(new MarkerOptions().position(seoul)
                            .title("Marker in Seoul")
                                           /*.icon(BitmapDescriptorFactory.fromResource(com.google.android.gms.R.drawable.push_in))*/
            );
        }
        //
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        //1. 좌표 설정
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(seoul));

        //2 좌표와 카메라 zoom 설정
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,13));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.552186, 127.0),mZoonSize/*13*/));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( new LatLng(-18.142, 178.431), 2));
        //moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.86997, 151.2089), 18));//실내지도
    /*-------------------------------------------------------------------------------------------------*/
         // Other supported types include: MAP_TYPE_NORMAL,
        // MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NONE);//화면에 맵이 사라짐
    /*-------------------------------------------------------------------------------------------------*/
        //스타일 지정
        // Customise the styling of the base map using a JSON object defined
        // in a raw resource file.
       /* MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_json);
        mMap.setMapStyle(style);*/
/*-------------------------------------------------------------------------------------------------*/
        //사요자 지정 마커 사용에  - 상속 받는 쪽에서 처리
        // You can customize the marker image using images bundled with
        // your app, or dynamically generated bitmaps.
        /*mMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(41.889, -87.622)));*/
/*-------------------------------------------------------------------------------------------------*/
        //1 Map Click
        if(mViewType==1) {
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng arg0) {
                 /*   String textTitle = "[Map Click] latitude ="
                            + arg0.latitude + ", longitude ="
                            + arg0.longitude;*/
          /*  Toast.makeText(getApplicationContext(), textTitle, Toast.LENGTH_LONG)
                    .show();*/
                    //Marker 추가
                    LatLng latLng=new LatLng(arg0.latitude,arg0.longitude);
                    //MarkerOptions newMarker= new MarkerOptions().position(latLng).title(textTitle);
                    //mMap.addMarker(new MarkerOptions().position(latLng).title(textTitle));
                    //mMarks.add(newMarker);
                    //1. 폴리라인
                    // Polylines are useful for marking paths and routes on the map.
               /* mMap.addPolyline(new PolylineOptions().geodesic(true)
                        .add(new LatLng(-33.866, 151.195))  // Sydney
                        .add(new LatLng(-18.142, 178.431))  // Fiji
                        .add(new LatLng(21.291, -157.821))  // Hawaii
                        .add(new LatLng(37.423, -122.091))  // Mountain View
                );*/
               /*
               PolylineOptions polyline_options = new PolylineOptions()
                        .addAll(arraylist_lat_lon).color(Color.GREEN).width(2);
                polyline = googleMap.addPolyline(polyline_options);
               */
                    //2점 연결 - 시작점 , 마지막점
                /*mMap.addPolyline(new PolylineOptions()
                        .color(Color.BLUE)
                        .width(5)
                        .geodesic(true)
                        .add(mLastedMarkLatLng)
                        .add(latLng)
                        //.add(new LatLng(arg0.latitude,arg0.longitude))
                );

                String sOrigin="";
                sOrigin.format("<<<%f,%f >>",(float)mLastedMarkLatLng.latitude,(float)mLastedMarkLatLng.longitude);
                String sDestination=latLng.toString();
                Toast.makeText(getApplicationContext(),sOrigin, Toast.LENGTH_LONG)
                        .show();
                */
                if(mLastedMarkLatLng==null) {
                    mLastedMarkLatLng = latLng;
                }
                sendRequestWithDirection(mLastedMarkLatLng, latLng);
                mLastedMarkLatLng = latLng;
                }
            });
        }


        //2. 클릭을 오래 했을때
        if(mViewType==1) {
            mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                public void onMapLongClick(LatLng point) {
                    String text = "[Map LongClick] latitude ="
                            + point.latitude + ", longitude ="
                            + point.longitude;
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    /*-------------------------------------------------------------------------------------------------*/
        //2.Map marker Click
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                if(mViewType==1) {
                    /*String text = "[Map Marker Click] latitude ="
                            + marker.getPosition().latitude + ", longitude ="
                            + marker.getPosition().longitude;
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG)
                            .show();*/
                    //mMap.addPolyline(new PolylineOptions());
                    mMap.clear();
                    mRoutes.clear();
                }else {
                    //상세 보여주기
                }
                return false;
            }
        });
    /*-------------------------------------------------------------------------------------------------*/
    }
}