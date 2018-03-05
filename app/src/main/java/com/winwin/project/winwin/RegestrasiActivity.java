package com.winwin.project.winwin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.winwin.project.winwin.Config.AppController;
import com.winwin.project.winwin.Model.getID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ID_MEMBER;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_KARYAWAN;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_MEMBERSYSTEM;

public class RegestrasiActivity extends AppCompatActivity {

    @BindView(R.id.namadepan)
    EditText namadepan;
    @BindView(R.id.namabelakang)
    EditText namabelakang;
    @BindView(R.id.tanggal_lhr)
    EditText tanggalLhr;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.nomor_hp)
    EditText nomorHp;
    @BindView(R.id.Password)
    TextInputEditText Password;
    @BindView(R.id.ConfPassword)
    TextInputEditText ConfPassword;
    @BindView(R.id.btnDaftar)
    Button btnDaftar;
    @BindView(R.id.link_back_login)
    TextView linkBackLogin;
    String dateString, pas, cpas;
    ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    RequestQueue requestQueue;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestrasi);
        ButterKnife.bind(this);

        pas = Password.getText().toString();
        cpas = ConfPassword.getText().toString();

        ConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validate();
            }
        });

        requestQueue = Volley.newRequestQueue(RegestrasiActivity.this);
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

    private void Validate() {

        if (cpas.matches(pas)) {
            Toast.makeText(this, "Cocok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tidak Cocok", Toast.LENGTH_SHORT).show();
        }
    }

    private void CheckValues() {
        if (namadepan.getText().toString().compareTo("") == 0) {
            namadepan.setError("Null");
        }
        if (namabelakang.getText().toString().compareTo("") == 0) {
            namabelakang.setError("Null");
        }
        if (email.getText().toString().compareTo("") == 0) {
            email.setError("Null");
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

    private void clearText() {
        namadepan.getText().clear();
        namabelakang.getText().clear();
        tanggalLhr.getText().clear();
        email.getText().clear();
        nomorHp.getText().clear();
        Password.getText().clear();
        ConfPassword.getText().clear();
    }

    private void insertKaryawan(final String namadepan, final String namabelakang, final String email) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_POST_KARYAWAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(RegestrasiActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                DialogForm();
                hideDialog();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(RegestrasiActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                        clearText();

                    } else {
                        Toast.makeText(RegestrasiActivity.this,
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
                Log.d("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("namadepan", namadepan);
                params.put("kar_email_winwin", email);
                params.put("namabelakang", namabelakang);

                return params;
            }

        };
        requestQueue.add(strReq);
        // Adding request to request queue
    }

    private void insertMemberSystem(final String username, final String password, final String member_id_kar) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_POST_MEMBERSYSTEM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(RegestrasiActivity.this, "Berhasil Di Input Ke Menjadi User untuk Login", Toast.LENGTH_SHORT).show();

                clearText();
                hideDialog();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(RegestrasiActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                        clearText();

                    } else {
                        Toast.makeText(RegestrasiActivity.this,
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
                Log.d("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("member_user", username);
                params.put("member_pass", password);
                params.put("member_id_karyawan", member_id_kar);
                return params;
            }

        };
        requestQueue.add(strReq);
        // Adding request to request queue
    }

    @OnClick({R.id.btnDaftar, R.id.link_back_login, R.id.tanggal_lhr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDaftar:
                CheckValues();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {

                    insertKaryawan(namadepan.getText().toString(), namabelakang.getText().toString(), email.getText().toString());

                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.link_back_login:
                finish();
                break;
            case R.id.tanggal_lhr:

                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                dateString = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month) + "-" + year;
                                tanggalLhr.setText(dateString);
                            }
                        });
                cdp.show(getSupportFragmentManager(), null);
                break;
        }
    }

    private void DialogForm() {
        final Dialog dialog = new Dialog(RegestrasiActivity.this);
        LayoutInflater inflater = (LayoutInflater) RegestrasiActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_sukses_regestrasi, null);
        Button button = view.findViewById(R.id.Success);
        final TextView id_member_karyawans = view.findViewById(R.id.id_member_karyawan);
        requestQueue = Volley.newRequestQueue(RegestrasiActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_ID_MEMBER + namadepan.getText().toString()+namabelakang.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        getID modelMenu = new getID();
                        modelMenu.setId(json.getString("kar_id"));
                        id_member_karyawans.setText(modelMenu.getId());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        });
        requestQueue.add(stringRequest);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {

                    insertMemberSystem(namadepan.getText().toString(), Password.getText().toString(), id_member_karyawans.getText().toString());
                    finish();
                } else {

                }
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius_bg_pop_up);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(view);

        dialog.show();


    }

}
