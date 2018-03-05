package com.winwin.project.winwin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Model.ModelProfil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_PROFIL;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_USERNAME;

public class EditAkunActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.txtNamaClient)
    TextView txtNamaClient;
    @BindView(R.id.namabelakang)
    TextView namabelakang;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.viewPhotoktp)
    TextView viewPhotoktp;
    @BindView(R.id.uploadPhotoktp)
    TextView uploadPhotoktp;
    @BindView(R.id.takephotoktp)
    TextView takephotoktp;
    @BindView(R.id.uploadPhotoSelfi)
    TextView uploadPhotoSelfi;
    @BindView(R.id.takephotoselfi)
    TextView takephotoselfi;
    @BindView(R.id.viewPhotoSelfi)
    TextView viewPhotoSelfi;
    @BindView(R.id.norek)
    TextView norek;
    @BindView(R.id.namabank)
    TextView namabank;
    @BindView(R.id.cabang)
    TextView cabang;
    @BindView(R.id.an)
    TextView an;
    @BindView(R.id.uploadPhotorek)
    TextView uploadPhotorek;
    @BindView(R.id.takephotorek)
    TextView takephotorek;
    @BindView(R.id.viewPhotorek)
    TextView viewPhotorek;
    SharedPreferences sharedpreferences;
    String username;
    StringRequest stringRequest;
    String id_klien;
    @BindView(R.id.notelppon)
    TextView notelppon;
    RequestQueue requestQueue;
    @BindView(R.id.karyawan_id)
    TextView karyawanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        id_klien = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtNamaClient.setText(username);
        requestQueue = Volley.newRequestQueue(EditAkunActivity.this);
        load();
    }

    private void load() {
        stringRequest = new StringRequest(Request.Method.GET, URL_POST_PROFIL + id_klien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response_profile", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelProfil dataClient = new ModelProfil();
                        dataClient.setNamadepan(json.getString("kar_namalengkap"));
                        dataClient.setNotelp(json.getString("kar_no_telepon"));
                        dataClient.setEmail(json.getString("kar_email_winwin"));
                        dataClient.setPassword(json.getString("kar_email_password"));
                        dataClient.setNorek(json.getString("kar_no_rek"));
                        dataClient.setBank(json.getString("kar_nama_bank"));
                        dataClient.setCabang(json.getString("kar_cabang"));
                        dataClient.setAtasnama(json.getString("kar_an"));

                        namabelakang.setText(username);
                        notelppon.setText(dataClient.getNotelp());
                        email.setText(dataClient.getEmail());
                        password.setText(dataClient.getPassword());
                        norek.setText(dataClient.getNorek());
                        namabank.setText(dataClient.getBank());
                        cabang.setText(dataClient.getCabang());
                        an.setText(dataClient.getAtasnama());

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
