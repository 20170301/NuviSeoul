package com.gangnam4bungate.nuviseoul.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.config.CODES;
import com.gangnam4bungate.nuviseoul.holder.PlanAdapter;
import com.gangnam4bungate.nuviseoul.model.AreaBaseModel;
import com.gangnam4bungate.nuviseoul.network.NetworkManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.mystory.commonlibrary.network.MashupCallback;
import com.mystory.commonlibrary.utils.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MashupCallback,
                                                                GoogleApiClient.ConnectionCallbacks,
                                                                GoogleApiClient.OnConnectionFailedListener,
                                                                LocationListener{

    private TextView mTv_title;
    private EditText mEt_title;
    private ImageView mIv_add;
    private ImageView mIv_search;
    private RecyclerView mRvPlan;
    private PlanAdapter mPlanAdapter;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    public final static int PERMISSION_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            mCurrentLocation = savedInstanceState.getParcelable(LOCATION);
        }

        setContentView(R.layout.activity_main);

        buildGoogleApiClient();
        mGoogleApiClient.connect();
        initLayout();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if(mGoogleApiClient.isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        if(mGoogleApiClient.isConnected()){
            getDeviceLocation();
        }
        super.onResume();
    }

    public void initLayout(){

        //mGpsInfo = new GpsInfo(this);
        //Location location = mGpsInfo.getLocation(this);

        ArrayList<String> list = new ArrayList<>();
        list.add("1");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        mTv_title = (TextView) toolbar.findViewById(R.id.tv_title);
        mEt_title = (EditText) toolbar.findViewById(R.id.et_title);
        if(mTv_title != null)
            mTv_title.setText(getString(R.string.main_title));
        setSupportActionBar(toolbar);
        /////////////////////////////
        mTv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTv_title.setVisibility(View.INVISIBLE);
                mEt_title.setVisibility(View.VISIBLE);
            }
        });
        ///////////////////////////////

        mIv_add = (ImageView) findViewById(R.id.iv_add);
        if(mIv_add != null){
            mIv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), PlanEditActivity.class));
                }
            });
        }
        mIv_search = (ImageView) findViewById(R.id.iv_search);
        if(mIv_search != null){
            mIv_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mEt_title_value = mEt_title.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("mEt_title_value", mEt_title_value);
                    startActivity(intent);
                }
            });
        }

        mPlanAdapter = new PlanAdapter();
        mPlanAdapter.bindData(list);
        mRvPlan = (RecyclerView) findViewById(R.id.rv_plan);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvPlan.setLayoutManager(layoutManager);
        mRvPlan.setAdapter(mPlanAdapter);

        NetworkManager.getInstance().requestAreaBaseListInfo(this, CODES.API_CONTENTTYPE.FESTIVAL);
    }

    protected synchronized void buildGoogleApiClient(){
        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        createLocationRequest();
    }

    private void getDeviceLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest,  this
            );
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_ACCESS_FINE_LOCATION);
        }
    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getDeviceLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION:
                updateLocationUI();
                return;
        }
    }


    private static final String CAMERA_POSITION = "camera_position";
    private static final String LOCATION = "location";

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if(mMap != null) {
            outState.putParcelable(CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(LOCATION, mCurrentLocation);
            super.onSaveInstanceState(outState);
        }
    }

    private void updateLocationUI() {
        if (mMap == null) return;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        getDeviceLocation();

        /*
        if(mCameraPosition != null){
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if(mCurrentLocation != null){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mCurrentLocation.getLatitude(),
                    mCurrentLocation.getLongitude()), 16));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(25.3, 34.3), 16));
        }*/

        Location location = Util.findGeoPoint(getApplicationContext(), "서울시 용산구 이촌동 동작대교");
        if(mMap != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 11));

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
        updateLocationUI();
    }

    @Override
    public void onMashupSuccess(JSONObject object, String requestCode) {

        if(requestCode != null && requestCode.equals(CODES.RequestCode.REQUEST_AREABASELIST)) {
            try {
                AreaBaseModel model = new Gson().fromJson(object.toString(), AreaBaseModel.class);
                if(model != null){

                }
            } catch (Exception e) {
                Log.e(CODES.TAG, "onMashupSucess Exception : " + e.getMessage());
            }

            Log.d(CODES.TAG, object.toString());
        }
    }

    @Override
    public void onMashupFail(VolleyError error, String requestCode) {
        if(requestCode != null && requestCode.equals(CODES.RequestCode.REQUEST_AREABASELIST)) {

            //Log.d(CODES.TAG, object.toString());
        }
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
