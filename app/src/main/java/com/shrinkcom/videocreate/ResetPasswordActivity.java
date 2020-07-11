package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ResetPasswordActivity extends AppCompatActivity {
    TextView tvsubmit;
    EditText etpass,etcpass;
    String EtPass,EtCpass,Useremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        tvsubmit=findViewById(R.id.btn_submit);
        etpass=findViewById(R.id.etpass);
        etcpass=findViewById(R.id.etcpass);

        Useremail=getIntent().getStringExtra("email");

        tvsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EtPass = etpass.getText().toString().trim();
                EtCpass = etcpass.getText().toString().trim();
                if (EtPass.isEmpty()) {
                    etpass.setError((getString(R.string.enter_password)));
                    etpass.requestFocus();
                }
                else if(!EtPass.equals(EtCpass)){
                    Toast.makeText(ResetPasswordActivity.this,(getString(R.string.email_not_match)),Toast.LENGTH_SHORT).show();
                }else{
                    ResetSenddata();

                }
            }
        });
    }

    //TODO 30/03/2020 RESET PASSWORD API

    void ResetSenddata () {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage((getString(R.string.loading)));
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSERESETPASWWORD", ">>>" + response);
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(ResetPasswordActivity.this, obj.getString((getString(R.string.messege))), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResetPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "setpassword");
                params.put("email", ""+Useremail);
                params.put("password", "" +EtPass);
                Log.e("SENDRESETPASSWORD",">>"+params);
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
