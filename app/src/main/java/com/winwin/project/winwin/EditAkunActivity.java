package com.winwin.project.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winwin.project.winwin.Model.ModelProfil;
import com.winwin.project.winwin.Model.ModelUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_IMAGE_FROM_FOLDER;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_PROFIL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_VIEW;
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
    String id_kar;
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
    ArrayList<ModelUrl> list_data;
    private final int MY_SOCKET_TIMEOUT_MS = 60 * 1000;
    String urlKtp, urlSelfi, urlRek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        id_kar = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtNamaClient.setText(username);
        requestQueue = Volley.newRequestQueue(EditAkunActivity.this);
        load();
        underline();
        gone();
        visible();
        urlDialog();
    }

    private void visible() {
        btEdit.setVisibility(View.VISIBLE);
        viewPhotorek.setVisibility(View.VISIBLE);
        viewPhotoSelfi.setVisibility(View.VISIBLE);
        viewPhotoktp.setVisibility(View.VISIBLE);
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
        stringRequest = new StringRequest(Request.Method.GET, URL_POST_PROFIL + id_kar, new Response.Listener<String>() {
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

    private void urlDialog() {
        list_data = new ArrayList<ModelUrl>();

        stringRequest = new StringRequest(Request.Method.GET, URL_VIEW + id_kar, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("responese url", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_ktp"));
                        list_data.add(model);
                        urlKtp = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("ktp", urlKtp);
                    }

                    JSONArray jsonArray2 = new JSONArray(response);
                    for (int a = 0; a < jsonArray2.length(); a++) {
                        JSONObject json = jsonArray2.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_selfi"));
                        list_data.add(model);
                        urlSelfi = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("pap", urlSelfi);
                    }

                    JSONArray jsonArray3 = new JSONArray(response);
                    for (int a = 0; a < jsonArray3.length(); a++) {
                        JSONObject json = jsonArray3.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_rek"));
                        list_data.add(model);
                        urlRek = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("rek", urlRek);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void ktpDialog() {
        final Dialog dialog = new Dialog(EditAkunActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_view_fotoktp, null);

        ImageView Image = view.findViewById(R.id.ImgKtp);
        TextView info = view.findViewById(R.id.information);
        if (urlKtp != null) {
            Glide.with(this).load(urlKtp)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Image);

        } else {
            info.setText("Image Not Found In Server");
            Image.setImageResource(R.drawable.noimage);
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    private void selfiDialog() {
        final Dialog dialog = new Dialog(EditAkunActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_view_fotoktp, null);

        ImageView Image = view.findViewById(R.id.ImgKtp);
        TextView info = view.findViewById(R.id.information);
        if (urlSelfi != null) {
            Glide.with(this).load(urlSelfi)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Image);

        } else {
            info.setText("Image Not Found In Server");
            Image.setImageResource(R.drawable.noimage);
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    private void rekDialog() {
        final Dialog dialog = new Dialog(EditAkunActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_view_fotoktp, null);

        ImageView Image = view.findViewById(R.id.ImgKtp);
        TextView info = view.findViewById(R.id.information);
        if (urlRek != null) {
            Glide.with(this).load(urlRek)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Image);

        } else {
            info.setText("Image Not Found In Server");
            Image.setImageResource(R.drawable.noimage);
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();

    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.btEdit, R.id.viewPhotoktp, R.id.viewPhotoSelfi, R.id.viewPhotorek})
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
            case R.id.viewPhotoktp:
                ktpDialog();
                break;

            case R.id.viewPhotoSelfi:
                selfiDialog();
                break;

            case R.id.viewPhotorek:
                rekDialog();
                break;
        }
    }

}
