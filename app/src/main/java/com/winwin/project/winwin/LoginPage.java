package com.winwin.project.winwin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.winwin.project.winwin.Config.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_LOGIN;
import static com.winwin.project.winwin.Config.http.TAG_ID;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_PASS;
import static com.winwin.project.winwin.Config.http.TAG_USERNAME;


public class LoginPage extends AppCompatActivity {

    @BindView(R.id.txtUsername)
    EditText txtUsername;
    @BindView(R.id.txtPassword)
    TextInputEditText txtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    ProgressDialog pDialog;
    Intent intent;

    int success;
    ConnectivityManager conMgr;
    @BindView(R.id.link_daftar)
    TextView linkDaftar;

    private static final String TAG = LoginPage.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    String text1, text2; //Deklarasi object string

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ButterKnife.bind(this);

        cekInternet();
        // Cek session login jika TRUE maka langsung buka MainActivity
        cekSession();
    }

    private void cekSession() {
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);

        if (session) {
            Intent intent = new Intent(LoginPage.this, MenuActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void cekInternet() {
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void checkLogin(final String username, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String username = jObj.getString(TAG_USERNAME);
                        String id = jObj.getString(TAG_ID);
                        String id_karyawan = jObj.getString(TAG_MEMBER_ID_KARYAWAN);
                        String passw = txtPassword.getText().toString();

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USERNAME, username);
                        editor.putString(TAG_MEMBER_PASS, passw);
                        editor.putString(TAG_MEMBER_PASS, passw);
                        editor.putString(TAG_MEMBER_ID_KARYAWAN, id_karyawan);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(LoginPage.this, MenuActivity.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USERNAME, username);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                if (error instanceof TimeoutError) {
                    Toast.makeText(LoginPage.this, "Timeout", Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginPage.this, "Please check your server network", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(LoginPage.this, "no connection", Toast.LENGTH_SHORT).show();
                }
//                Log.d("abd",  error
//                        + ">>" + error.networkResponse.statusCode
//                        + ">>" + error.networkResponse.data
//                        + ">>" + error.getCause()
//                        + ">>" + error.getMessage());
                hideDialog();

            }


        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_user", username);
                params.put("member_pass", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        // mengecek kolom yang kosong
        if (username.trim().length() > 0 && password.trim().length() > 0) {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                checkLogin(username, password);
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(), "Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.link_daftar)
    public void onViewClicked() {
        startActivity(new Intent(LoginPage.this, RegestrasiActivity.class));
    }
}




