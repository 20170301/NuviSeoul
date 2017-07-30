package com.gangnam4bungate.nuviseoul.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gangnam4bungate.nuviseoul.R;
import com.gangnam4bungate.nuviseoul.ui.common.CommonActivity;

import java.io.File;

/**
 * Created by wsseo on 2017. 7. 9..
 */

public class PhotoViewActivity extends CommonActivity {
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;

    private Uri mImeageCaptureUri;
    private ImageView iv_UserPhoto;
    private int id_view;
    private String absolutePath;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);

        Button btn_addPicture = (Button)this.findViewById(R.id.btn_UploadPicture);
        btn_addPicture.setOnClickListener((View.OnClickListener) this);
    }

    public void onClickAdd(View v) {

        id_view = v.getId();

        DialogInterface.OnClickListener albumListener = null;
        DialogInterface.OnClickListener cameraListener = null;
        DialogInterface.OnClickListener calcelListener = null;

        if (id_view == R.id.btn_UploadPicture) {

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

        }


        new AlertDialog.Builder(this)
                .setPositiveButton("핸드폰 갤러리", albumListener)
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
