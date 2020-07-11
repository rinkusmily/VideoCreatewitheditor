package com.shrinkcom.videocreate.storage;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SessionManagerLan {
  public final String sessionName = "Video";
  SharedPreferences sharedPreferences;
  private static final String PREFER_LANGUAGE = "language";

  SharedPreferences.Editor editor;
  private String email;
  Context context;

  @Retention(RetentionPolicy.SOURCE)
  @StringDef({ ENGLISH, ARABIC })
  public @interface LocaleDef {
    String[] SUPPORTED_LOCALES = { ENGLISH, ARABIC };
  }

  static final String ENGLISH = "en";
  static final String ARABIC = "ar";


  public SessionManagerLan(Context activity) {
    sharedPreferences = activity.getSharedPreferences("sessionName", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    this.context=activity;
  }





  public void setLanguage(String email) {
    editor.putString(PREFER_LANGUAGE, email);
    editor.commit();
  }
  public String getLanguage() {
    return sharedPreferences.getString(PREFER_LANGUAGE, "en");
  }


}
