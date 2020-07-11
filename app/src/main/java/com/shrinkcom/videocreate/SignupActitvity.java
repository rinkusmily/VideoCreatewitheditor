package com.shrinkcom.videocreate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.storage.SessionManager;
import com.shrinkcom.videocreate.storage.URLs;
import com.shrinkcom.videocreate.storage.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActitvity extends AppCompatActivity {
    EditText etfname,etlname,etuname,etemail,etcemail,etpass,etcpass,etphone;
    String EtFname,EtLname,EtUname,EtEmail,EtCemail,Etpass,EtCpass,Etphone,Evc,Zaad;
    Button resgistered;
    TextView login;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_actitvity);
        login=findViewById(R.id.login_here);
        resgistered=findViewById(R.id.resgistered);
        etfname=findViewById(R.id.etfname);
        etlname=findViewById(R.id.et_lname);
        etuname=findViewById(R.id.etuname);
        etemail=findViewById(R.id.etemail);
        etcemail=findViewById(R.id.etconfirmemail);
        etpass=findViewById(R.id.etpass);
        etcpass=findViewById(R.id.etconfirmpass);
        etphone=findViewById(R.id.etphone);
        sessionManager = new SessionManager(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActitvity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        resgistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validate()){
                    return;
                }
                RegistrationSenddata();

            }
        });
    }

    private boolean validate() {
        boolean valid = true;
          EtFname = etfname.getText().toString().trim();
          EtLname = etlname.getText().toString().trim();
          EtUname = etuname.getText().toString().trim();
          EtEmail = etemail.getText().toString().trim();
          EtCemail = etcemail.getText().toString().trim();
          Etpass = etpass.getText().toString().trim();
          EtCpass = etcpass.getText().toString().trim();
          Etphone = etphone.getText().toString().trim();

        if (EtFname.isEmpty()) {
            etfname.setError(getString(R.string.enter_name));
            valid = false;
        } else if (EtLname.isEmpty()) {
            etlname.setError(getString(R.string.enter_last_name));
            valid = false;
        }else if (EtUname.isEmpty()) {
            etuname.setError(getString(R.string.enter_username));
            valid = false;
        } else if (EtEmail.isEmpty()) {
            etemail.setError(getString(R.string.enter_email));
            valid = false;
        } else if (!Patterns.PHONE.matcher(Etphone).matches()) {
            etphone.setError(getString(R.string.enter_phone_number));
            valid = false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(EtEmail).matches()) {
            etemail.setError(getResources().getString(R.string.enter_valid_email));
            valid = false;
        }
        else if(!EtEmail.equals(EtCemail)){
            Toast.makeText(SignupActitvity.this,(getString(R.string.email_not_match)),Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if (Etpass.isEmpty()) {
            etpass.setError((getString(R.string.enter_password)));
            valid = false;
        }
        else if(!Etpass.equals(EtCpass)){
            Toast.makeText(SignupActitvity.this, R.string.password_not_match,Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;

    }

    //TODO 30/03/2020 REGISTER USERDATA API

    void RegistrationSenddata() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage((getString(R.string.loading)));
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("RESPONSEREGISTER",">>>"+response);
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Intent i1 = new Intent(SignupActitvity.this, LoginActivity.class);
                            startActivity(i1);
                            finish();
                            Toast.makeText(SignupActitvity.this, obj.getString((getString(R.string.messege))), Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "registration");
                params.put("first_name", ""+EtFname);
                params.put("last_name", ""+EtLname);
                params.put("username",""+EtUname);
                params.put("email",""+ EtEmail);
                params.put("password",""+Etpass);
                params.put("phone",""+Etphone);
                Log.e("SENDVALUEREGISTER",">>>>"+params.toString());
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
