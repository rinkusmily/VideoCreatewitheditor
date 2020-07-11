package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shrinkcom.videocreate.LoginActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.helper.LocaleHelper;

public class PremiumActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnbuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premium);
        btnbuy=findViewById(R.id.btn_buy);
        toolbar=findViewById(R.id.mToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(PremiumActivity.this, LoginActivity.class);
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
