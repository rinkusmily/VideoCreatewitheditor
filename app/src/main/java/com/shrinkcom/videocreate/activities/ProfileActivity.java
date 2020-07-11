package com.shrinkcom.videocreate.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.videocreate.DashboardActivity;
import com.shrinkcom.videocreate.LoginActivity;
import com.shrinkcom.videocreate.R;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.interfaces.AppUtility;
import com.shrinkcom.videocreate.storage.SessionManager;
import com.shrinkcom.videocreate.storage.URLs;
import com.shrinkcom.videocreate.storage.Utilitynew;
import com.shrinkcom.videocreate.storage.VolleyMultipartRequest;
import com.shrinkcom.videocreate.storage.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    CircleImageView profileimage;
    EditText etfname,etlname,etmssage,etphone;
    TextView etuname;
    String EtUname,EtFname,EtLname,EtEmail,Image,EtPhone;
    Button btn_update;
    TextView etemail;
    SessionManager sessionManager;
    String[] RequiredPermissions = {Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    byte[] byteArray;
    Bitmap _bitmap;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask,publickeys,memosl,ADD,DES;
    String updatetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager = new SessionManager(this);
        toolbar = findViewById(R.id.mToolbar);
        profileimage = findViewById(R.id.profile_image);
        etuname = findViewById(R.id.etuname);
        etfname = findViewById(R.id.etfname);
        etlname = findViewById(R.id.et_lname);
        etphone = findViewById(R.id.etphone);
        btn_update = findViewById(R.id.btn_update);
         Image = sessionManager.getStoreString(SessionManager.IMAGE);
        Log.e("Image", "--->" + Image);

        EtUname = sessionManager.getStoreString(SessionManager.KEY_USERNAME);
        EtFname = sessionManager.getStoreString(SessionManager.KEY_FNAME);
        EtLname = sessionManager.getStoreString(SessionManager.KEY_LNAME);
        EtPhone = sessionManager.getStoreString(SessionManager.KEY_PHONE);

        etuname.setText(EtUname);
        etfname.setText(EtFname);
        etlname.setText(EtLname);
        etphone.setText(EtPhone);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_person_black_24dp);
        requestOptions.error(R.drawable.ic_person_black_24dp);

        Glide.with(profileimage.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(URLs.BASE_PROFILE+Image)
                .into(profileimage);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(byteArray!=null){
                    validate();
                }else {
                    Profile();
                }
            }
        });
        profileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    private Boolean validate() {
        EtFname = etfname.getText().toString().trim();
        EtLname = etlname.getText().toString().trim();
        EtUname = etuname.getText().toString().trim();
        EtPhone = etphone.getText().toString().trim();

        if (EtFname.isEmpty()) {
            etfname.setError(getString(R.string.enter_name));
            etfname.requestFocus();
        } else if (EtLname.isEmpty()) {
            etlname.setError(getString(R.string.enter_last_name));
            etlname.requestFocus();
        } else if (EtUname.isEmpty()) {
            etuname.setError(getString(R.string.enter_username));
            etuname.requestFocus();
        }
        else if (EtPhone.isEmpty()) {
            etphone.setError(getString(R.string.enter_phone_number));
            etphone.requestFocus();
        }
        else {
            editProfile();
        }
        return true;
    }
    // ____________________________________________PROFILE SET CODE___________________________________
    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_libary),
                getString(R.string.cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utilitynew.checkPermission(ProfileActivity.this);

                if (items[item].equals(getString(R.string.take_photo))) {
                    userChoosenTask = (getString(R.string.take_photo));
                    if (result)
                        cameraIntent();

                } else if (items[item].equals(getString(R.string.choose_from_libary))) {
                    userChoosenTask = (getString(R.string.choose_from_libary));
                    if (result)
                        galleryIntent();

                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    File destination;

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        profileimage.setImageBitmap(bitmap);

        updatetime = String.valueOf(SystemClock.currentThreadTimeMillis());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        Log.e("ByteArraaa", ">>" + byteArray);
        destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(stream.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profileimage.setImageBitmap(bitmap);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap thumbnail = null;
        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updatetime = String.valueOf(SystemClock.currentThreadTimeMillis());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byteArray = stream.toByteArray();

        Log.e("ByteArraaa", ">>" + byteArray);

        profileimage.setImageBitmap(thumbnail);
    }



    private void editProfile() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage((getString(R.string.loading)));
        progressDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, URLs.BASE_ADDRESS, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                Log.e("RESPONSEPROFILE", "--->" + resultResponse);
                try {
                    JSONObject obj = new JSONObject(resultResponse);
                    if (obj.getInt("result")==1) {
                        JSONArray jsonArray = obj.getJSONArray("userData");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.e("IMAGE", "--->" + object.getString("image"));
                            sessionManager.saveString(SessionManager.IMAGE, object.getString("image"));
                            sessionManager.saveString(SessionManager.KEY_FNAME, object.getString("first_name"));
                            sessionManager.saveString(SessionManager.KEY_LNAME, object.getString("last_name"));
                            sessionManager.saveString(SessionManager.KEY_USERNAME, object.getString("username"));
                            sessionManager.saveString(SessionManager.KEY_PHONE, object.getString("phone"));
                            Toast.makeText(ProfileActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                            Intent i1 = new Intent(ProfileActivity.this, DashboardActivity.class);
                            startActivity(i1);
                            finish();
                        }
                    }else {
                        AppUtility.showPayment(ProfileActivity.this,obj.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
                Log.e("ERRORRRRR", "---->" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action","update_profile");
                params.put("user_id",sessionManager.getStoreString(SessionManager.KEY_ID));
                params.put("first_name",EtFname);
                params.put("last_name", EtLname);
                params.put("username", EtUname);
                params.put("phone", EtPhone);
                Log.e("SENDPROFILE", params.toString());
                return params;
            }
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                params.put("image", new VolleyMultipartRequest.DataPart(updatetime + "file_a.jpg", byteArray, "avtar/jpeg"));
                Log.e("USERIMAGE", "===>" + byteArray);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(multipartRequest);

    }

    private void Profile() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEPROFILE", "--->" + response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("result")==1) {
                                JSONArray jsonArray = obj.getJSONArray("userData");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Log.e("IMAGE", "--->" + object.getString("image"));

                                    sessionManager.saveString(SessionManager.IMAGE, object.getString("image"));
                                    sessionManager.saveString(SessionManager.KEY_FNAME, object.getString("first_name"));
                                    sessionManager.saveString(SessionManager.KEY_LNAME, object.getString("last_name"));
                                    sessionManager.saveString(SessionManager.KEY_USERNAME, object.getString("username"));
                                    sessionManager.saveString(SessionManager.KEY_PHONE, object.getString("phone"));
                                    Toast.makeText(ProfileActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                    Intent i1 = new Intent(ProfileActivity.this, DashboardActivity.class);
                                    startActivity(i1);
                                    finish();
                                }
                            }else {
                                AppUtility.showPayment(ProfileActivity.this,obj.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }

                    }



                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","update_profile");
                params.put("user_id",sessionManager.getStoreString(SessionManager.KEY_ID));
                params.put("first_name",etfname.getText().toString());
                params.put("last_name", etlname.getText().toString());
                params.put("username", etuname.getText().toString());
                params.put("phone",etphone.getText().toString());
                params.put("image","");
                Log.e("PROFILESEND",params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("languagelog", "language "+base);
    }

}
