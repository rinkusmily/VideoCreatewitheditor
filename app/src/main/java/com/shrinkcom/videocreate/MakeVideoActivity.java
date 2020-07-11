package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shrinkcom.videocreate.activities.PremiumActivity;
import com.shrinkcom.videocreate.activities.PreviewMainActivity;
import com.shrinkcom.videocreate.activities.StudioActivity;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.SessionManager;

import java.util.Locale;

public class MakeVideoActivity extends AppCompatActivity {
    CardView cardView,cardviewone,cardtviewtwo;
    Toolbar toolbar;
    ImageView imglogin,update_profile;
    String userid;
    SessionManager sessionManager;
    Locale myLocale;

    LinearLayout linone,lintwo,linthree,linfour,linfive,linsix,linseven,lineight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);
        Log.e("getlanguage",">>"+sessionManager.getLanguage());
        if(Locale.getDefault().getLanguage().equals("en")){
            setContentView(R.layout.activity_make_video);
        }else {
            setContentView(R.layout.make_video_arabic);
        }
        super.onCreate(savedInstanceState);
        cardView=findViewById(R.id.cardview);
        cardviewone=findViewById(R.id.cardviewone);
        cardtviewtwo=findViewById(R.id.cardviewtwo);
        toolbar=findViewById(R.id.mToolbar);
        imglogin=findViewById(R.id.img_login);
        update_profile=findViewById(R.id.update_profile);
        linone=findViewById(R.id.lin_one);
        lintwo=findViewById(R.id.lin_two);
        linthree=findViewById(R.id.lin_three);
        linfour=findViewById(R.id.lin_four);
        linfive=findViewById(R.id.lin_five);
        linsix=findViewById(R.id.lin_six);
        linseven=findViewById(R.id.lin_seven);
        lineight=findViewById(R.id.lin_eight);


        userid=sessionManager.getStoreString(SessionManager.KEY_ID);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, StudioActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        cardviewone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, StudioActivity.class);
                startActivity(intent1);
                finish();
            }
        });

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

        cardtviewtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, StudioActivity.class);
                startActivity(intent1);
                finish();
            }
        });

        imglogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MakeVideoActivity.this, PremiumActivity.class);
                startActivity(intent);
                finish();
            }
        });

        linone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);

                startActivity(intent1);
            }
        });
        lintwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        linthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        linfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        linfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        linsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        linseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
            }
        });
        lineight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MakeVideoActivity.this, PreviewMainActivity.class);
                startActivity(intent1);
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
