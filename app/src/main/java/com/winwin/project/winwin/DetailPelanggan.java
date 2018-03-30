package com.winwin.project.winwin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Model.ModelDetailDataClient;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_DETAIL;

public class DetailPelanggan extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.etNoHP)
    TextView etNoHP;
    @BindView(R.id.btVisit)
    Button btVisit;
    SharedPreferences sharedpreferences;
    String id_klien, id_pengajuan;
    @BindView(R.id.etNoOptional)
    TextView etNoOptional;
    @BindView(R.id.etAlamatRumah)
    TextView etAlamatRumah;
    @BindView(R.id.etNamaKantor)
    TextView etNamaKantor;
    @BindView(R.id.etAlamatKantor)
    TextView etAlamatKantor;
    @BindView(R.id.etNama)
    TextView etNama;
    @BindView(R.id.linNone)
    LinearLayout linNone;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;

    private OwnProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private final int MY_SOCKET_TIMEOUT_MS = 10 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pelanggan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id_klien = intent.getStringExtra("id_client");
        id_pengajuan = intent.getStringExtra("pengajuan_id");
        progressDialog = new OwnProgressDialog(DetailPelanggan.this);
        requestQueue = Volley.newRequestQueue(DetailPelanggan.this);
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
        DataAda();
    }


    private void load() {
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, URL_DETAIL + id_klien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelDetailDataClient dataClient = new ModelDetailDataClient();
                        dataClient.setNama(json.getString("cli_nama_lengkap"));
                        dataClient.setNohp(json.getString("cli_handphone"));
                        dataClient.setAlamat(json.getString("cli_alamat"));
                        dataClient.setNooptional(json.getString("cli_telepon_perusahaan"));
                        dataClient.setAlamat_perusahaan(json.getString("cli_alamat_perusahaan"));
                        dataClient.setPerusahaan(json.getString("cli_perusahaan"));

                        etNama.setText(dataClient.getNama());
                        etNoHP.setText(dataClient.getNohp());
                        etNoOptional.setText(dataClient.getNooptional());
                        etAlamatRumah.setText(dataClient.getAlamat());
                        etNamaKantor.setText(dataClient.getPerusahaan());
                        etAlamatKantor.setText(dataClient.getAlamat_perusahaan());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }

                if (etNama.getText().equals("") | etNoHP.getText().equals("") | etNoOptional.getText().equals("") | etAlamatRumah.getText().equals("")) {
                    DataKosong();
                } else {
                    DataAda();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (etNama.getText().equals("") | etNoHP.getText().equals("") | etNoOptional.getText().equals("") | etAlamatRumah.getText().equals("")) {
                    DataKosong();
                } else {
                    DataAda();
                }
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }

                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailPelanggan.this, "timeout", Toast.LENGTH_SHORT).show();
                    if (Swipe != null) {
                        Swipe.setRefreshing(false);
                    }
                    if (etNama.getText().equals("") | etNoHP.getText().equals("") | etNoOptional.getText().equals("") | etAlamatRumah.getText().equals("")) {
                        DataKosong();
                    } else {
                        DataAda();
                    }
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailPelanggan.this, "no connection", Toast.LENGTH_SHORT).show();
                    if (etNama.getText().equals("") | etNoHP.getText().equals("") | etNoOptional.getText().equals("") | etAlamatRumah.getText().equals("")) {
                        DataKosong();
                    } else {
                        DataAda();
                    }
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private void DataKosong() {
        btVisit.setVisibility(View.GONE);
        linNone.setVisibility(View.VISIBLE);
    }

    private void DataAda() {
        btVisit.setVisibility(View.VISIBLE);
        linNone.setVisibility(View.GONE);
    }


    @OnClick({R.id.ic_home, R.id.img_back, R.id.btVisit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(DetailPelanggan.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btVisit:

                if (etNama.getText().equals("") | etNoHP.getText().equals("") | etNamaKantor.getText().equals("")) {
                    DataKosong();
                    Toast.makeText(DetailPelanggan.this, "Data Tidak ada! " + "\n" + "Mohon Refresh Ulang", Toast.LENGTH_SHORT).show();
                } else {
                    intent = new Intent(DetailPelanggan.this, PengambilanVisit.class);
                    intent.putExtra("id_client", id_klien);
                    intent.putExtra("pengajuan_id", id_pengajuan);
                    startActivity(intent);
                }

                break;
        }
    }
}
