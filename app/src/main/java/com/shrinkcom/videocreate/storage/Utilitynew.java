package com.shrinkcom.videocreate.storage;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.shrinkcom.videocreate.R;


public class Utilitynew {
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle(context.getString(R.string.permission_necessary));
                    alertBuilder.setMessage(context.getString(R.string.eternal_storage_persmission));
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public  static  void showMessageAlert(Context montext,String message){

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(montext).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void updateResources(Activity context, String language) {
// Locale locale = new Locale(language);
// Locale.setDefault(locale);
//
// Resources res = context.getResources();
// Configuration config =res.getConfiguration();
// config.setLocale(new Locale("ja"));
// res.updateConfiguration(config, res.getDisplayMetrics());
        try {
//            SessionManager sessionManager = new SessionManager(context);
            Resources res = context.getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
//
//            if (!TextUtils.isEmpty(sessionManager.getLanguage())) {
//                if (sessionManager.getLanguage().equalsIgnoreCase("english")) {
//                    conf.setLocale(new Locale("en"));
//
//                } else {
//                    conf.setLocale(new Locale("ar"));

//                }
//            }
//        else{
//                String defaultDeviceLanguage = Locale.getDefault().getDisplayLanguage();
////                sessionManager.setLanguage("ar");
//                conf.setLocale(new Locale("ar"));
//            }
            res.updateConfiguration(conf, dm);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(100);

        } catch (Exception e) {

            Toast.makeText(context , e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    public static  boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(

                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

}