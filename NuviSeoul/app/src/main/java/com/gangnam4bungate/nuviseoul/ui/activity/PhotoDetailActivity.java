package com.gangnam4bungate.nuviseoul.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gangnam4bungate.nuviseoul.R;

import java.io.File;

public class PhotoDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    private Uri mImeageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absolutePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        Button btn_addPicture = (Button)this.findViewById(R.id.btn_UploadPicture);
        btn_addPicture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("[[TEST]]","들어온다!!");

        DialogInterface.OnClickListener albumListener = null;
        DialogInterface.OnClickListener cameraListener = null;
        DialogInterface.OnClickListener calcelListener = null;

        cameraListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakePhotoAction();
            }
        };

        albumListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doTakeAlbumAction();
            }
        };

        calcelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setNeutralButton("앨범", albumListener)
        .setPositiveButton("카메라", cameraListener)
        .setNegativeButton("취소", calcelListener)
        .show();

    }

    private void doTakeAlbumAction() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,PICK_FROM_ALBUM);
    }

    private void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String url = "tmp_"+String.valueOf(System.currentTimeMillis())+".jsp";
        mImeageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mImeageCaptureUri);
        startActivityForResult(intent,PICK_FROM_CAMERA);
    }

}
