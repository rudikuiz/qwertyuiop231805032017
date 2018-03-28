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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterHistoryPencairan;
import com.winwin.project.winwin.Adapter.AdapterMenu;
import com.winwin.project.winwin.Model.ModelHistoryaPencairan;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_ALL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_PENCAIRAN;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class HistoryPencairanActivity extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvHistoryPencairan)
    RecyclerView rvHistoryPencairan;
    RequestQueue requestQueue;
    StringRequest stringRequest;
    AdapterHistoryPencairan adapterHistoryPencairan;
    ArrayList<ModelHistoryaPencairan> arrayList = new ArrayList<>();
    @BindView(R.id.ic_home)
    ImageView icHome;
    OwnProgressDialog loading;
    @BindView(R.id.Swipe)
    SwipeRefreshLayout Swipe;
    SharedPreferences sharedpreferences;
    String member_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_pencairan);
        ButterKnife.bind(this);

        rvHistoryPencairan.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(HistoryPencairanActivity.this, 1,
                GridLayoutManager.VERTICAL, false);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        rvHistoryPencairan.setLayoutManager(layoutManager);
        requestQueue = Volley.newRequestQueue(HistoryPencairanActivity.this);

        loading = new OwnProgressDialog(HistoryPencairanActivity.this);

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

        arrayList = new ArrayList<ModelHistoryaPencairan>();
        stringRequest = new StringRequest(Request.Method.GET, URL_GET_PENCAIRAN + member_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response: ", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelHistoryaPencairan modelMenu = new ModelHistoryaPencairan();
                        modelMenu.setTanggal(json.getString("number"));
                        modelMenu.setDanamasuk(json.getString("cli_id"));
                        modelMenu.setKomisi(json.getString("cli_nama_lengkap"));
                        modelMenu.setStatus(json.getString("cli_nama_lengkap"));
                        arrayList.add(modelMenu);
                    }

                    AdapterHistoryPencairan adapter = new AdapterHistoryPencairan(arrayList, HistoryPencairanActivity.this);
                    rvHistoryPencairan.setAdapter(adapter);
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
                Intent intent = new Intent(HistoryPencairanActivity.this, MenuActivity.class);
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
