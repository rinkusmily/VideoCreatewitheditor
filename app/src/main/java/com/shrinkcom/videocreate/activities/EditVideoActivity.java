package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.shrinkcom.videocreate.LoginActivity;
import com.shrinkcom.videocreate.MakeVideoActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.helper.LocaleHelper;

public class EditVideoActivity extends AppCompatActivity {
    ImageView imgmusic,imglogin;
    Toolbar toolbar;
    VideoView  vv_video;
    String str_video;
    LinearLayout lintrim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);
        imgmusic=findViewById(R.id.img_music);
        imglogin=findViewById(R.id.img_login);
        toolbar=findViewById(R.id.mToolbar);
        vv_video = (VideoView) findViewById(R.id.vv_video);
        lintrim =  findViewById(R.id.trim);
        str_video = getIntent().getStringExtra("video");
        Log.e("str_video","++"+str_video);

        vv_video.setVideoPath(str_video);
        vv_video.start();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imgmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(EditVideoActivity.this, MusicActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        lintrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(EditVideoActivity.this, trim_video.class);
                trim_video.addpath( str_video );
                startActivity(intent1);
                finish();
            }
        });


        imglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(EditVideoActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });


    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("languagelog", "language "+base);
    }
}
