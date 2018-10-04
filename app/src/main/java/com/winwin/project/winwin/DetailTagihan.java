package com.winwin.project.winwin;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
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
import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winwin.project.winwin.Model.ModelDetailDataClient;
import com.winwin.project.winwin.Model.ModelUrl;
import com.winwin.project.winwin.Setting.DecimalsFormat;
import com.winwin.project.winwin.Setting.OwnProgressDialog;
import com.winwin.project.winwin.Utils.HttpsTrustManager;

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
    String pengajuanid;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    @BindView(R.id.btndetailpembayaran)
    Button btndetailpembayaran;
    @BindView(R.id.btnTulisCatatan)
    Button btnTulisCatatan;
    @BindView(R.id.btnKunjungan)
    Button btnKunjungan;
    @BindView(R.id.btnLihatLokasi)
    Button btnLihatLokasi;
    @BindView(R.id.btnDebt)
    Button btnDebt;
    @BindView(R.id.txtBiayaPerpanjangan)
    TextView txtBiayaPerpanjangan;
    @BindView(R.id.txttelahdibayar)
    TextView txttelahdibayar;
    @BindView(R.id.txtSisa)
    TextView txtSisa;
    @BindView(R.id.rowPerpanjangan)
    TableRow rowPerpanjangan;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private OwnProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    ArrayList<ModelUrl> list_data;
    String urlKtp;
    String Lat, Lang;
    private final int MY_SOCKET_TIMEOUT_MS = 60 * 1000;
    LocationManager locationManager;
    boolean GpsStatus;
    String provider, slat, slang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan);
        ButterKnife.bind(this);
        HttpsTrustManager.allowAllSSL();
        Intent intent = getIntent();
        id_klien = intent.getStringExtra("id_client");
        progressDialog = new OwnProgressDialog(DetailTagihan.this);

        Log.d("idku", id_klien);
        idClient.setText(id_klien);
        requestQueue = Volley.newRequestQueue(DetailTagihan.this);

        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });
        load();
        viewKtpDialog();

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        }

    }

    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());

        slat = String.valueOf(lat);
        slang = String.valueOf(lng);


    }

//    private void Actionsdaf() {
//
//        StringRequest strReq = new StringRequest(Request.Method.POST, "http://hq.ppgwinwin.com/winwin/api/update_lokasi.php", new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("cli_id", id_klien);
//                params.put("latitude", slat);
//                params.put("longitude", slang);
//                return params;
//            }
//
//        };
//        requestQueue.add(strReq);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckGpsStatus();
        if (GpsStatus == true) {
        } else {
            Dialog();
            Toast.makeText(DetailTagihan.this, "GPS IS NON ACTIVE", Toast.LENGTH_SHORT).show();
        }
    }

    private void Dialog() {
        final Dialog dialog = new Dialog(DetailTagihan.this);
        LayoutInflater inflater = (LayoutInflater) DetailTagihan.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_dialog_gps, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        Button btnSubmit = (Button) view.findViewById(R.id.btActive);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });


        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void CheckGpsStatus() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
                        String kelurahan = json.getString("cli_kelurahan");
                        String kecamatan = json.getString("cli_kecamatan");
                        String kota = json.getString("cli_kota");
                        ModelDetailDataClient dataClient = new ModelDetailDataClient();
                        dataClient.setNama(json.getString("cli_nama_lengkap"));
                        dataClient.setNohp(json.getString("cli_handphone"));
                        dataClient.setAlamat(json.getString("cli_alamat") + "\nKel. " + kelurahan + "\nKec. " + kecamatan + "\n" + kota);
                        dataClient.setKtp(json.getString("cli_no_ktp"));
                        dataClient.setPerusahaan(json.getString("cli_perusahaan"));
                        dataClient.setPosisi(json.getString("cli_posisi"));
                        dataClient.setTelp_perusahaan(json.getString("cli_telepon_perusahaan"));
                        dataClient.setAlamat_perusahaan(json.getString("cli_alamat_perusahaan"));
                        dataClient.setPengajuan_tgl(json.getString("pengajuan_tanggal"));
                        dataClient.setPgj_nilai_pgj(json.getString("pengajuan_nilai_disetujui"));
                        dataClient.setPgjtotal(json.getString("pengajuan_total_disetujui"));
                        dataClient.setDenda_biaya(json.getString("denda_biaya"));
                        dataClient.setKomisi(json.getString("prosen_komisi"));
                        dataClient.setOperasional(json.getString("prosen_operasional"));
                        dataClient.setPeng_id(json.getString("pengajuan_id"));
                        dataClient.setStatus(json.getString("pengajuan_stat_lunas"));
                        dataClient.setSisa(json.getString("sisa"));
                        dataClient.setTotal_bayar(json.getString("total_bayar"));
                        dataClient.setTotal_biaya_perpanjangan(json.getString("total_biaya_perpanjangan"));
                        dataClient.setTotalhutang(json.getString("total_hutang"));

                        pengajuanid = dataClient.getPeng_id();
                        Lat = json.getString("lat");
                        Lang = json.getString("lng");

                        txtNamaClient.setText(dataClient.getNama());
                        txtNoHpNas.setText(dataClient.getNohp());
                        txtAlamat.setText(dataClient.getAlamat());
                        txtFotoKtp.setText(dataClient.getKtp());
                        txtPerusahaan.setText(dataClient.getPerusahaan());
                        txtPosisi.setText(dataClient.getPosisi());
                        txtNoTelpon.setText(dataClient.getTelp_perusahaan());
                        txtAlamatPerusahaan.setText(dataClient.getAlamat_perusahaan());
                        txtTglPinjam.setText(dataClient.getPengajuan_tgl());

                        txtJumlahPinjam.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getPgj_nilai_pgj()) + desimal);
                        txtTotalPinjam.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getPgjtotal()) + desimal);
                        txtDenda.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getDenda_biaya()) + desimal);

