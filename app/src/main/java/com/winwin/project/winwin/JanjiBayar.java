package com.winwin.project.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.winwin.project.winwin.Model.ModelClient;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_JANJI_TRIAL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_REQUES_BAYAR;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class JanjiBayar extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.linBelumAktif)
    LinearLayout linBelumAktif;
    @BindView(R.id.spDate)
    TextView spDate;
    @BindView(R.id.btAjukan)
    Button btAjukan;
    @BindView(R.id.linRequestJanji)
    LinearLayout linRequestJanji;
    @BindView(R.id.linTidakDisetujui)
    LinearLayout linTidakDisetujui;
    @BindView(R.id.etTglDisetujui)
    TextView etTglDisetujui;
    @BindView(R.id.linDisetujui)
    LinearLayout linDisetujui;
    @BindView(R.id.etTanggal)
    TextView etTanggal;
    String dateString;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    String idclient;
    SharedPreferences sharedpreferences;
    @BindView(R.id.etNamaClient)
    TextView etNamaClient;
    @BindView(R.id.btRequest)
    Button btRequest;
    String namac, id_cli, member_id, pengajuan_id;
    OwnProgressDialog loading;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_janji_bayar);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        namac = intent.getStringExtra("nama");
        id_cli = intent.getStringExtra("id_client");
        pengajuan_id = intent.getStringExtra("pengajuan_id");
        etNamaClient.setText(namac);

        Log.d("cliid", id_cli);
        Log.d("idpe", pengajuan_id);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");


        DisableAllLinear();
        Aktif();
        etTanggal.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        requestQueue = Volley.newRequestQueue(JanjiBayar.this);
        loading = new OwnProgressDialog(JanjiBayar.this);
    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.etTanggal, R.id.btAjukan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(JanjiBayar.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.etTanggal:
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
//                                dateString = String.format("%02d", dayOfMonth) + "-" + String.format("%02d", month) + "-" + year;
                                dateString = String.format(year + "-" + String.format("%02d", month) + "-" + "%02d", dayOfMonth);
                                spDate.setText(dateString);
                            }
                        });
                cdp.show(getSupportFragmentManager(), null);
                break;
            case R.id.btAjukan:
                janjibayar();

                break;
        }
    }

    private void CekRequestBayar() {
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        idclient = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Log.d("urls", URL_GET_JANJI_TRIAL + idclient);

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_JANJI_TRIAL + idclient, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("asdf", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelClient model = new ModelClient();
                        model.setKode(json.getString("pengajuan_janji_bayar_aktif"));
                        String kode = model.getKode();
                        Log.d("isi", kode);
                        if (kode.equals("1")) {
                            Aktif();
                        } else {
                            belumAktif();
                        }
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

    private void janjibayar() {
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_REQUES_BAYAR, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(JanjiBayar.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                loading.dismiss();
                DialogForm();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error: ", error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
                Toast.makeText(JanjiBayar.this, "Error saat menyimpan", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("pengajuan_janji_bayar_created_by", member_id);
                params.put("pengajuan_janji_bayar_tgl", dateString);
                params.put("pengajuan_id", pengajuan_id);

                Log.d("pasfd", params.toString());
                return params;

            }

        };
        requestQueue.add(strReq);
    }

    private void belumAktif() {
        linBelumAktif.setVisibility(View.VISIBLE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    private void Aktif() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.VISIBLE);
    }

    private void PengajuanDiterima() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    private void DisableAllLinear() {
        linBelumAktif.setVisibility(View.GONE);
        linDisetujui.setVisibility(View.GONE);
        linTidakDisetujui.setVisibility(View.GONE);
        linRequestJanji.setVisibility(View.GONE);
    }

    private void DialogForm() {
        final Dialog dialog = new Dialog(JanjiBayar.this);
        LayoutInflater inflater = (LayoutInflater) JanjiBayar.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_konfirmasi_tujuan, null);
        Button btnSelesai = view.findViewById(R.id.btselesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JanjiBayar.this, ListJanjiBayar.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.setCancelable(false);
        dialog.show();
    }
}
