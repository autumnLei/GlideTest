package com.lei.glidetest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Util;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    PhotoView mPhotoView;
    Button mButton;
    Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPhotoView = findViewById(R.id.photoview);
        mButton = findViewById(R.id.button);

        // 申请权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        //  可行
//        mPhotoView.setImageResource(R.drawable.pic02);

        //  不可行
        RequestOptions options = new RequestOptions().centerCrop().autoClone().lock();
        Log.i("main_", "onCreate: ");
        RequestOptions options1 = options.circleCrop();
        RequestOptions options2 = new RequestOptions().apply(options1);

        Glide.with(this)
                .load(R.drawable.pic02)
                .apply(options2)
                .into(mPhotoView);

        // 可行
//        View main = findViewById(R.id.ll_main);
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.drawable.pic02, options);
//        options.inSampleSize = 3;
//        options.inJustDecodeBounds = false;mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic02, options);
//        mPhotoView.setImageBitmap(mBitmap);
//        Log.d("out", "outHeight:"+options.outHeight+" width:"+options.outWidth+" inSampleSize:"+options.inSampleSize);
        //  可行 不过要按放大后界面比例加载
//            View main = findViewById(R.id.ll_main);
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(Uri.parse("content://com.estrongs.files/storage/emulated/0/pic/UD_03.jpg").getPath(), options);
//        public int getSampleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
//            options.inJustDecodeBounds = false;
//            mBitmap = BitmapFactory.decodeFile(Uri.parse("content://com.estrongs.files/storage/emulated/0/pic/UD_03.jpg").getPath(), options);
//            mPhotoView.setImageBitmap(mBitmap);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams posterParams = mPhotoView.getLayoutParams();
                posterParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                posterParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                mPhotoView.setLayoutParams(posterParams);
            }
        });


        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                startActivityForResult(intent, 2);
            }
        });

    }

    public int getSampleSize(BitmapFactory.Options options, int viewWidth, int viewHeight) {
        if (viewWidth == 0 || viewHeight == 0 || options == null) {
            return 1;
        }
        int widthScale = options.outWidth / viewWidth;
        int heightScale = options.outHeight / viewHeight;
        Log.d("out", "width==" + widthScale + " heightScale==" + heightScale);
        Toast.makeText(this, "width==" + widthScale + " heightScale==" + heightScale, Toast.LENGTH_LONG).show();
        return widthScale >= heightScale ? heightScale : widthScale;
    }



}
