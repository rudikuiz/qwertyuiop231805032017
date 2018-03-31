package com.winwin.project.winwin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterAnalistdebt;
import com.winwin.project.winwin.Adapter.AdapterMenu;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ANALIS;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class AnalistDebt extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvAnalistDebt)
    RecyclerView rvAnalistDebt;
    ArrayList<ModelMenu> listData = new ArrayList<>();
    AdapterAnalistdebt analistdebt;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;
    SharedPreferences sharedpreferences;
    String member_id;
    ArrayList<ModelMenu> mExampleList;
    @BindView(R.id.etCari)
    EditText etCari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analist_debt);
        ButterKnife.bind(this);
        rvAnalistDebt.setHasFixedSize(true);

        mExampleList = new ArrayList<ModelMenu>();
        listData = new ArrayList<ModelMenu>();

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        GridLayoutManager layoutManager = new GridLayoutManager(AnalistDebt.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvAnalistDebt.setLayoutManager(layoutManager);


        requestQueue = Volley.newRequestQueue(AnalistDebt.this);

        loading = new OwnProgressDialog(AnalistDebt.this);

        loading.show();
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJSON();
            }
        });
        getJSON();

        etCari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() == 0) {
                    AdapterAnalistdebt adapter = new AdapterAnalistdebt(listData, AnalistDebt.this);
                    rvAnalistDebt.setAdapter(adapter);
                } else {
                    filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getJSON() {


        stringRequest = new StringRequest(Request.Method.GET, URL_GET_ANALIS + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelMenu modelMenu = new ModelMenu();
                        modelMenu.getCli_id();
                        modelMenu.setCli_id(json.getString("cli_id"));
                        modelMenu.setCli_nama(json.getString("cli_nama_lengkap"));
                        modelMenu.setPengajuan_id(json.getString("task_id_pengajuan"));
                        listData.add(modelMenu);
                    }

                    AdapterAnalistdebt adapter = new AdapterAnalistdebt(listData, AnalistDebt.this);
                    rvAnalistDebt.setAdapter(adapter);
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
                    Toast.makeText(AnalistDebt.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(AnalistDebt.this, "no connection", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AnalistDebt.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
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
        AdapterAnalistdebt adapter = new AdapterAnalistdebt(filteredList, AnalistDebt.this);
        rvAnalistDebt.setAdapter(adapter);

    }
}
