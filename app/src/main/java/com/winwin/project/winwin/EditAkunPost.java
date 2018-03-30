package com.winwin.project.winwin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_UPDATE_PROFIL_TRIAL;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class EditAkunPost extends AppCompatActivity {

    String getNama, getPhone, getEmail, getPass, getnorek, getNamaBank, getCabang, getAn, getUser;
    public static final int REQUEST_CODE_CAMERA1 = 0012;
    public static final int REQUEST_CODE_CAMERA2 = 0013;
    public static final int REQUEST_CODE_CAMERA3 = 0014;

    public static final int REQUEST_CODE_GALLERY1 = 0015;
    public static final int REQUEST_CODE_GALLERY2 = 0016;
    public static final int REQUEST_CODE_GALLERY3 = 0017;
    Uri imageUri;
    ImageView mimageView;
    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.txtNamaClient)
    TextView txtNamaClient;
    @BindView(R.id.karyawan_id)
    TextView karyawanId;
    @BindView(R.id.namabelakang)
    EditText namabelakang;
    @BindView(R.id.notelppon)
    EditText notelppon;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.viewPhotoktp)
    TextView viewPhotoktp;
    @BindView(R.id.uploadPhotoktp)
    TextView uploadPhotoktp;
    @BindView(R.id.takephotoktp)
    TextView takephotoktp;
    @BindView(R.id.ImgKtp)
    ImageView ImgKtp;
    @BindView(R.id.uploadPhotoSelfi)
    TextView uploadPhotoSelfi;
    @BindView(R.id.takephotoselfi)
    TextView takephotoselfi;
    @BindView(R.id.viewPhotoSelfi)
    TextView viewPhotoSelfi;
    @BindView(R.id.ImgSelfi)
    ImageView ImgSelfi;
    @BindView(R.id.norek)
    EditText norek;
    @BindView(R.id.namabank)
    EditText namabank;
    @BindView(R.id.cabang)
    EditText cabang;
    @BindView(R.id.an)
    EditText an;
    @BindView(R.id.uploadPhotorek)
    TextView uploadPhotorek;
    @BindView(R.id.takephotorek)
    TextView takephotorek;
    @BindView(R.id.viewPhotorek)
    TextView viewPhotorek;
    @BindView(R.id.ImgRek)
    ImageView ImgRek;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    RequestQueue requestQueue;
    String tag_json_obj = "json_obj_req";
    int success;
    @BindView(R.id.ImgNameSelfi)
    TextView ImgNameSelfi;
    @BindView(R.id.ImgNameRek)
    TextView ImgNameRek;
    @BindView(R.id.ImgNameKtp)
    TextView ImgNameKtp;
    ConnectivityManager conMgr;
    String client_id, pathImages;
    String namad, notelp, alamatemail, passwords, pathktp, pathselfi, noreks, bank, cabangs, ans, pathpathrek;
    SharedPreferences sharedpreferences;
    String PathImages = "http://winwinujicobaadmin.tamboraagungmakmur.com/debt_collector/images/";
    int bitmap_size = 60, TAKE_IMAGE = 1, TAKE_IMAGE2 = 2, TAKE_IMAGE3 = 3;
    private String KEY_IMAGE = "image";
    Bitmap decoded, decoded2, decoded3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun_post);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        client_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Log.d("idalex", client_id);

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

        requestQueue = Volley.newRequestQueue(EditAkunPost.this);
        visible();

        getIntentDataFromAkun();
    }

    private void getIntentDataFromAkun() {
        Intent intent = getIntent();
        getUser = intent.getStringExtra("profile");
        getNama = intent.getStringExtra("name");
        getPhone = intent.getStringExtra("phone");
        getEmail = intent.getStringExtra("email");
        getPass = intent.getStringExtra("password");
        getnorek = intent.getStringExtra("no_rek");
        getNamaBank = intent.getStringExtra("bank_name");
        getCabang = intent.getStringExtra("cab");
        getAn = intent.getStringExtra("an");

        txtNamaClient.setText(getUser);
        namabelakang.setText(getNama);
        notelppon.setText(getPhone);
        email.setText(getEmail);
        password.setText(getPass);
        norek.setText(getnorek);
        namabank.setText(getNamaBank);
        cabang.setText(getCabang);
        an.setText(getAn);
        viewPhotoktp.setVisibility(View.GONE);
        viewPhotorek.setVisibility(View.GONE);
        viewPhotoSelfi.setVisibility(View.GONE);
    }

    private void visible() {
        btSubmit.setVisibility(View.VISIBLE);
        takephotorek.setVisibility(View.VISIBLE);
        takephotoselfi.setVisibility(View.VISIBLE);
        takephotoktp.setVisibility(View.VISIBLE);

        uploadPhotoSelfi.setVisibility(View.VISIBLE);
        uploadPhotorek.setVisibility(View.VISIBLE);
        uploadPhotoktp.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_IMAGE && resultCode == RESULT_OK) {
            if (takephotoktp.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView(bitmap);
            }
        }

        if (requestCode == TAKE_IMAGE2 && resultCode == RESULT_OK) {
            if (takephotoselfi.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageSelfi(bitmap);
            }
        }
        if (requestCode == TAKE_IMAGE3 && resultCode == RESULT_OK) {
            if (takephotorek.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageRek(bitmap);
            }
        }

    }

    public void takeImageKTP(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_IMAGE);
    }

    public void takeImageSelfi(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_IMAGE2);
    }

    public void takeImageRek(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_IMAGE3);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgKtp.setImageBitmap(decoded);
    }

    private void setToImageSelfi(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded2 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgSelfi.setImageBitmap(decoded2);
    }

    private void setToImageRek(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded3 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgRek.setImageBitmap(decoded3);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    @OnClick({R.id.ic_home, R.id.img_back, R.id.viewPhotoktp, R.id.uploadPhotoktp, R.id.takephotoktp, R.id.uploadPhotoSelfi, R.id.takephotoselfi, R.id.viewPhotoSelfi, R.id.uploadPhotorek, R.id.takephotorek, R.id.viewPhotorek, R.id.btSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.viewPhotoktp:
                break;
            case R.id.uploadPhotoktp:
                EasyImage.openGallery(EditAkunPost.this, REQUEST_CODE_GALLERY1);
                break;
            case R.id.takephotoktp:
//                EasyImage.openCamera(EditAkunPost.this, REQUEST_CODE_CAMERA1);
                takeImageKTP(view);
                break;
            case R.id.uploadPhotoSelfi:
                EasyImage.openGallery(EditAkunPost.this, REQUEST_CODE_GALLERY2);
                break;
            case R.id.takephotoselfi:
//                EasyImage.openCamera(EditAkunPost.this, REQUEST_CODE_CAMERA2);
                takeImageSelfi(view);
                break;
            case R.id.viewPhotoSelfi:
                break;
            case R.id.uploadPhotorek:
                EasyImage.openGallery(EditAkunPost.this, REQUEST_CODE_GALLERY3);
                break;
            case R.id.takephotorek:
//                EasyImage.openCamera(EditAkunPost.this, REQUEST_CODE_CAMERA3);
                takeImageRek(view);
                break;
            case R.id.viewPhotorek:
                break;
            case R.id.btSubmit:
                namad = txtNamaClient.getText().toString();
                notelp = notelppon.getText().toString();
                alamatemail = email.getText().toString();
                passwords = password.getText().toString();
                pathktp = ImgNameKtp.getText().toString();
                pathselfi = ImgNameSelfi.getText().toString();
                noreks = norek.getText().toString();
                bank = namabank.getText().toString();
                cabangs = cabang.getText().toString();
                ans = an.getText().toString();
                pathpathrek = ImgNameRek.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    UpdateData();

                } else {
                    Toast.makeText(EditAkunPost.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ic_home:
                Intent intent = new Intent(EditAkunPost.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void UpdateData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Loading ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_UPDATE_PROFIL_TRIAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(EditAkunPost.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();
                hideDialog();
                finish();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    hideDialog();
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("kar_namalengkap", txtNamaClient.getText().toString());
                params.put("kar_no_telepon", notelppon.getText().toString());
                params.put("kar_email_winwin", email.getText().toString());
                params.put("member_pass", password.getText().toString());
                params.put("kar_foto_ktp", getStringImage(decoded));
                params.put("kar_foto_selfi", getStringImage(decoded2));
                params.put("kar_no_rek", norek.getText().toString());
                params.put("kar_nama_bank", namabank.getText().toString());
                params.put("kar_cabang", cabang.getText().toString());
                params.put("kar_an", an.getText().toString());
                params.put("kar_foto_rek", getStringImage(decoded3));
                params.put("kar_id", client_id);

                return params;
            }

        };
        requestQueue.add(strReq);

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
