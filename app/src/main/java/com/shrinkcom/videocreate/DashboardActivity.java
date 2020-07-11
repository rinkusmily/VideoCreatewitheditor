package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.shrinkcom.videocreate.activities.MusicActivity;
import com.shrinkcom.videocreate.activities.PremiumActivity;
import com.shrinkcom.videocreate.activities.ProfileActivity;
import com.shrinkcom.videocreate.activities.ShareAcitvity;
import com.shrinkcom.videocreate.activities.StudioActivity;
import com.shrinkcom.videocreate.activities.SucscribeActivity;
import com.shrinkcom.videocreate.activities.TemplateActivity;
import com.shrinkcom.videocreate.adapter.DashboardAdapter;
import com.shrinkcom.videocreate.adapter.MyAdapter;
import com.shrinkcom.videocreate.adapter.MyPagerAdapter;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class DashboardActivity extends AppCompatActivity {
    private static ViewPager pager ;
    private static int currentPage = 0;
    RecyclerView recyclerView;
    private DashboardAdapter mAdapter;
    TextView tvmakevideo,tvvideo,tvsocail;
    ImageView imglogin,update_profile,mv;
    CircleIndicator indicator;
    String userid;

    HashMap<String,String> Hash_file_maps ;
   SessionManager sessionManager;
    private static final Integer[] XMEN= {R.drawable.slide_one,R.drawable.slide_two,R.drawable.slide_three,R.drawable.slide_four};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    ArrayList personImages   = new ArrayList<>(Arrays.asList(R.drawable.slide_one, R.drawable.slide_two,
            R.drawable.slide_three, R.drawable.slide_four));

    static Integer[] drawableArray = {R.drawable.slide_one, R.drawable.slide_two, R.drawable.slide_three,
            R.drawable.slide_four};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(this);
        userid=sessionManager.getStoreString(SessionManager.KEY_ID);

        pager = findViewById(R.id.pager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        recyclerView=findViewById(R.id.recycler_view);
        tvmakevideo=findViewById(R.id.tv_makevideo);
        tvsocail=findViewById(R.id.tv_social);
        update_profile=findViewById(R.id.update_profile);
        tvvideo=findViewById(R.id.tv_video);
        imglogin=findViewById(R.id.img_login);
        mAdapter = new DashboardAdapter(DashboardActivity.this,drawableArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);




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

        tvmakevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, MakeVideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvsocail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, ShareAcitvity.class);
                startActivity(intent);
                finish();
            }
        });

        tvvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, TemplateActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this, PremiumActivity.class);
                startActivity(intent);
                finish();
            }
        });

        init();




    }

    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        pager.setAdapter(new MyPagerAdapter(DashboardActivity.this,XMENArray));
        indicator.setViewPager(pager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }

        }, 2500, 3000);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("VideoCreate", "language "+base);
    }


}
