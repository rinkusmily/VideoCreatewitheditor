package com.shrinkcom.videocreate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.videocreate.activities.StudioActivity;
import com.shrinkcom.videocreate.helper.LocaleHelper;
import com.shrinkcom.videocreate.interfaces.AppUtility;
import com.shrinkcom.videocreate.storage.SessionManager;
import com.shrinkcom.videocreate.storage.URLs;
import com.shrinkcom.videocreate.storage.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.firebase.auth.GoogleAuthProvider.getCredential;


public class LoginActivity extends AppCompatActivity {
    Button btnlogin;
    TextView registernow, tvforgot,tv_google;
    EditText etemail, etpass;
    String Email, Password,email;
    SessionManager sessionManager;
   LoginButton loginButton;
    private GoogleApiClient googleApiClient;
    CallbackManager callbackManager;
    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess;
    protected GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    String emailfb,first_name,strname;
    protected FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(getApplicationContext());
        setContentView(R.layout.activity_login);
        btnlogin = findViewById(R.id.btn_login);
        etemail = findViewById(R.id.etemail);
        etpass = findViewById(R.id.etpass);
        registernow = findViewById(R.id.register_now);
        tvforgot = findViewById(R.id.tv_forgot);
        sessionManager = new SessionManager(this);
        callbackManager = CallbackManager.Factory.create();

        mAuth = FirebaseAuth.getInstance();
        setupGoogleSigninClient();
        initview();

        // TODO: 2/15/2020 fblogincode

         loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
           public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()

                getUserProfile(AccessToken.getCurrentAccessToken());


           }
;

    @Override
    public void onCancel() {
        // App code
    }

    @Override
    public void onError(FacebookException exception) {
        Log.e("errorfacebook",">>>"+exception);
        // App code
    }
});


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registernow.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,SignupActitvity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initview() {
        tv_google = findViewById(R.id.tv_google);
        loginButton = findViewById(R.id.login_button);


        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        tv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignIn();
            }
        });
    }


        private Boolean validate() {

        Email = etemail.getText().toString().trim();
        Password = etpass.getText().toString().trim();

        if (Email.isEmpty()) {
            etemail.setError((getString(R.string.enter_email)));
            etemail.requestFocus();
        }

        else if (Password.isEmpty()) {
            etpass.setError((getString(R.string.enter_password)));
            etpass.requestFocus();
        } else {
            loginSendData();
        }
        return true;

    }

    //TODO 30/03/2020 LOGIN  API

    void loginSendData() {
        final ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage((getString(R.string.loading)));
        pDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSELOGIN", ">>>" + response);
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("result")==1) {
                                JSONArray jsonArray = obj.getJSONArray("userData");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    sessionManager.saveString(SessionManager.KEY_ID, object.getString("user_id"));
                                    sessionManager.saveString(SessionManager.KEY_EMAIL, object.getString("email"));
                                    sessionManager.saveString(SessionManager.KEY_FNAME, object.getString("first_name"));
                                    sessionManager.saveString(SessionManager.KEY_LNAME, object.getString("last_name"));
                                    sessionManager.saveString(SessionManager.KEY_USERNAME, object.getString("username"));
                                    sessionManager.saveString(SessionManager.KEY_PHONE, object.getString("phone"));
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent i1 = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(i1);
                                    finish();
                                }
                            }else {
                                AppUtility.showPayment(LoginActivity.this,obj.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.hide();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "login");
                params.put("email", "" + Email);
                params.put("password", "" + Password);
                Log.e("LOGINSENDVALUE", ">>" + params);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    // TODO: 2/15/2020 fbloginapi

    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
               currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                       try {
                            first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            emailfb = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                           LoginManager.getInstance().logOut();
                           facebooklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }




    // TODO: 2/15/2020 gmail login code
    private void setupGoogleSigninClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Web_Client_Id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("","account"+account);
                signInWithGoogle(account);


            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void signInWithGoogle(GoogleSignInAccount acct) {
        if (acct != null) {
            Log.e("", "firebaseAuthWithGoogle:" + acct.getId());
            AuthCredential credential = getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("error", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                strname =    mAuth.getCurrentUser().getDisplayName();
                                email =    mAuth.getCurrentUser().getEmail();
                                String phome =    mAuth.getCurrentUser().getPhoneNumber();
                                gmaillogin();
                                Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            } else {

                              //  Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

//        mAuth.GoogleSignInClient.signOut(mGoogleSignInClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//
//                    }
//                });
    }
    public void performSignIn() {

        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    public void facebooklogin(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSSEEEEEfbLogin",">>>"+response);
                        pDialog.hide();
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("result")==1) {
                                JSONArray jsonArray = obj.getJSONArray("userData");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    sessionManager.saveString(SessionManager.KEY_ID, object.getString("user_id"));
                                    sessionManager.saveString(SessionManager.KEY_EMAIL, object.getString("email"));
                                    sessionManager.saveString(SessionManager.KEY_FNAME, object.getString("first_name"));
                                    sessionManager.saveString(SessionManager.KEY_LNAME, object.getString("last_name"));
                                    sessionManager.saveString(SessionManager.KEY_USERNAME, object.getString("username"));
                                    sessionManager.saveString(SessionManager.KEY_PHONE, object.getString("phone"));
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent i1 = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(i1);
                                    finish();
                                }
                            }else {
                                AppUtility.showPayment(LoginActivity.this,obj.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", ""+emailfb);
                params.put("username", ""+first_name);
                params.put("action", "social_login");
                params.put("login_by", "facebook");
                Log.e("paramss",">>"+params);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void gmaillogin(){
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.BASE_ADDRESS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSSEEEEEGmailLogin",">>>"+response);
                        pDialog.hide();
                        pDialog.hide();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("result")==1) {
                                JSONArray jsonArray = obj.getJSONArray("userData");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    sessionManager.saveString(SessionManager.KEY_ID, object.getString("user_id"));
                                    sessionManager.saveString(SessionManager.KEY_EMAIL, object.getString("email"));
                                    sessionManager.saveString(SessionManager.KEY_FNAME, object.getString("first_name"));
                                    sessionManager.saveString(SessionManager.KEY_LNAME, object.getString("last_name"));
                                    sessionManager.saveString(SessionManager.KEY_USERNAME, object.getString("username"));
                                    sessionManager.saveString(SessionManager.KEY_PHONE, object.getString("phone"));
                                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    Intent i1 = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(i1);
                                    finish();
                                }
                            }else {
                                AppUtility.showPayment(LoginActivity.this,obj.getString("message"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", ""+email);
                params.put("username", ""+strname);
                params.put("action", "social_login");
                params.put("login_by", "google");
                Log.e("paramss",">>"+params);
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
