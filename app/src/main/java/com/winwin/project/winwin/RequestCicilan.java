package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.winwin.project.winwin.Setting.DecimalsFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestCicilan extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.etHutangPokok)
    TextView etHutangPokok;
    @BindView(R.id.etBunga)
    TextView etBunga;
    @BindView(R.id.etDenda)
    TextView etDenda;
    @BindView(R.id.etTotalPembayaran)
    TextView etTotalPembayaran;
    @BindView(R.id.etRequestCicilan)
    TextView etRequestCicilan;
    @BindView(R.id.etTotal)
    TextView etTotal;
    @BindView(R.id.SpinCicilan)
    Spinner SpinCicilan;
    @BindView(R.id.btRequest)
    Button btRequest;
    int hutangpokok, bunga, denda, totalpembayaran, biayarequestcicilan;
    String totalfinal;
    int totaltagihan;
    int cicilan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_cicilan);
        ButterKnife.bind(this);

        SpinCicilan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                spinvalues = SpinCicilan.getSelectedItem().toString();
                Perhitungan();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getTanggal();
    }


    private String getTanggal() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void Perhitungan() {

        biayarequestcicilan = Integer.parseInt(etRequestCicilan.getText().toString());
        hutangpokok = Integer.parseInt(etHutangPokok.getText().toString());
        bunga = Integer.parseInt(etBunga.getText().toString());
        denda = Integer.parseInt(etDenda.getText().toString());
        totalpembayaran = Integer.parseInt(etTotalPembayaran.getText().toString());

        totaltagihan = Integer.parseInt(Integer.toString(hutangpokok + bunga + denda - totalpembayaran));
        Log.d("SisaTotalTagihan", String.valueOf(totaltagihan));
        totalfinal = String.valueOf(Integer.parseInt(String.valueOf(totaltagihan + biayarequestcicilan)));
        Log.d("Total", Integer.toString(Integer.parseInt(totalfinal)));

        Log.d("cicilan", String.valueOf(biayarequestcicilan));

        etTotal.setText(DecimalsFormat.priceWithoutDecimal(Integer.toString(Integer.parseInt(totalfinal))));

        cicilan = biayarequestcicilan / Integer.parseInt(SpinCicilan.getSelectedItem().toString());

    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.btRequest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(RequestCicilan.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.btRequest:


                intent = new Intent(RequestCicilan.this, DetRequestCicilan.class);
                intent.putExtra("sisatotaltagihan", String.valueOf(totaltagihan));
                intent.putExtra("cicilan", String.valueOf(cicilan));
                intent.putExtra("brpxcicilan", SpinCicilan.getSelectedItem().toString());
                intent.putExtra("jatuhtempo", getTanggal());
                startActivity(intent);
                break;
        }
    }


}
