package com.winwin.project.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterJatuhTempo;
import com.winwin.project.winwin.Model.ModelJatuhTempo;
import com.winwin.project.winwin.Model.ModelTableKomisi;
import com.winwin.project.winwin.Setting.DecimalsFormat;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_DETAIL_PEMBAYARAN;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_JATUH_TEMPO;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_KOMISI;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_USERNAME;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.ic_logo)
    ImageView icLogo;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.editAkun)
    TextView editAkun;
    @BindView(R.id.jatuhTempoBerikutnya)
    TextView jatuhTempoBerikutnya;
    @BindView(R.id.DetailPembayaran)
    TextView DetailPembayaran;
    @BindView(R.id.UangOperasional)
    TextView UangOperasional;
    @BindView(R.id.UangKomisi)
    TextView UangKomisi;
    String username;

    SwipeRefreshLayout Swipe;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<ModelJatuhTempo> listData;
    OwnProgressDialog loading;
    SharedPreferences sharedpreferences;
    String member_id;
    @BindView(R.id.ic_home)
    ImageView icHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        jatuhTempoBerikutnya.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        txtUser.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        DetailPembayaran.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtUser.setText(username);
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        getJSON();

    }


    @OnClick({R.id.img_back, R.id.txtUser, R.id.jatuhTempoBerikutnya, R.id.DetailPembayaran, R.id.editAkun, R.id.ic_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txtUser:
                break;
            case R.id.jatuhTempoBerikutnya:
                DialogForm();
                break;
            case R.id.DetailPembayaran:
                DialogForm2();
                break;
            case R.id.editAkun:
                Intent intent = new Intent(ProfileActivity.this, EditAkunActivity.class);
                startActivity(intent);
                break;
            case R.id.ic_home:
                Intent intents = new Intent(ProfileActivity.this, MenuActivity.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intents);
                finish();
                break;
        }
    }


    private void DialogForm() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_jatuh_tempo_berikutnya, null);

        final RecyclerView rvJatuhTempo = view.findViewById(R.id.rvJatuhTempo);

        ImageView imageView = view.findViewById(R.id.close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtUser.setText(username);

        rvJatuhTempo.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1,
                GridLayoutManager.VERTICAL, false);
        rvJatuhTempo.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        loading = new OwnProgressDialog(ProfileActivity.this);

        loading.show();

        listData = new ArrayList<ModelJatuhTempo>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_JATUH_TEMPO + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelJatuhTempo modelMenu = new ModelJatuhTempo();
                        modelMenu.setNama(json.getString("namalengkap"));
                        modelMenu.setNilai(json.getString("jumlah"));
                        modelMenu.setTempo(json.getString("jatuhtempo"));
                        listData.add(modelMenu);
                    }

                    AdapterJatuhTempo adapter = new AdapterJatuhTempo(listData, ProfileActivity.this);
                    rvJatuhTempo.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();
//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        });
        requestQueue.add(stringRequest);

        dialog.setContentView(view);

        dialog.show();


    }

    private void DialogForm2() {
        final Dialog dialog = new Dialog(ProfileActivity.this);
        LayoutInflater inflater = (LayoutInflater) ProfileActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_detail_pembayaran, null);
        ImageView imageView = view.findViewById(R.id.ic_close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        final RecyclerView rvJatuhTempo = view.findViewById(R.id.rvJDetailPembayaran);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtUser.setText(username);

        rvJatuhTempo.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1,
                GridLayoutManager.VERTICAL, false);
        rvJatuhTempo.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(ProfileActivity.this);

        loading = new OwnProgressDialog(ProfileActivity.this);

        loading.show();
//        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getJSON();
//            }
//        });
        listData = new ArrayList<ModelJatuhTempo>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAIL_PEMBAYARAN + member_id, new Response.Listener<String>() {
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
                        listData.add(modelMenu);
                    }

                    AdapterJatuhTempo adapter = new AdapterJatuhTempo(listData, ProfileActivity.this);
                    rvJatuhTempo.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();
//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
//                if (Swipe != null) {
//                    Swipe.setRefreshing(false);
//                }
            }
        });
        requestQueue.add(stringRequest);

        dialog.setContentView(view);

        dialog.show();

    }

    private void getJSON() {
        stringRequest = new StringRequest(Request.Method.GET, URL_KOMISI + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                        ModelTableKomisi modelMenu = new ModelTableKomisi();

                    modelMenu.setBiayaope(json.getString("operasional"));
                    modelMenu.setKomisi(json.getString("komisi"));

                    UangOperasional.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(modelMenu.getBiayaope()) + ",-");
                    UangKomisi.setText("Rp. " + DecimalsFormat.priceWithoutDecimal(modelMenu.getKomisi()) + ",-");



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
