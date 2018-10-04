package com.winwin.project.winwin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.winwin.project.winwin.Adapter.DashboardAdapter;
import com.winwin.project.winwin.Model.ModelDashboard;
import com.winwin.project.winwin.Model.ModelUrl;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_ADDTUGAS;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_COUNT_NOTIF;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_IMAGE_FROM_FOLDER;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_SEND_LOCATION;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_VIEW;
import static com.winwin.project.winwin.Config.http.TAG_ID;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;
import static com.winwin.project.winwin.Config.http.TAG_USERNAME;


public class MenuActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnClient)
    CircleButton btnClient;
    @BindView(R.id.img_logout)
    ImageView imgLogout;
    @BindView(R.id.btnMenu)
    CircleButton btnMenu;
    String username, member_id, member_id_karyawan;
    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.btnProfil)
    CircleButton btnProfil;
    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.tvClient)
    TextView tvClient;
    StringRequest stringRequest;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.img_notification)
    ImageView imgNotification;
    @BindView(R.id.etcount)
    TextView etcount;
    OwnProgressDialog loading;
    private static final String TAG_SUCCESS = "success";
    RequestQueue requestQueue;
    String member_id_kar;
    SharedPreferences sharedpreferences;
    Button btnSubmit;
    EditText etKeterangan;
    int success;
    @BindView(R.id.ic_logo)
    CircleImageView icLogo;
    private ArrayList<ModelDashboard> arrayList = new ArrayList<>();
    private DashboardAdapter adapter;
    Button acc, noacc;
    String counts, pengajuan_id, nama, alamat, provider, slat, slang;
    TextView Etnama, Etalamat;
    LocationManager locationManager;
    boolean GpsStatus;
    private FusedLocationProviderClient mFusedLocationClient;
    int PROS_ID = 1001;
    ArrayList<ModelUrl> list_data;
    String urlKtp, urlSelfi, urlRek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(MenuActivity.this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        txtUser.setText(username);
        menuku();
        counts = "0";
        rvMenu.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(MenuActivity.this, 2,
                GridLayoutManager.VERTICAL, false);
        rvMenu.setLayoutManager(layoutManager);
        loading = new OwnProgressDialog(MenuActivity.this);
        adapter = new DashboardAdapter(arrayList, MenuActivity.this);
        rvMenu.setAdapter(adapter);


        getNotif();

        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    private void vused() {
        Log.d("fusedzx0", "1");

        if (ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MenuActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(MenuActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MenuActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PROS_ID);
//                ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                        Manifest.permission.ACCESS_FINE_LOCATION);

            } else {

            }

        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object

                                double lat = location.getLatitude();
                                double lon = location.getLongitude();

                                slat = String.valueOf(lat);
                                slang = String.valueOf(lon);
                                Log.d("asdfrT", String.valueOf(lat) + "," + String.valueOf(lon));

                                Actionsdaf();
                            }
                        }
                    });
        }


    }

    public void CheckGpsStatus() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void Actionsdaf() {
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_SEND_LOCATION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("sends", response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("kar_id", member_id);
                params.put("latitude", slat);
                params.put("longitude", slang);
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private void menuku() {
        arrayList.add(new ModelDashboard(R.drawable.daftarklien, "Daftar Client"));
        arrayList.add(new ModelDashboard(R.drawable.ic_komisi, "Komisi"));
        arrayList.add(new ModelDashboard(R.drawable.ic_profile, "Profile"));
        arrayList.add(new ModelDashboard(R.drawable.ic_analistcebt, "Analist Debt"));

    }

    private void getImagesAll() {
        list_data = new ArrayList<ModelUrl>();

        stringRequest = new StringRequest(Request.Method.GET, URL_VIEW + member_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("responese url", response);
                try {


                    JSONArray jsonArray2 = new JSONArray(response);
                    for (int a = 0; a < jsonArray2.length(); a++) {
                        JSONObject json = jsonArray2.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_selfi"));
                        list_data.add(model);
                        urlSelfi = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("pap", urlSelfi);
                        if (model.getLabel() != null && !model.getLabel().equals("null")) {
                            Glide.with(MenuActivity.this).load(urlSelfi)
                                    .crossFade()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(icLogo);

                        } else {
                            icLogo.setImageResource(R.drawable.noimage);
                        }

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

    @OnClick({R.id.img_logout, R.id.btnClient, R.id.btnMenu, R.id.btnProfil, R.id.img_notification})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_logout:
                new AlertDialog.Builder(this)
                        .setMessage("Apakah anda yakin ingin keluar dari aplikasi ini??")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(LoginPage.session_status, false);
                                editor.putString(TAG_ID, null);
                                editor.putString(TAG_USERNAME, null);
                                editor.commit();

                                Intent intent = new Intent(MenuActivity.this, LoginPage.class);
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                break;
            case R.id.btnClient:
                Intent i = new Intent(MenuActivity.this, DaftarClientTagih.class);
                startActivity(i);
                break;
            case R.id.btnMenu:
                Intent in = new Intent(MenuActivity.this, KomisiActivity.class);
                startActivity(in);
                break;
            case R.id.btnProfil:
                startActivity(new Intent(MenuActivity.this, ProfileActivity.class));
//                Toast.makeText(this, "Active, Nothing Menu. Wait update from Programmer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_notification:
                if (counts.equals("1")) {
                    notifDialog();
                } else {
                    startActivity(new Intent(MenuActivity.this, ListNotification.class));
                }

                break;
        }
    }

    private void getNotif() {
        stringRequest = new StringRequest(Request.Method.GET, URL_COUNT_NOTIF + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    etcount.setText(jsonObject.getString("count"));
                    counts = jsonObject.getString("count");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        pengajuan_id = json.getString("pengajuan_id");
                        nama = json.getString("cli_nama_lengkap");
                        alamat = json.getString("cli_alamat");
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


    private void Action(final String aksi, final Dialog dialog) {
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_ADDTUGAS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(MenuActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                if (dialog != null) {
                    dialog.dismiss();
                }

                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(MenuActivity.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MenuActivity.this,
                                jObj.getString("message"), Toast.LENGTH_LONG).show();

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
                Toast.makeText(MenuActivity.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();

                loading.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("pengajuan_id", pengajuan_id);
                params.put("pengajuan_assigned_to", member_id);
                String sukses = "";
                if (etKeterangan != null) {
                    sukses = etKeterangan.getText().toString();
                }
                params.put("pengajuan_alasan_tolak", sukses);
                params.put("pengajuan_assigned_to_aksi", aksi);
                Log.d("asf", params.toString());
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private void Dialog() {
        final Dialog dialog = new Dialog(MenuActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_alasan_menolak, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        btnSubmit = (Button) view.findViewById(R.id.btSubmit);
        etKeterangan = (EditText) view.findViewById(R.id.etKet);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action("tolak", dialog);

            }
        });


        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getImagesAll();
        txtUser.setText(username);
        CheckGpsStatus();
        if (GpsStatus == true) {
            vused();
            Log.d("gantian", "1");
        } else {
            Dialog();
            Toast.makeText(getApplicationContext(), "GPS IS NON ACTIVE", Toast.LENGTH_SHORT).show();
        }
    }

    private void notifDialog() {
        final Dialog dialog = new Dialog(MenuActivity.this);
        LayoutInflater inflater = (LayoutInflater) MenuActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_notification, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        acc = (Button) view.findViewById(R.id.btnYa);
        noacc = (Button) view.findViewById(R.id.btnTidak);
        Etnama = view.findViewById(R.id.etNama);
        Etalamat = view.findViewById(R.id.etAlamat);

        Etnama.setText(nama);
        Etalamat.setText(alamat);
        acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action("setuju", null);
                dialog.dismiss();
            }
        });

        noacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.show();
    }


}
