package com.shrinkcom.videocreate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.SessionManagerLan;

import java.util.Locale;

public class LanguageSelectActivity extends AppCompatActivity {

    TextView btn_english,btn_arabic;
    SessionManagerLan sessionManager;
    String currentLanguage = "en", currentLang;
    Locale myLocale;
    final  CharSequence[] values= {"English(India)", "Arabic" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        sessionManager = new SessionManagerLan(this);
         currentLanguage = getIntent().getStringExtra(currentLang);
         Log.e("currentLanguage",">>"+currentLanguage);
        btn_english = findViewById(R.id.btn_english);
        btn_arabic = findViewById(R.id.btn_arabic);

        btn_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("en");
            }
        });

        btn_arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("ar");
            }
        });
    }

    private void updateViews(String languageCode) {
        sessionManager.setLanguage(languageCode);
        Context context = LocaleHelper.setLocale(LanguageSelectActivity.this, languageCode);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = myLocale;
        resources.updateConfiguration(conf, dm);
        startActivity(new Intent(LanguageSelectActivity.this, DashboardActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));


    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("languagelog", "language "+base);
    }

}
