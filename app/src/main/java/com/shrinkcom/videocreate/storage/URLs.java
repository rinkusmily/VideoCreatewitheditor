package com.shrinkcom.videocreate.storage;

import android.text.Html;

public class URLs {
    public static final String BASE_ADDRESS= "http://shrinkcom.com/video_editing/api/webservices.php";
    public static final String BASE_PROFILE= "http://shrinkcom.com/video_editing/api/images/";



    public static  String stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            return Html.fromHtml(html).toString();
        }

    }
}