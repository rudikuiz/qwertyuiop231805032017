package com.winwin.project.winwin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.winwin.project.winwin.Model.ModelJatuhTempo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_DETAIL_PEMBAYARAN;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_ID;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_USERNAME;


public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnClient)
    CircleButton btnClient;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    @BindView(R.id.btnMenu)
    CircleButton btnMenu;
    SharedPreferences sharedpreferences;
    String username, member_id, member_id_karyawan;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnProfil)
    CircleButton btnProfil;
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.ic_logo)
    ImageView icLogo;
    @BindView(R.id.tvClient)
    TextView tvClient;
    RequestQueue requestQueue;
    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = getIntent().getStringExtra(TAG_USERNAME);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

//        Toast.makeText(MenuActivity.this, member_id, Toast.LENGTH_SHORT).show();

        txtUser.setText(username);
    }

    @OnClick({R.id.img_logout, R.id.btnClient, R.id.btnMenu, R.id.btnProfil})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_logout:
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(LoginPage.session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_USERNAME, null);
                editor.commit();

                Intent intent = new Intent(MenuActivity.this, LoginPage.class);
                finish();
                startActivity(intent);
                break;
            case R.id.btnClient:
                Intent i = new Intent(MenuActivity.this, DaftarClientTagih.class);
                startActivity(i);
                break;
            case R.id.btnMenu:
                Intent in = new Intent(MenuActivity.this, KomisiActivity.class);
                startActivity(in);
                break;
            case R.id.btnProfil:
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
//                Toast.makeText(this, "Active, Nothing Menu. Wait update from Programmer", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getIdKaryawan() {
        stringRequest = new StringRequest(Request.Method.GET, URL_MEMBER_ID_KARYAWAN + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelJatuhTempo modelMenu = new ModelJatuhTempo();
                        modelMenu.setNama(json.getString("cli_nama_lengkap"));
                        modelMenu.setNilai(json.getString("nilai_bayar"));
                        modelMenu.setTempo(json.getString("tgl_bayar"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(stringRequest);
    }

}