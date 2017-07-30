package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gangnam4bungate.nuviseoul.R;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    public void onClick1(View v){
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        startActivity(intent);
    }
}
