package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterHistory;
import com.winwin.project.winwin.Model.ModelHistory;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_HISTORY;

public class HistoryPembayaran extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;

    RequestQueue requestQueue;
    StringRequest stringRequest;
    ArrayList<ModelHistory> listData;
    OwnProgressDialog loading;
    String client_id;
    @BindView(R.id.ic_home)
    ImageView icHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pembayaran);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        client_id = bundle.getString("id");
        Log.d("id_cli_history", client_id);

        rvHistory.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(HistoryPembayaran.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(HistoryPembayaran.this);

        loading = new OwnProgressDialog(HistoryPembayaran.this);

        loading.show();

        getJSON();
    }

    private void getJSON() {

        listData = new ArrayList<ModelHistory>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_HISTORY + client_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelHistory modelMenu = new ModelHistory();
                        modelMenu.setWaktu(json.getString("pemb_tanggal"));
                        modelMenu.setPembayaran(json.getString("pemb_waktu"));
                        modelMenu.setVia(json.getString("pemb_nominal"));
                        modelMenu.setCatatan(json.getString("mediabayar_label"));
                        listData.add(modelMenu);
                    }

                    AdapterHistory adapter = new AdapterHistory(listData, HistoryPembayaran.this);
                    rvHistory.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loading.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        });
        requestQueue.add(stringRequest);
    }

    @OnClick({R.id.ic_home, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(HistoryPembayaran.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
