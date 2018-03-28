package com.winwin.project.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winwin.project.winwin.Model.ModelDetailDataClient;
import com.winwin.project.winwin.Model.ModelUrl;
import com.winwin.project.winwin.Setting.DecimalsFormat;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_DETAIL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_IMAGE;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_IMAGE_FROM_FOLDER;

public class DetailTagihan extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ic_logo_client)
    ImageView icLogoClient;
    @BindView(R.id.txtNamaClient)
    TextView txtNamaClient;
    @BindView(R.id.txtNoHpNas)
    TextView txtNoHpNas;
    @BindView(R.id.txtAlamat)
    TextView txtAlamat;
    @BindView(R.id.txtFotoKtp)
    TextView txtFotoKtp;
    @BindView(R.id.tvdatapekerjaan)
    TextView tvdatapekerjaan;
    @BindView(R.id.txtPerusahaan)
    TextView txtPerusahaan;
    @BindView(R.id.txtPosisi)
    TextView txtPosisi;
    @BindView(R.id.txtNoTelpon)
    TextView txtNoTelpon;
    @BindView(R.id.txtAlamatPerusahaan)
    TextView txtAlamatPerusahaan;
    @BindView(R.id.tvdetail)
    TextView tvdetail;
    @BindView(R.id.txtTglPinjam)
    TextView txtTglPinjam;
    @BindView(R.id.txtJumlahPinjam)
    TextView txtJumlahPinjam;
    @BindView(R.id.txtTotalPinjam)
    TextView txtTotalPinjam;
    @BindView(R.id.txtDenda)
    TextView txtDenda;
    @BindView(R.id.txtTotalHutang)
    TextView txtTotalHutang;
    @BindView(R.id.txtTotalNilaiHutang)
    TextView txtTotalNilaiHutang;
    @BindView(R.id.txtNilaiBiayaOperasional)
    TextView txtNilaiBiayaOperasional;
    @BindView(R.id.txtnilaikomisi)
    TextView txtnilaikomisi;
    @BindView(R.id.btndetailpembayaran)
    Button btndetailpembayaran;
    @BindView(R.id.btnTulisCatatan)
    Button btnTulisCatatan;
    @BindView(R.id.id_client)
    TextView idClient;

    String id_klien;
    String Rupiah = "Rp. ";
    String desimal = ",-";

    ImageView icHome;
    @BindView(R.id.nilaiKomisi)
    TextView nilaiKomisi;
    @BindView(R.id.nilaiOperasional)
    TextView nilaiOperasional;
    @BindView(R.id.btnKunjungan)
    Button btnKunjungan;
    @BindView(R.id.btnLihatLokasi)
    Button btnLihatLokasi;
    @BindView(R.id.btnDebt)
    Button btnDebt;
    String pengajuanid;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private OwnProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    ArrayList<ModelUrl> list_data;
    String urlKtp;
    String Lat, Lang;
    private final int MY_SOCKET_TIMEOUT_MS = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id_klien = intent.getStringExtra("id_client");
        progressDialog = new OwnProgressDialog(DetailTagihan.this);

        Log.d("idku", id_klien);
        idClient.setText(id_klien);
        requestQueue = Volley.newRequestQueue(DetailTagihan.this);
        load();
        viewKtpDialog();

    }


    private void load() {
        progressDialog.show();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_DETAIL + id_klien, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("datakuz", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("client");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelDetailDataClient dataClient = new ModelDetailDataClient();
                        dataClient.setNama(json.getString("cli_nama_lengkap"));
                        dataClient.setNohp(json.getString("cli_handphone"));
                        dataClient.setAlamat(json.getString("cli_alamat"));
                        dataClient.setKtp(json.getString("cli_no_ktp"));
                        dataClient.setPerusahaan(json.getString("cli_perusahaan"));
                        dataClient.setPosisi(json.getString("cli_posisi"));
                        dataClient.setTelp_perusahaan(json.getString("cli_telepon_perusahaan"));
                        dataClient.setAlamat_perusahaan(json.getString("cli_alamat_perusahaan"));
                        dataClient.setPengajuan_tgl(json.getString("pengajuan_tanggal"));
                        dataClient.setPgj_nilai_pgj(json.getString("pengajuan_nilai_pengajuan"));
                        dataClient.setPgjtotal(json.getString("pengajuan_total_pengajuan"));
                        dataClient.setDenda_biaya(json.getString("denda_biaya"));
                        dataClient.setKomisi(json.getString("prosen_komisi"));
                        dataClient.setOperasional(json.getString("prosen_operasional"));
                        dataClient.setPeng_id(json.getString("pengajuan_id"));
                        pengajuanid = dataClient.getPeng_id();
                        Lat = json.getString("lat");
                        Lang = json.getString("lng");

                        txtNamaClient.setText(json.getString("cli_nama_lengkap"));
                        txtNoHpNas.setText(json.getString("cli_handphone"));
                        txtAlamat.setText(json.getString("cli_alamat"));
                        txtFotoKtp.setText(json.getString("cli_no_ktp"));
                        txtPerusahaan.setText(json.getString("cli_perusahaan"));
                        txtPosisi.setText(json.getString("cli_posisi"));
                        txtNoTelpon.setText(json.getString("cli_telepon_perusahaan"));
                        txtAlamatPerusahaan.setText(json.getString("cli_alamat_perusahaan"));

                        txtnilaikomisi.setText(json.getString("prosen_komisi"));
                        txtNilaiBiayaOperasional.setText(json.getString("prosen_operasional"));
                        txtTglPinjam.setText(json.getString("pengajuan_tanggal"));

                        txtJumlahPinjam.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(json.getString("pengajuan_nilai_pengajuan")) + desimal);
                        txtTotalPinjam.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(json.getString("pengajuan_total_pengajuan")) + desimal);
                        txtDenda.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(json.getString("denda_biaya")) + desimal);

                        if (txtNilaiBiayaOperasional.getText().equals("100")) {
                            txtNilaiBiayaOperasional.setText("0");
                        }

                        if (txtnilaikomisi.getText().equals("100")) {
                            txtnilaikomisi.setText("0");
                        }

                        int angka1 = Integer.parseInt(json.getString("pengajuan_nilai_pengajuan"));
                        int angka2 = Integer.parseInt(json.getString("pengajuan_total_pengajuan"));
                        int angka3 = Integer.parseInt(json.getString("denda_biaya"));
                        int angka4 = Integer.parseInt(json.getString("prosen_operasional"));
                        int angka5 = Integer.parseInt(json.getString("prosen_komisi"));

                        String hasil = Integer.toString(angka1 + angka2 + angka3);
                        String hasilOperasional = Integer.toString(Integer.parseInt(hasil) * angka4 / 100);
                        String hasilKomisi = Integer.toString(Integer.parseInt(hasil) * angka5 / 100);

                        txtTotalNilaiHutang.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasil) + desimal);
                        txtNilaiBiayaOperasional.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasilOperasional) + desimal);
                        txtnilaikomisi.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(hasilKomisi) + desimal);
                        Log.d("hasil:", hasil);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailTagihan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailTagihan.this, "no connection", Toast.LENGTH_SHORT).show();
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

    private void viewKtpDialog() {
        list_data = new ArrayList<ModelUrl>();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_IMAGE + id_klien, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("responese url", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setKodePelanggan(json.getString("cli_nomor_pelanggan"));
                        model.setNamaDocument(json.getString("cli_doc_file"));
                        list_data.add(model);
                        urlKtp = URL_GET_IMAGE_FROM_FOLDER + model.getKodePelanggan() + "/" + model.getNamaDocument();
                        Log.d("hasil url", urlKtp);
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

    private void diagnosisDialog() {
        final Dialog dialog = new Dialog(DetailTagihan.this);
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

    @OnClick({R.id.img_back, R.id.txtFotoKtp, R.id.btndetailpembayaran, R.id.btnTulisCatatan, R.id.btnLihatLokasi, R.id.txtFotoKtpshow, R.id.ic_home})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btndetailpembayaran:
                Bundle bundle = new Bundle();
                bundle.putString("id", idClient.getText().toString());
                Intent i = new Intent(DetailTagihan.this, HistoryPembayaran.class);
                i.putExtras(bundle);
                startActivity(i);
                break;
            case R.id.btnTulisCatatan:
                Bundle bundles = new Bundle();
                bundles.putString("id", idClient.getText().toString());
                Intent is = new Intent(DetailTagihan.this, TulisCatatan.class);
                is.putExtras(bundles);
                startActivity(is);
                break;
            case R.id.btnLihatLokasi:
                Intent intents = new Intent(DetailTagihan.this, MapsActivity.class);
                intents.putExtra("nama", txtNamaClient.getText());
                intents.putExtra("jumlah", txtTotalNilaiHutang.getText());
                intents.putExtra("lat", Lat);
                intents.putExtra("lang", Lang);
                intents.putExtra("client_id", id_klien);
                startActivity(intents);
//                Toast.makeText(this, "Active, Nothing Menu. Wait update from Programmer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.txtFotoKtpshow:
                diagnosisDialog();
                break;
            case R.id.ic_home:
                Intent intent = new Intent(DetailTagihan.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    @OnClick({R.id.btnKunjungan, R.id.btnLihatLokasi, R.id.btnDebt})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btnKunjungan:
                Intent intent = new Intent(DetailTagihan.this, DataKunjunganActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLihatLokasi:
                Intent intents = new Intent(DetailTagihan.this, MapsActivity.class);
                intents.putExtra("nama", txtNamaClient.getText());
                intents.putExtra("jumlah", txtTotalNilaiHutang.getText());
                intents.putExtra("lat", Lat);
                intents.putExtra("lang", Lang);
                intents.putExtra("client_id", id_klien);
                startActivity(intents);
                break;
            case R.id.btnDebt:
                intent = new Intent(DetailTagihan.this, BadDebt.class);
                intent.putExtra("pengajuan_id",  pengajuanid);
                startActivity(intent);
                break;
        }
    }
}
