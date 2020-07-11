package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.helper.LocaleHelper;

public class PreviewActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imgmusic;
    TextView tvdone,btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        toolbar=findViewById(R.id.mToolbar);
        imgmusic=findViewById(R.id.img_music);
        tvdone=findViewById(R.id.btn_done);
        btnsave=findViewById(R.id.btn_save);

        imgmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PreviewActivity.this, MusicActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PreviewActivity.this, PreviewDialogueAcitivity.class);
                startActivity(intent1);

            }
        });

        tvdone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PreviewActivity.this, ShareAcitvity.class);
                startActivity(intent1);
                finish();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
