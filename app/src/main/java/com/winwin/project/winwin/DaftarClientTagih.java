package com.winwin.project.winwin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.winwin.project.winwin.Adapter.AdapterMenu;
import com.winwin.project.winwin.Adapter.SearchAdapter;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.Setting.OwnProgressDialog;
import com.winwin.project.winwin.Utils.AppConf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ALL;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class DaftarClientTagih extends AppCompatActivity {

    @BindView(R.id.ic_logo_client)
    ImageView icLogoClient;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.no)
    TextView no;
    @BindView(R.id.rvDaftarClient)
    RecyclerView rvDaftarClient;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<ModelMenu> listData;
    OwnProgressDialog loading;
    @BindView(R.id.ic_home)
    ImageView icHome;
    SharedPreferences sharedpreferences;
    String member_id;
    @BindView(R.id.etCari)
    EditText etCari;
    SearchAdapter mAdapter;
    ArrayList<ModelMenu> mExampleList;
    @BindView(R.id.etCarikota)
    EditText etCarikota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_client_tagih);
        ButterKnife.bind(this);
        rvDaftarClient.setHasFixedSize(true);


        mExampleList = new ArrayList<ModelMenu>();
        listData = new ArrayList<ModelMenu>();

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1,
                GridLayoutManager.VERTICAL, false);
        rvDaftarClient.setLayoutManager(layoutManager);

        requestQueue = Volley.newRequestQueue(DaftarClientTagih.this);

        loading = new OwnProgressDialog(DaftarClientTagih.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listData.clear();
                mExampleList.clear();
                getJSON();
            }
        });
        getJSON();

//        mAdapter = new SearchAdapter(mExampleList, DaftarClientTagih.this);

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    AdapterMenu adapter = new AdapterMenu(listData, DaftarClientTagih.this);
                    rvDaftarClient.setAdapter(adapter);
                } else {
                    filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etCarikota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    AdapterMenu adapter = new AdapterMenu(listData, DaftarClientTagih.this);
                    rvDaftarClient.setAdapter(adapter);
                } else {
                    filterK(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getJSON() {
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_ALL + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelMenu modelMenu = new ModelMenu();
                        modelMenu.setCli_id(json.getString("cli_id"));
                        modelMenu.setCli_nama(json.getString("cli_nama_lengkap"));
                        modelMenu.setPeng_janji(json.getString("pengajuan_janji_bayar_aktif"));
                        modelMenu.setPeng_cicilan(json.getString("pengajuan_cicilan_aktif"));
                        modelMenu.setPeng_lunas(json.getString("pengajuan_stat_lunas"));
                        modelMenu.setAlamat(json.getString("cli_alamat"));
                        modelMenu.setKecamatan(json.getString("cli_kecamatan"));
                        modelMenu.setKota(json.getString("cli_kota"));
                        listData.add(modelMenu);
                    }

                    AdapterMenu adapter = new AdapterMenu(listData, DaftarClientTagih.this);
                    rvDaftarClient.setAdapter(adapter);
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
                if (error instanceof TimeoutError) {
                    Toast.makeText(DaftarClientTagih.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(DaftarClientTagih.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                loading.dismiss();
                if (Swipe != null) {
                    Swipe.setRefreshing(false);
                }
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                AppConf.SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }

    private void filter(String text) {
        ArrayList<ModelMenu> filteredList = new ArrayList<>();
        for (ModelMenu item : listData) {
            if (item.getCli_nama().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mExampleList.clear();
        mExampleList.addAll(filteredList);
        AdapterMenu adapter = new AdapterMenu(filteredList, DaftarClientTagih.this);
        rvDaftarClient.setAdapter(adapter);

    }

    private void filterK(String text) {
        ArrayList<ModelMenu> filteredList = new ArrayList<>();
        for (ModelMenu item : listData) {
            if (item.getKota().toLowerCase().contains(text.toLowerCase()) ||
                    item.getKecamatan().toLowerCase().contains(text.toLowerCase()) ||
                    item.getAlamat().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mExampleList.clear();
        mExampleList.addAll(filteredList);
        AdapterMenu adapter = new AdapterMenu(filteredList, DaftarClientTagih.this);
        rvDaftarClient.setAdapter(adapter);

    }

    @OnClick({R.id.ic_home, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
