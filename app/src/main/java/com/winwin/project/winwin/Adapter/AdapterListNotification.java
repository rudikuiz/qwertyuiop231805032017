package com.winwin.project.winwin.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.winwin.project.winwin.LoginPage;
import com.winwin.project.winwin.Model.ListNotifModel;
import com.winwin.project.winwin.R;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_ADDTUGAS;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

/**
 */

public class AdapterListNotification extends RecyclerView.Adapter<AdapterListNotification.ListHolder> {


    private ArrayList<ListNotifModel> list;
    private Context context;
    OwnProgressDialog loading;
    int success;
    private static final String TAG_SUCCESS = "success";
    RequestQueue requestQueue;
    String member_id_kar;
    SharedPreferences sharedpreferences;
    Button btnSubmit;
    EditText etKeterangan;

    public AdapterListNotification(ArrayList<ListNotifModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_notification, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, final int position) {
        int number = position + 1;
        holder.no.setText(String.valueOf(number));
        holder.nama.setText(list.get(position).getNama());
        holder.alamat.setText(list.get(position).getAlamat());
        holder.btYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action(position, "setuju", null);
                Toast.makeText(context, "Transaksi Disetujui", Toast.LENGTH_SHORT).show();

            }
        });

        holder.btTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Transaksi Ditolak", Toast.LENGTH_SHORT).show();
                Dialog(position);
            }
        });
    }

    private void Dialog(final int pos) {
        final Dialog dialog = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_alasan_menolak, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        btnSubmit = (Button) view.findViewById(R.id.btSubmit);
        etKeterangan = (EditText) view.findViewById(R.id.etKet);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action(pos, "tolak", dialog);

            }
        });


        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void Action(final int pos, final String aksi, final Dialog dialog) {
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_ADDTUGAS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(context, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(context, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context,
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
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();

                loading.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                ListNotifModel model = list.get(pos);
                Map<String, String> params = new HashMap<String, String>();
                params.put("pengajuan_id", model.getPengajuan_id());
                params.put("pengajuan_assigned_to", member_id_kar);
                String sukses = "";
                if (etKeterangan != null){
                    sukses = etKeterangan.getText().toString();
                }
                params.put("pengajuan_alasan_tolak", sukses);
                params.put("pengajuan_assigned_to_aksi", aksi);
                Log.d("sdaf", params.toString());
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.no)
        TextView no;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.alamat)
        TextView alamat;
        @BindView(R.id.btYa)
        Button btYa;
        @BindView(R.id.btTidak)
        Button btTidak;


        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            loading = new OwnProgressDialog(context);
            requestQueue = Volley.newRequestQueue(context);
            sharedpreferences = context.getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
            member_id_kar = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        }
    }
}
