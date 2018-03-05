package com.winwin.project.winwin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.Adapter.AdapterCatatan;
import com.winwin.project.winwin.Config.AppController;
import com.winwin.project.winwin.Model.ModelCatatan;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_ADD_NOTES;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_CATATAN;

public class TulisCatatan extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rc_data_catatan)
    RecyclerView rcDataCatatan;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;
    @BindView(R.id.btnBatal)
    Button btnBatal;
    @BindView(R.id.txtTulisCatatan)
    EditText txtTulisCatatan;

    ArrayList<ModelCatatan> listCatatan = new ArrayList<>();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    OwnProgressDialog loading;

    String client_id;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    int success;
    private static final String TAG = TulisCatatan.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";
    @BindView(R.id.ic_home)
    ImageView icHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tulis_catatan);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        client_id = bundle.getString("id");
        Log.d("id_cli_catatan", client_id);

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

        rcDataCatatan.setHasFixedSize(true);

        GridLayoutManager gridCatatan = new GridLayoutManager(TulisCatatan.this, 1,
                GridLayoutManager.VERTICAL, false);
        rcDataCatatan.setLayoutManager(gridCatatan);
        requestQueue = Volley.newRequestQueue(TulisCatatan.this);

        loading = new OwnProgressDialog(TulisCatatan.this);

        loading.show();

        getJSON();
    }

    private void getJSON() {

        listCatatan = new ArrayList<ModelCatatan>();

        stringRequest = new StringRequest(Request.Method.GET, URL_GET_CATATAN + client_id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response_catatan: ", response);
                Log.d("catatan url ", URL_GET_CATATAN + client_id);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelCatatan modelCatatan = new ModelCatatan();
                        modelCatatan.setXxx(json.getString("ajunote_catatan"));
                        modelCatatan.setTxtjam(json.getString("ajunote_waktu"));
                        modelCatatan.setTxtTanggal(json.getString("ajunote_tanggal"));
                        listCatatan.add(modelCatatan);
                    }

                    AdapterCatatan adapter = new AdapterCatatan(TulisCatatan.this, listCatatan);
                    rcDataCatatan.setAdapter(adapter);
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

    @OnClick({R.id.btnSimpan, R.id.btnBatal, R.id.img_back, R.id.ic_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSimpan:
                String text = txtTulisCatatan.getText().toString();
                Log.d("data", text + client_id);
                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    addNotes(text, client_id);

                } else {
                    Toast.makeText(TulisCatatan.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnBatal:
                txtTulisCatatan.getText().clear();
                Toast.makeText(TulisCatatan.this, "Text Clear", Toast.LENGTH_SHORT).show();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.ic_home:
                Intent intent = new Intent(TulisCatatan.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void addNotes(final String text, final String id_client) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_ADD_NOTES, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(TulisCatatan.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                txtTulisCatatan.getText().clear();
                hideDialog();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(TulisCatatan.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                        txtTulisCatatan.getText().clear();

                    } else {
                        Toast.makeText(TulisCatatan.this,
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

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
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("ajunote_catatan", text);
                params.put("ajunote_id_client", id_client);

                return params;
            }

        };
        requestQueue.add(strReq);
        // Adding request to request queue
        AppController.getmInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
