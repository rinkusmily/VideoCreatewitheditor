package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.shrinkcom.videocreate.LoginActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.adapter.MusicAdapter;
import com.shrinkcom.videocreate.adapter.MyAdapter;
import com.shrinkcom.videocreate.helper.LocaleHelper;

public class MusicActivity extends AppCompatActivity {
  Toolbar toolbar;
  ImageView imglogin;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        toolbar=findViewById(R.id.mToolbar);
        imglogin=findViewById(R.id.img_login);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.pager);
        tabLayout.addTab(tabLayout.newTab().setText("DOWNLOADS"));
        tabLayout.addTab(tabLayout.newTab().setText("MY FILES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        toolbar=findViewById(R.id.mToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });




        final MusicAdapter adapter = new MusicAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        imglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MusicActivity.this, LoginActivity.class);
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
