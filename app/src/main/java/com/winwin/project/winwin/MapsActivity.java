package com.winwin.project.winwin;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.namaklien)
    TextView namaklien;
    @BindView(R.id.jumlahUang)
    TextView jumlahUang;
    @BindView(R.id.btnOK)
    Button btnOK;
    @BindView(R.id.namaklien2)
    TextView namaklien2;
    @BindView(R.id.se)
    LinearLayout se;
    @BindView(R.id.se7)
    LinearLayout se7;
    @BindView(R.id.sebesar)
    TextView sebesar;

    private GoogleMap mMap;
    String nama, jumlah, lat, lang, client_get_id;
    LocationManager locationManager;
    double getLat, getLang;
    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        nama = intent.getStringExtra("nama");
        jumlah = intent.getStringExtra("jumlah");
        lat = intent.getStringExtra("lat");
        lang = intent.getStringExtra("lang");
        client_get_id = intent.getStringExtra("client_id");
        namaklien.setText(nama);
        namaklien2.setText(nama);
        jumlahUang.setText(jumlah);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
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

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    getLat = location.getLatitude();
                    getLang = location.getLongitude();

                    latLng = new LatLng(getLat, getLang);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(getLat, getLang, 1);
                        String str = addresses.get(0).getLocality() + ",";
                        str += addresses.get(0).getCountryName();

                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    getLat = location.getLatitude();
                    getLang = location.getLongitude();

                    latLng = new LatLng(getLat, getLang);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(getLat, getLang, 1);
                        String str = addresses.get(0).getLocality() + ",";
                        str += addresses.get(0).getCountryName();

                        mMap.addMarker(new MarkerOptions().position(latLng).title(str));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        LatLng lokasi = new LatLng(-7.373265, 112.6496639);
        LatLng client = new LatLng(Double.parseDouble(lat), Double.parseDouble(lang));

        float zoom = 17.5f;
//        mMap.addMarker(new MarkerOptions().position(latLng).title("DEBT"));

        mMap.addMarker(new MarkerOptions().position(client).title("CLIENT"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(client, zoom));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(client));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @OnClick(R.id.btnOK)
    public void onViewClicked() {

        if (btnOK.getText().equals("Selesai")) {
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MapsActivity.this, DataKunjunganActivity.class);
                    intent.putExtra("id_client", client_get_id);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            DialogForm();
        }
    }

    TextView textPertama, textKedua, textKetiga;

    private void DialogForm() {
        final Dialog dialog = new Dialog(MapsActivity.this);
        LayoutInflater inflater = (LayoutInflater) MapsActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_konfirmasi_tujuan, null);

        textPertama = view.findViewById(R.id.textPertama);
        textKedua = view.findViewById(R.id.text2);
        textKetiga = view.findViewById(R.id.text4);
        Button Setuju = view.findViewById(R.id.btnSetuju);
        ImageView imageView = view.findViewById(R.id.ic_close);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        textPertama.setText(getString(R.string.textPertama, nama, jumlah));
        textKedua.setText(getString(R.string.textKedua, nama, 0, 0));
        textKetiga.setText(getString(R.string.textKetiga, nama));
        Setuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textPertama.setText(getString(R.string.textSukses, nama));
//                textKedua.setText("");
//                textKetiga.setText("");
//                Intent intent = new Intent(MapsActivity.this, DataKunjunganActivity.class);
//                startActivity(intent);
                dialog.cancel();
                se.setVisibility(LinearLayout.GONE);
                se7.setVisibility(LinearLayout.GONE);
                sebesar.setText("");
                jumlahUang.setText(getString(R.string.textSukses, nama));
                btnOK.setText("Selesai");
            }
        });
        String pekok = getString(R.string.textPertama, nama, jumlah);
        Log.d("textpertama", pekok);

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.corner_radius_bg_pop_up);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);

        dialog.show();
    }
}
