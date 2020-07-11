package com.shrinkcom.videocreate.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.shrinkcom.videocreate.DashboardActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.adapter.DashboardAdapter;
import com.shrinkcom.videocreate.adapter.MyAdapter;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.SessionManager;

public class StudioActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    ImageView imglogin,update_profile;
    String userid;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studio);
        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        update_profile=findViewById(R.id.update_profile);

        viewPager=(ViewPager)findViewById(R.id.pager);
        imglogin=findViewById(R.id.img_login);
        tabLayout.addTab(tabLayout.newTab().setText("Camera"));
        tabLayout.addTab(tabLayout.newTab().setText("Photo/Video"));
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        toolbar=findViewById(R.id.mToolbar);
        sessionManager = new SessionManager(this);
        userid=sessionManager.getStoreString(SessionManager.KEY_ID);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        try {
            if (userid.equals("")) {
                imglogin.setVisibility(View.VISIBLE);
            }
            else {
                update_profile.setVisibility(View.VISIBLE);
                imglogin.setVisibility(View.GONE);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                Intent intent=new Intent(StudioActivity.this, PremiumActivity.class);
                startActivity(intent);
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
