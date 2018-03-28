package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Rekomendiasi extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.rbSetuju)
    RadioButton rbSetuju;
    @BindView(R.id.rbTolak)
    RadioButton rbTolak;
    @BindView(R.id.rgConfirmation)
    RadioGroup rgConfirmation;
    @BindView(R.id.btKirim)
    Button btKirim;
    String Confirmasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendiasi);
        ButterKnife.bind(this);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        int id = rgConfirmation.getCheckedRadioButtonId();
        switch (id) {
            case R.id.rbSetuju:
                Confirmasi = "Disetujui";
                break;
            case R.id.rbTolak:
                Confirmasi = "Ditolak";
                break;
        }
    }

    @OnClick({R.id.ic_home, R.id.img_back, R.id.rgConfirmation, R.id.btKirim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(Rekomendiasi.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.rgConfirmation:
                break;
            case R.id.btKirim:
                onRadioButtonClicked(view);
                Toast.makeText(Rekomendiasi.this, "" + Confirmasi, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
