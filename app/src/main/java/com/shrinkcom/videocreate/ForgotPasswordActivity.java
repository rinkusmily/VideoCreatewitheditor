package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

public class ForgotPasswordActivity extends AppCompatActivity {
    TextView tvback,tvgetpassword;
    EditText forgot_email;
    String ForgetEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        tvback=findViewById(R.id.goback);
        tvgetpassword=findViewById(R.id.sendotp);
        forgot_email=findViewById(R.id.forgot_email);
        tvgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetEmail = forgot_email.getText().toString().trim();
               if (ForgetEmail.isEmpty()) {
                    forgot_email.setError((getString(R.string.enter_name)));
                    forgot_email.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(ForgetEmail).matches()) {
                    forgot_email.setError(getResources().getString(R.string.enter_valid_email));
                }else{
                   ForgetSenddata();
                }

            }
        });
        tvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

    }

    //TODO 30/03/2020 FORGOT PASSWORD API

    void ForgetSenddata() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage((getString(R.string.loading)));
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEFORGOT",">>>"+response);
                        pDialog.dismiss();

                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(ForgotPasswordActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(ForgotPasswordActivity.this, OtpCheckActivity.class);
                            intent.putExtra("email",ForgetEmail);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "forget_password");
                params.put("email", ""+ForgetEmail);
                Log.e("SENDVALUEFORGOT",">>>"+params);
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
