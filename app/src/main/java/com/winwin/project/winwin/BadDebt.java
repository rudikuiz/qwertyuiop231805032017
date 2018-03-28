package com.winwin.project.winwin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stephenvinouze.drawingview.DrawingView;
import com.winwin.project.winwin.Setting.OwnProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_BADDEBT;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;


public class BadDebt extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.spinCol)
    Spinner spinCol;
    @BindView(R.id.etKet)
    TextView etKet;
    @BindView(R.id.ImgTTD)
    ImageView ImgTTD;
    @BindView(R.id.pupup)
    ImageView pupup;
    OwnProgressDialog loading;
    int success;
    private static final String TAG_SUCCESS = "success";
    RequestQueue requestQueue;
    @BindView(R.id.ImgDebt)
    ImageView ImgDebt;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    @BindView(R.id.ImgKamera)
    ImageView ImgKamera;
    String values;
    int PICK_IMAGE_REQUEST = 1;
    Bitmap decoded,decoded2;
    int bitmap_size = 100;
    String pengajuanid, member_id;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bad_debt);
        ButterKnife.bind(this);
        loading = new OwnProgressDialog(BadDebt.this);
        requestQueue = Volley.newRequestQueue(BadDebt.this);
        spinCol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                values = spinCol.getSelectedItem().toString();
                if (values.equals("Meninggal")) {
                    etKet.setText("Foto Keterangan Kematian");
                }

                if (values.equals("Hilang")) {
                    etKet.setText("Foto Keterangan Hilang");
                }

                if (values.equals("Tidak Sanggup Bayar")) {
                    etKet.setText("Foto Keterangan Tidak Sanggup Bayar");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");

        Intent intent = getIntent();
        pengajuanid = intent.getStringExtra("pengajuan_id");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (ImgKamera.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView(bitmap);
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgDebt.setImageBitmap(decoded);
    }

    private void setToImageView2(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded2 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgTTD.setImageBitmap(decoded2);
    }

    public void takeImageFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.pupup, R.id.btSubmit, R.id.ImgKamera})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(BadDebt.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.pupup:
                popShow();
                break;
            case R.id.btSubmit:
                addBaddebt();
                break;
            case R.id.ImgKamera:
                takeImageFromCamera();
                break;
        }
    }

    private void addBaddebt() {
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_BADDEBT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(BadDebt.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                loading.dismiss();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(BadDebt.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(BadDebt.this,
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
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                loading.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("keterangan", etKet.getText().toString());
                params.put("foto_ttd", getStringImage(decoded));
                params.put("foto_keterangan", getStringImage(decoded2));
                params.put("id_pengajuan",pengajuanid);
                params.put("id_karyawan", member_id);
                Log.d("sdaf", params.toString());
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private void popShow() {
        final Dialog dialog = new Dialog(BadDebt.this);
        LayoutInflater inflater = (LayoutInflater) BadDebt.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_tanda_tangan, null);
        final DrawingView drawingView = view.findViewById(R.id.drawing_view);
        Button btnsave = (Button) view.findViewById(R.id.btnsave);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap drawingBitmap = drawingView.getDrawing();
//                ImgTTD.setImageBitmap(drawingBitmap);
                setToImageView2(drawingBitmap);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
