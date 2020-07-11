package com.shrinkcom.videocreate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.PermissionChecker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    com.shrinkcom.videocreate.storage.PermissionChecker permissionChecker = new com.shrinkcom.videocreate.storage.PermissionChecker();

    String[] RequiredPermissions = {Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        handler = new Handler();

        checkPermissions();

//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        printHashKey(SplashActivity.this);
//                        Intent intent = new Intent(SplashActivity.this, LanguageSelectActivity.class);
//                        startActivity(intent);
//                        finish();
//
//                    }
//                }, 3000);
//
//            }
//
//        }, 1 * 1000);
    }



    private void checkPermissions() {
        permissionChecker.verifyPermissions(this, RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {
            @Override
            public void onPermissionAllGranted() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                printHashKey(SplashActivity.this);
                                Intent intent = new Intent(SplashActivity.this, LanguageSelectActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        }, 3000);

                    }
//
                }, 1 * 1000);
            }


            @Override
            public void onPermissionDeny(String[] permissions) {
                Toast.makeText(SplashActivity.this, R.string.permite_permisssion, Toast.LENGTH_LONG).show();

            }

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));

                Log.e("PRINTHASHKEY", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("PRINTHASHKEY", "printHashKey() Hash Key: " + e);

        } catch (Exception e) {
            Log.e("PRINTHASHKEY", "printHashKey() Hash Key: " + e);

        }
    }
    }


