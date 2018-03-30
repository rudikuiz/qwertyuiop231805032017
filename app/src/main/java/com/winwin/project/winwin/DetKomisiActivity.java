package com.winwin.project.winwin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterTableKomisi;
import com.winwin.project.winwin.Model.ModelTableKomisi;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_KOMISI_TAHAP;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DetKomisiActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvDetailKomisi)
    RecyclerView rvDetailKomisi;
    @BindView(R.id.txtKomisi)
    TextView txtKomisi;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    SharedPreferences sharedpreferences;
    String member_id;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    AdapterTableKomisi tableKomisi;
    ArrayList<ModelTableKomisi> arrayList = new ArrayList<>();
    @BindView(R.id.ic_home)
    ImageView icHome;
    OwnProgressDialog loading;
    @BindView(R.id.totalOperasiional)
    TextView totalOperasiional;
    @BindView(R.id.totalKomisi)
    TextView totalKomisi;
    String title, values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_komisi);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        title = intent.getStringExtra("tahapan");
        values = intent.getStringExtra("values");
        txtKomisi.setText(title);

        rvDetailKomisi.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(DetKomisiActivity.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvDetailKomisi.setLayoutManager(layoutManager);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        requestQueue = Volley.newRequestQueue(DetKomisiActivity.this);

        loading = new OwnProgressDialog(DetKomisiActivity.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSON();
            }
        });

        getJSON();
    }

    private void getJSON() {
        Log.d("asfaf", URL_GET_KOMISI_TAHAP + member_id + "&tahap=" + values);
        arrayList = new ArrayList<ModelTableKomisi>();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_KOMISI_TAHAP + member_id + "&tahap=" + values, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    int total = 0;
                    int totalKom = 0;

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelTableKomisi modelMenu = new ModelTableKomisi();
                        modelMenu.setId(json.getString("number"));
                        modelMenu.setNama(json.getString("cli_nama_lengkap"));
                        modelMenu.setBiayaope(json.getString("prosen_operasional"));
                        modelMenu.setKomisi(json.getString("prosen_komisi"));
                        arrayList.add(modelMenu);
                        total = total + Integer.parseInt(modelMenu.getBiayaope());
                        totalKom = totalKom + Integer.parseInt(modelMenu.getKomisi());
                        totalOperasiional.setText(String.valueOf(total));
                        totalKomisi.setText(String.valueOf(totalKom));
                    }

                    AdapterTableKomisi adapter = new AdapterTableKomisi(arrayList, DetKomisiActivity.this);
                    rvDetailKomisi.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        });
        requestQueue.add(stringRequest);
    }

    @OnClick({R.id.ic_home, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(DetKomisiActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
