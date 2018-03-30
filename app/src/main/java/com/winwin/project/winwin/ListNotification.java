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
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterListNotification;
import com.winwin.project.winwin.Model.ListNotifModel;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_COUNT_NOTIF;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ALL;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class ListNotification extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    private ArrayList<ListNotifModel> list = new ArrayList<>();
    AdapterListNotification adapterListNotification;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;
    SharedPreferences sharedpreferences;
    String member_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        GridLayoutManager layoutManager = new GridLayoutManager(ListNotification.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManager);
        loading = new OwnProgressDialog(ListNotification.this);
        requestQueue = Volley.newRequestQueue(ListNotification.this);
        Swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                getJSON();
            }
        });
        getJSON();
    }

    private void getJSON() {

        stringRequest = new StringRequest(Request.Method.GET, URL_COUNT_NOTIF + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ListNotifModel modelMenu = new ListNotifModel();
                        modelMenu.setNama(json.getString("cli_nama_lengkap"));
                        modelMenu.setAlamat(json.getString("cli_alamat"));
                        modelMenu.setPengajuan_id(json.getString("pengajuan_id"));
                        list.add(modelMenu);
                    }

                    adapterListNotification = new AdapterListNotification(list, ListNotification.this);
                    rvNotification.setAdapter(adapterListNotification);
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
                    Toast.makeText(ListNotification.this, "timeout", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(ListNotification.this, "no connection", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ListNotification.this, MenuActivity.class);
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
