package com.shrinkcom.videocreate.interfaces;

import android.app.Activity;
import android.content.DialogInterface;

public class AppUtility {
    public static void showPayment(Activity activity,String message) {
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(activity);
        alert.setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()                 {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        android.app.AlertDialog alert1 = alert.create();

        alert1.show();

    }
}
