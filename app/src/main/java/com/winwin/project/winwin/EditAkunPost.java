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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.winwin.project.winwin.Model.ModelProfil;
import com.winwin.project.winwin.Model.ModelUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.winwin.project.winwin.Config.RequestDatabase.URL_GET_IMAGE_FROM_FOLDER;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_PROFIL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_UPDATE_PROFIL_TRIAL;
import static com.winwin.project.winwin.Config.RequestDatabase.URL_VIEW;
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
    String id_kar;
    String namad, notelp, alamatemail, passwords, pathktp, pathselfi, noreks, bank, cabangs, ans, pathpathrek;
    SharedPreferences sharedpreferences;
    int bitmap_size = 100, TAKE_IMAGE = 1, TAKE_IMAGE2 = 2, TAKE_IMAGE3 = 3;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    private String KEY_IMAGE = "image";
    Bitmap decoded, decoded2, decoded3;
    ArrayList<ModelUrl> list_data;
    StringRequest stringRequest;
    String urlKtp, urlSelfi, urlRek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun_post);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        id_kar = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Log.d("idalex", id_kar);

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
        getImagesAll();
        load();
    }


    private void getImagesAll() {
        list_data = new ArrayList<ModelUrl>();

        stringRequest = new StringRequest(Request.Method.GET, URL_VIEW + id_kar, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("responese url", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_ktp"));
                        list_data.add(model);
                        urlKtp = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("ktp", urlKtp);
                        if (model.getLabel() != null && !model.getLabel().equals("null")) {
                            Glide.with(EditAkunPost.this).load(urlKtp)
                                    .crossFade()
                                    .placeholder(R.drawable.lodingimages)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ImgKtp);
                        } else {
                            ImgKtp.setImageResource(R.drawable.noimage);
                        }

                    }

                    JSONArray jsonArray2 = new JSONArray(response);
                    for (int a = 0; a < jsonArray2.length(); a++) {
                        JSONObject json = jsonArray2.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_selfi"));
                        list_data.add(model);
                        urlSelfi = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("pap", urlSelfi);
                        if (urlSelfi != null) {
                            Glide.with(EditAkunPost.this).load(urlSelfi)
                                    .crossFade()
                                    .placeholder(R.drawable.lodingimages)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(imageView);

                        } else {
                            imageView.setImageResource(R.drawable.noimage);
                        }

                        if (urlSelfi != null) {
                            Glide.with(EditAkunPost.this).load(urlSelfi)
                                    .crossFade()
                                    .placeholder(R.drawable.lodingimages)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ImgSelfi);


                        } else {
                            ImgSelfi.setImageResource(R.drawable.noimage);
                        }
                    }

                    JSONArray jsonArray3 = new JSONArray(response);
                    for (int a = 0; a < jsonArray3.length(); a++) {
                        JSONObject json = jsonArray3.getJSONObject(a);
                        ModelUrl model = new ModelUrl();
                        model.setLabel(json.getString("kar_foto_buku_rek"));
                        list_data.add(model);
                        urlRek = URL_GET_IMAGE_FROM_FOLDER + model.getLabel();

                        Log.d("rek", urlRek);
                        if (urlRek != null) {
                            Glide.with(EditAkunPost.this).load(urlRek)
                                    .crossFade()
                                    .placeholder(R.drawable.lodingimages)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ImgRek);

                        } else {
                            ImgRek.setImageResource(R.drawable.noimage);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
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

    private void load() {
        stringRequest = new StringRequest(Request.Method.GET, URL_POST_PROFIL + id_kar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response_profile", response);
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        ModelProfil dataClient = new ModelProfil();
                        dataClient.setNamadepan(json.getString("kar_namalengkap"));
                        dataClient.setNotelp(json.getString("kar_no_telepon"));
                        dataClient.setEmail(json.getString("kar_email_winwin"));
                        dataClient.setNorek(json.getString("kar_no_rek"));
                        dataClient.setBank(json.getString("kar_nama_bank"));
                        dataClient.setCabang(json.getString("kar_cabang"));
                        dataClient.setAtasnama(json.getString("kar_an"));

                        namabelakang.setText(dataClient.getNamadepan());
                        notelppon.setText(dataClient.getNotelp());
                        email.setText(dataClient.getEmail());
                        password.setText("");
                        norek.setText(dataClient.getNorek());
                        namabank.setText(dataClient.getBank());
                        cabang.setText(dataClient.getCabang());
                        an.setText(dataClient.getAtasnama());

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
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
        Intent intent = new Intent("OKOK");
        intent.putExtra("asd","asd");
        finish();
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
                if (decoded != null) {
                    params.put("kar_foto_ktp", getStringImage(decoded));
                }

                if (decoded2 != null) {
                    params.put("kar_foto_selfi", getStringImage(decoded2));
                }

                params.put("kar_no_rek", norek.getText().toString());
                params.put("kar_nama_bank", namabank.getText().toString());
                params.put("kar_cabang", cabang.getText().toString());
                params.put("kar_an", an.getText().toString());

                if (decoded3 != null) {
                    params.put("kar_foto_rek", getStringImage(decoded3));
                }

                params.put("kar_id", id_kar);

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