//                        txtTotalNilaiHutang.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getTotalhutang()) + desimal);
                        txtTotalNilaiHutang.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getSisa()) + desimal);

                        if (dataClient.getStatus().equals("f")) {
                            txtnilaikomisi.setText("Rp. 0");
                        }
                        txtnilaikomisi.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getKomisi()) + desimal);
                        txtNilaiBiayaOperasional.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getOperasional()) + desimal);

                        if (dataClient.getTotal_biaya_perpanjangan().equals("0")) {
                            rowPerpanjangan.setVisibility(View.GONE);
                        }
//                        txtSisa.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getSisa()) + desimal);
                        txtBiayaPerpanjangan.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getTotal_biaya_perpanjangan()) + desimal);
                        txttelahdibayar.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(dataClient.getTotal_bayar()) + desimal);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressDialog.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
                if (error instanceof TimeoutError) {
                    Toast.makeText(DetailTagihan.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DetailTagihan.this, "no connection to server", Toast.LENGTH_SHORT).show();
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
                        urlKtp = "https://hq.ppgwinwin.com/winwin/home/uploads/" + model.getKodePelanggan() + "/" + model.getNamaDocument();
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

        requestQueue.add(stringRequest);
    }

    private void diagnosisDialog() {
        final Dialog dialog = new Dialog(DetailTagihan.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_view_fotoktp, null);

        ImageView Image = view.findViewById(R.id.ImgKtp);
        Image.setOnTouchListener(new ImageMatrixTouchHandler(DetailTagihan.this));
        TextView info = view.findViewById(R.id.information);
        if (urlKtp != null) {
            Glide.with(this).load(urlKtp)
                    .crossFade()
                    .placeholder(R.drawable.lodingimages)
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
                bundles.putString("pengajuan_id", pengajuanid);
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

    @OnClick({R.id.btnKunjungan, R.id.btnDebt})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btnKunjungan:
                Intent intent = new Intent(DetailTagihan.this, DataKunjunganActivity.class);
                intent.putExtra("pengajuan_id", pengajuanid);
                intent.putExtra("client_id", id_klien);
                startActivity(intent);
                break;

            case R.id.btnDebt:
                intent = new Intent(DetailTagihan.this, BadDebt.class);
                intent.putExtra("pengajuan_id", pengajuanid);
                startActivity(intent);
                break;
        }
    }
}
