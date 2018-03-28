package com.winwin.project.winwin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import butterknife.OnClick;

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
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.btEdit)
    FloatingActionButton btEdit;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
    @BindView(R.id.ImgSelfi)
    ImageView ImgSelfi;
    @BindView(R.id.ImgRek)
    ImageView ImgRek;

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
        underline();
        gone();
        visible();
    }

    private void visible() {
        btEdit.setVisibility(View.VISIBLE);
    }

    private void gone() {
        btSubmit.setVisibility(View.GONE);

        takephotorek.setVisibility(View.GONE);
        takephotoselfi.setVisibility(View.GONE);
        takephotoktp.setVisibility(View.GONE);

        uploadPhotoSelfi.setVisibility(View.GONE);
        uploadPhotorek.setVisibility(View.GONE);
        uploadPhotoktp.setVisibility(View.GONE);
    }

    private void underline() {
        viewPhotoktp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewPhotoSelfi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        viewPhotorek.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        uploadPhotoktp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        uploadPhotorek.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        uploadPhotoSelfi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        takephotoktp.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        takephotoselfi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        takephotorek.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
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

    @OnClick({R.id.ic_home, R.id.img_back, R.id.btEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(EditAkunActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btEdit:
                intent = new Intent(EditAkunActivity.this, EditAkunPost.class);
                intent.putExtra("profile", username);
                intent.putExtra("name", namabelakang.getText());
                intent.putExtra("phone", notelppon.getText());
                intent.putExtra("email", email.getText());
                intent.putExtra("password", password.getText());
                intent.putExtra("no_rek", norek.getText());
                intent.putExtra("bank_name", namabank.getText());
                intent.putExtra("cab", cabang.getText());
                intent.putExtra("an", an.getText());
                startActivity(intent);
                break;
        }
    }

}
