package com.shrinkcom.videocreate.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.shrinkcom.videocreate.R;

import java.io.File;

import life.knowledge4.videotrimmer.K4LVideoTrimmer;
import life.knowledge4.videotrimmer.interfaces.OnTrimVideoListener;

public class trim_video extends AppCompatActivity {


    static String path;
    K4LVideoTrimmer videoTrimmer;

    Activity activity;

    public static void addpath( String p ){
        path = p;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trim_video);

        videoTrimmer  = ((K4LVideoTrimmer) findViewById(R.id.timeLine));
        videoTrimmer.setMaxDuration(30);

        activity = this ;

        File direct = new File(Environment.getExternalStorageDirectory()
                + "/TrimVideo");

        if (!direct.exists()) {
            direct.mkdirs();
        }


        final String output = direct+"/"+  getIntent().getStringExtra("key")+".mp4";
        videoTrimmer.setDestinationPath(output);


        if (videoTrimmer != null) {
            videoTrimmer.setVideoURI(Uri.parse(path));
        }

        videoTrimmer.setOnTrimVideoListener(new OnTrimVideoListener() {
            @Override
            public void getResult(Uri uri) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(trim_video.this ,"Video trim successfully .... ", Toast.LENGTH_SHORT).show();

                    }
                });

                System.out.println( "file = " + uri.getPath() + " = " + uri.toString()  );
                //String videopath = WritetoExternalStorage.getVideopath( uri  , trim_video.this );

                File f = new File( uri.getPath() );
                File to = new File( output );

                f.renameTo(to);

                finish();


            }

            @Override
            public void cancelAction() {


            }
        });


    }
}
