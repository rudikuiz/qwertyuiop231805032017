package com.winwin.project.winwin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.winwin.project.winwin.Config.AppController;
import com.winwin.project.winwin.Model.ModelDetailDataClient;
import com.winwin.project.winwin.Model.getID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
    @BindView(R.id.uploadPhoto)
    TextView uploadPhoto;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.tglkunjungan)
    TextView tglkunjungan;
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
    String member_id, getClientId, values, totalpinjaman, nilai_bayar, id_pengajuan, dateString, getId_pengajuan;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    @BindView(R.id.btTakeDate)
    Button btTakeDate;
    @BindView(R.id.etDate)
    TextView etDate;
    @BindView(R.id.takeFoto)
    TextView takeFoto;
    @BindView(R.id.ImgPenagihan)
    ImageView ImgPenagihan;
    @BindView(R.id.jumlah)
    EditText jumlah;
    @BindView(R.id.txtTulisCatatan)
    EditText txtTulisCatatan;
    int PICK_IMAGE_REQUEST = 1, bitmap_size = 100;
    Bitmap decoded;

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

        rg.setOnCheckedChangeListener(this);
        Intent intent = getIntent();
        getClientId = intent.getStringExtra("client_id");
        getId_pengajuan = intent.getStringExtra("pengajuan_id");
        getPengajuanID();
        getNilaiBayar();
    }


    private void TambahkanDataKunjnungan() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_POST_DATA_KUNJUNGAN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(DataKunjunganActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                hideDialog();
                finish();
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
                params.put("id_pengajuan", getId_pengajuan);
                params.put("id_karyawan", member_id);
                params.put("id_klien", getClientId);
                params.put("tipe", values);
                params.put("foto", getStringImage(decoded));
                params.put("tgl_janji", etDate.getText().toString());
                params.put("nominal", jumlah.getText().toString());
                params.put("tgl_cicilan", getTanggal());
                params.put("keterangan", txtTulisCatatan.getText().toString());
                Log.d("asf", params.toString());
                return params;
            }

        };
        requestQueue.add(strReq);
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

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgPenagihan.setImageBitmap(decoded);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (takeFoto.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView(bitmap);
            }
        }
    }

    @OnClick({R.id.img_back, R.id.btnSimpan, R.id.ic_home, R.id.btTakeDate, R.id.takeFoto})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btnSimpan:
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    TambahkanDataKunjnungan();

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
            case R.id.btTakeDate:
                CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                        .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                            @Override
                            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                                int month = monthOfYear + 1;
                                dateString = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", dayOfMonth);
                                etDate.setText(dateString);
                            }
                        });
                cdp.show(getSupportFragmentManager(), null);
                break;
            case R.id.takeFoto:
                takeImageFromCamera(view);
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbLunas) {
            values = "lunas";
        } else if (checkedId == R.id.rbPelangganLunas) {
            values = "janji_bayar";
        } else if (checkedId == R.id.pelanggankop) {
            values = "cicilan";
        } else {
            values = "lain-lain";
        }

        Log.d("isi", values);
    }
}
