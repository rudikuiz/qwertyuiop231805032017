package com.winwin.project.winwin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Config.AppController;
import com.winwin.project.winwin.Model.ModelDetailDataClient;
import com.winwin.project.winwin.Model.getID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_DETAIL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ID_PENGAJUAN;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_DATA_KUNJUNGAN;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DataKunjunganActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.kunjungan)
    TextView kunjungan;
    @BindView(R.id.lblkunjungan)
    TextView lblkunjungan;
    @BindView(R.id.jamkunjungan)
    TextView jamkunjungan;
    @BindView(R.id.viewPhoto)
    TextView viewPhoto;
    @BindView(R.id.uploadPhoto)
    TextView uploadPhoto;
    @BindView(R.id.spinHari)
    Spinner spinHari;
    EditText txtTulisCatatan;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.tglkunjungan)
    TextView tglkunjungan;
    @BindView(R.id.tahun)
    TextView tahun;
    @BindView(R.id.spipnBln)
    Spinner spipnBln;
    @BindView(R.id.rbLunas)
    RadioButton rbLunas;
    @BindView(R.id.rbPelangganLunas)
    RadioButton rbPelangganLunas;
    @BindView(R.id.rg)
    RadioGroup rg;
    @BindView(R.id.pelanggankop)
    RadioButton pelanggankop;
    @BindView(R.id.rbPelangganCicilan)
    RadioButton rbPelangganCicilan;
    @BindView(R.id.rbPelangganTD)
    RadioButton rbPelangganTD;
    @BindView(R.id.rbPelangganTK)
    RadioButton rbPelangganTK;

    RequestQueue requestQueue;
    SharedPreferences sharedpreferences;
    String member_id, getClientId, values, totalpinjaman, nilai_bayar, id_pengajuan;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_kunjungan);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
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

        requestQueue = Volley.newRequestQueue(DataKunjunganActivity.this);

        tglkunjungan.setText(getTanggal());
        jamkunjungan.setText(getWaktu());
        tahun.setText(getTahun());

        rg.setOnCheckedChangeListener(this);
        Intent intent = getIntent();
        getClientId = intent.getStringExtra("id_client");
        getPengajuanID();
        getNilaiBayar();
    }

    private void TambahkanDataKunjnungan(final String status, final String nilaibayar, final String id_pengajuans) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_POST_DATA_KUNJUNGAN+id_pengajuan, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(DataKunjunganActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                hideDialog();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(DataKunjunganActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                        txtTulisCatatan.getText().clear();

                    } else {
                        Toast.makeText(DataKunjunganActivity.this,
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

                Map<String, String> params = new HashMap<String, String>();
                params.put("status", status);
                params.put("nilai_bayar", nilaibayar);
                params.put("id_pengajuan", id_pengajuans);
                return params;
            }

        };
        requestQueue.add(strReq);
        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void getNilaiBayar() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAIL + getClientId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("nilaibayar", response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("client");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelDetailDataClient dataClient = new ModelDetailDataClient();

                        dataClient.setPgj_nilai_pgj(json.getString("pengajuan_nilai_pengajuan"));
                        dataClient.setPgjtotal(json.getString("pengajuan_total_pengajuan"));
                        dataClient.setDenda_biaya(json.getString("denda_biaya"));

                        int angka1 = Integer.parseInt(json.getString("pengajuan_nilai_pengajuan"));
                        int angka2 = Integer.parseInt(json.getString("pengajuan_total_pengajuan"));
                        int angka3 = Integer.parseInt(json.getString("denda_biaya"));

                        totalpinjaman = Integer.toString(angka1 + angka2 + angka3);
                        nilai_bayar = totalpinjaman + angka3;
//                        txtTotalNilaiHutang.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasil) + desimal);
//                        txtNilaiBiayaOperasional.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasilOperasional) + desimal);
//                        txtnilaikomisi.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasilKomisi) + desimal);
                        Log.d("bayar ", nilai_bayar);

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

    private void getPengajuanID() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_ID_PENGAJUAN + getClientId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("jsonwe: ", response + URL_GET_ID_PENGAJUAN + getClientId);

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        getID modelMenu = new getID();
                        modelMenu.setId(json.getString("pengajuan_id"));
                        id_pengajuan = modelMenu.getId();
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

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getTahun() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String getWaktu() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }


    @OnClick({R.id.img_back, R.id.btnSimpan, R.id.ic_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btnSimpan:
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    TambahkanDataKunjnungan(values, nilai_bayar, id_pengajuan);

                } else {
                    Toast.makeText(DataKunjunganActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ic_home:
                Intent intent = new Intent(DataKunjunganActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbLunas) {
            values = "lunas";
        } else {
            values = "cicilan";
        }

        Log.d("isi", values);
    }
}
