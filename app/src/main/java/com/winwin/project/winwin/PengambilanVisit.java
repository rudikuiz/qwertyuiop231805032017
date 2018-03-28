package com.winwin.project.winwin;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
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

import static com.winwin.project.winwin.Config.RequestDatabase.URL_POST_VISIT;
import static com.winwin.project.winwin.Config.http.TAG_MEMBER_ID_KARYAWAN;

public class PengambilanVisit extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.ImgTTD)
    ImageView ImgTTD;
    @BindView(R.id.ImgKantor)
    ImageView ImgKantor;
    @BindView(R.id.btTakeKantor)
    Button btTakeKantor;
    @BindView(R.id.ImgPelanggan)
    ImageView ImgPelanggan;
    @BindView(R.id.btTakePelanggan)
    Button btTakePelanggan;
    @BindView(R.id.ImgRumah)
    ImageView ImgRumah;
    @BindView(R.id.btTakeRumah)
    Button btTakeRumah;
    @BindView(R.id.btSubmit)
    Button btSubmit;
    int PICK_IMAGE_REQUEST = 1;
    int PICK_IMAGE_REQUEST2 = 2;
    int PICK_IMAGE_REQUEST3 = 3;
    int PICK_IMAGE_REQUEST4 = 4;
    int bitmap_size = 100;
    Bitmap decoded, decoded2, decoded3, decoded4;
    @BindView(R.id.etLat)
    TextView etLat;
    @BindView(R.id.etLang)
    TextView etLang;
    @BindView(R.id.pupup)
    ImageView pupup;
    OwnProgressDialog loading;
    int success;
    private static final String TAG_SUCCESS = "success";
    RequestQueue requestQueue;
    @BindView(R.id.etTulisCatatan)
    EditText etTulisCatatan;
    @BindView(R.id.rbSetuju)
    RadioButton rbSetuju;
    @BindView(R.id.rbTolak)
    RadioButton rbTolak;
    @BindView(R.id.rgConfirmation)
    RadioGroup rgConfirmation;
    @BindView(R.id.ImgAsset)
    ImageView ImgAsset;
    @BindView(R.id.btTakeAsset)
    Button btTakeAsset;
    private FusedLocationProviderClient mFusedLocationClient;
    int PROS_ID = 1001;
    SharedPreferences sharedpreferences;
    String member_id, client_id, pengajuan_id, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengambilan_visit);
        ButterKnife.bind(this);
        loading = new OwnProgressDialog(PengambilanVisit.this);
        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        member_id = sharedpreferences.getString(TAG_MEMBER_ID_KARYAWAN, "");
        Intent intent = getIntent();
        client_id = intent.getStringExtra("id_client");
        pengajuan_id = intent.getStringExtra("pengajuan_id");
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestQueue = Volley.newRequestQueue(PengambilanVisit.this);
        vused();
    }

    private void addvisit() {
        loading.show();
        StringRequest strReq = new StringRequest(Request.Method.POST, URL_POST_VISIT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("notes: ", response);
                Toast.makeText(PengambilanVisit.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                loading.dismiss();
                try {
                    final JSONObject jObj = new JSONObject(response.toString());
                    success = jObj.getInt(TAG_SUCCESS);
                    Log.d("isi tag", TAG_SUCCESS);
                    // Check for error node in json
                    if (response.equals("Berhasil")) {
                        Toast.makeText(PengambilanVisit.this, "Berhasil Di Input Ke database", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PengambilanVisit.this,
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
                params.put("id_pengajuan", pengajuan_id);
                params.put("id_karyawan", member_id);
                params.put("foto_ttd", getStringImage(decoded));
                params.put("foto_kantor", getStringImage(decoded));
                params.put("foto_client", getStringImage(decoded2));
                params.put("foto_rumah", getStringImage(decoded3));
                params.put("foto_aset", getStringImage(decoded4));
                params.put("keterangan", etTulisCatatan.getText().toString());
                params.put("status", status);
                return params;
            }

        };
        requestQueue.add(strReq);
    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.btTakeKantor,
            R.id.btTakePelanggan, R.id.btTakeRumah, R.id.btSubmit,
            R.id.pupup, R.id.btTakeAsset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(PengambilanVisit.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btTakeKantor:
                takeImageFromCamera(view);
                break;
            case R.id.btTakePelanggan:
                takeImageFromCamera2(view);
                break;
            case R.id.btTakeRumah:
                takeImageFromCamera3(view);
                break;
            case R.id.btSubmit:
                int select;

                select = rgConfirmation.getCheckedRadioButtonId();
                if (select == rbSetuju.getId()) {
                    status = "1";
                }

                if (select == rbTolak.getId()) {
                    status = "0";
                }
                Log.d("status", status);
                addvisit();
                break;
            case R.id.pupup:
                notifDialog();
                break;
            case R.id.btTakeAsset:
                takeImageFromCamera4(view);
                break;

        }
    }

    private void notifDialog() {
        final Dialog dialog = new Dialog(PengambilanVisit.this);
        LayoutInflater inflater = (LayoutInflater) PengambilanVisit.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.pop_up_tanda_tangan, null);
        final DrawingView drawingView = view.findViewById(R.id.drawing_view);
        Button btnsave = (Button) view.findViewById(R.id.btnsave);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap drawingBitmap = drawingView.getDrawing();
                ImgTTD.setImageBitmap(drawingBitmap);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgKantor.setImageBitmap(decoded);
    }

    private void setToImageView2(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded2 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgPelanggan.setImageBitmap(decoded2);
    }

    private void setToImageView3(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded3 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgRumah.setImageBitmap(decoded3);
    }

    private void setToImageView4(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded4 = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));
        ImgAsset.setImageBitmap(decoded4);
    }

    public void takeImageFromCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST);
    }

    public void takeImageFromCamera2(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST2);
    }

    public void takeImageFromCamera3(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST3);
    }

    public void takeImageFromCamera4(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, PICK_IMAGE_REQUEST4);
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (btTakeKantor.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView(bitmap);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK) {
            if (btTakeRumah.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView2(bitmap);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST3 && resultCode == RESULT_OK) {
            if (btTakePelanggan.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView3(bitmap);
            }
        }

        if (requestCode == PICK_IMAGE_REQUEST4 && resultCode == RESULT_OK) {
            if (btTakeAsset.isClickable()) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                setToImageView4(bitmap);
            }
        }

    }

    private void vused() {
        ActivityCompat.requestPermissions(PengambilanVisit.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PROS_ID);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(PengambilanVisit.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(PengambilanVisit.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed; request the permission


                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
            }
            return;
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object


                            double lat = location.getLatitude();
                            double lon = location.getLongitude();

                            etLang.setText(String.valueOf(lat));
                            etLang.setText(String.valueOf(lon));

                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PROS_ID) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // DO NOTHING
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // Logic to handle location object


                                    double lat = location.getLatitude();
                                    double lon = location.getLongitude();

                                    etLat.setText(String.valueOf(lat));
                                    etLang.setText(String.valueOf(lon));

                                }
                            }
                        });
            } else {
                finish();
            }

        }
    }

}
