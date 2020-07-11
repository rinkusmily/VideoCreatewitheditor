package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.URLs;
import com.shrinkcom.videocreate.storage.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpCheckActivity extends AppCompatActivity {
    TextView tvback,tvnext;
    EditText otp1,otp2,otp3,otp4;
    String OTP,Useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_check);
        tvback=findViewById(R.id.btn_back);
        tvnext=findViewById(R.id.btn_nextsecond);
        otp1=findViewById(R.id.otp1);
        otp1.addTextChangedListener(textWatchersecond);
        otp2=findViewById(R.id.otp2);
        otp2.addTextChangedListener(textWatcherthree);
        otp3=findViewById(R.id.otp3);
        otp3.addTextChangedListener(textWatcherfour);
        otp4=findViewById(R.id.otp4);


    Useremail=getIntent().getStringExtra("email");

        tvnext.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validate();


        }
    });
        tvback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(OtpCheckActivity.this, ForgotPasswordActivity.class);
            startActivity(i);
            finish();
        }
    });
}
    public TextWatcher textWatcherone = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public TextWatcher textWatchersecond = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            otp2.requestFocus();
        }
    };

    public TextWatcher textWatcherthree = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            otp3.requestFocus();
        }
    };
    public TextWatcher textWatcherfour = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            otp4.requestFocus();
        }
    };

    private void validate() {

        String OTP11 = otp1.getText().toString().trim();
        String OTP22 = otp2.getText().toString().trim();
        String OTP33 = otp3.getText().toString().trim();
        String OTP44 = otp4.getText().toString().trim();
        OTP = otp1.getText().toString().trim()+otp2.getText().toString().trim()+otp3.getText().toString().trim()+otp4.getText().toString().trim();
        if (OTP11.isEmpty()) {
            otp1.setError((getString(R.string.enter_otp)));
            otp1.requestFocus();

        } else if (OTP22.isEmpty()) {
            otp2.setError((getString(R.string.enter_otp)));
            otp2.requestFocus();
        }
        else if (OTP33.isEmpty()) {
            otp3.setError((getString(R.string.enter_otp)));
            otp3.requestFocus();
        }else if (OTP44.isEmpty()) {
            otp4.setError((getString(R.string.enter_otp)));
            otp4.requestFocus();
        }
        else {
            OtpSenddata();
        }
    }

    //TODO 30/03/2020 OTP SEND API

    void OtpSenddata() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage((getString(R.string.loading)));
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPONSEOTP",">>>"+response);
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);

                            Intent intent = new Intent(OtpCheckActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("email", Useremail);
                            startActivity(intent);
                            finish();
                            Toast.makeText(OtpCheckActivity.this, obj.getString(getString(R.string.messege)), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OtpCheckActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "verifyotp");
                params.put("email", ""+Useremail);
                params.put("otp", ""+OTP);
                Log.e("SENDOTP",">>>"+params);
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
