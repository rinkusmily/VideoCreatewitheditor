package com.shrinkcom.videocreate.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SessionManager {

    private static SharedPreferences _sPrefs, _sPrefs2 = null;
    SharedPreferences pref;
     Editor editor;
     Context mContext;
     int PRIVATE_MODE = 0;
    private static Editor _editor = null;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({ ENGLISH, ARABIC})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {ENGLISH, ARABIC};

    }
        static final String ENGLISH = "en";
        static final String ARABIC = "ar";

    public static SessionManager mInstance;


    private static final String PREF_NAME = "HayaanAcedemy";
    private static final String PREFER_LANGUAGE = "language";
    public static final String KEY_ID = "user_id";
    public static final String KEY_FNAME = "first_name";
    public static final String KEY_LNAME = "last_name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ABOUT = "about_user_desc";
    public static final String IMAGE = "profile_image";
    public static final String LESSTIONLIST = "lessonlist";
    public static final String KEY_LESSTIONID = "lessonid";
    public static final String KEY_VIDEO_ID = "videoid";
    public static final String KEY_INSTRUCTIONID = "instructorid";
    public static final String KEY_INSTRUCTOR_TITLE = "instructortile";
    public static final String KEY_INSTRUCTOR_TITLE_SOMALI = "instructortilesomali";
    public static final String KEY_THREADID = "threadid";
    public static final String KEY_COURSEID = "courseid";
    public static final String KEY_DATE = "date";
    public static final String KEY_REFERANCE_NO = "reference_no";
    public static final String KEY_OTP = "otp";
    public static final String KEY_PLAYBACKINFO = "playbackinfo";
    public static final String KEY_NEWS_ID = "newsid";
    public static final String KEY_COMPLETESTATUS= "statuscomplete";
    public static final String KEY_LESSION_STATUS= "statuslesson";
    public static final String KEY_RATING="rating";
    public static final String KEY_COMMENTID="commentid";
    public static final String KEY_LESSON_LIST="lessonlist";
    public static final String PREFS_CHAT_SCREEN_OPEN_STATUS = "chatscreenopenstatus";
    public static final String KEY_PERSONAL_RECEIVERID = "personalreceiverid";
    public static final String KEY_RECEIVER_FREIND = "receiverfreind";
    public static final String KEY_FINISH= "getfinish";
    public static final String KEY_SECRETKEY= "getfinish";
    public static final String KEY_REPLY= "getreplyMSG";
    public static final String KEY_LOGINTOKEN= "logintoken";
    public static final String KEY_EXPIRE_SESSION_DATE= "expiresession";


    public SessionManager(Context mContext) {
        this.mContext = mContext;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();


        }

        public static synchronized SessionManager getInstance(Context context) {
    if (mInstance == null) {
        mInstance = new SessionManager(context);
    }
    return mInstance;
    }
    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
    public  void saveString(String key, String value){
        editor.putString(key,value);
        editor.commit();

    }

    public String getStoreString(String key){
     return pref.getString(key,"");
    }



    public void setLanguage(String email) {
        editor.putString(PREFER_LANGUAGE, email);
        editor.commit();
    }
    public String getLanguage() {
        return pref.getString(PREFER_LANGUAGE, "en");
    }


    public boolean readBooleanPrefs(String pref_name) {
        return pref.getBoolean(pref_name, false);
    }

    public void writeBooleanPrefs(String pref_name, boolean pref_val) {
       // pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        editor.putBoolean(pref_name, pref_val);
        editor.commit();
    }


    public  List<String> readArratList(String pref_name) {

        Set<String> set = _sPrefs2.getStringSet(pref_name, null);
        List<String> mainList = new ArrayList<String>();
        mainList.addAll(set);
        return mainList;
    }


    public  void  clearFrefs(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
    }
}
