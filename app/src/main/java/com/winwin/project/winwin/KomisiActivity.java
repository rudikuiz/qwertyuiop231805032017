package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class KomisiActivity extends AppCompatActivity {

    @BindView(R.id.id_client)
    TextView idClient;
    @BindView(R.id.Tahapan)
    Spinner Tahapan;
    @BindView(R.id.btnTampil)
    Button btnTampil;
    @BindView(R.id.btnHistory)
    Button btnHistory;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ic_home)
    ImageView icHome;
    String values, valess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komisi);
        ButterKnife.bind(this);
        spinner();
    }

    private void spinner() {
        Tahapan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                values = Tahapan.getSelectedItem().toString();
                if (values.equals("Tahap 1")) {
                valess = "1";
                }

                if (values.equals("Tahap 2")) {
                    valess = "2";
                }

                if (values.equals("Tahap 3")) {
                    valess = "3";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.img_back, R.id.btnTampil, R.id.btnHistory, R.id.ic_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btnTampil:
                Intent i = new Intent(KomisiActivity.this, DetKomisiActivity.class);
                String tahapan = Tahapan.getSelectedItem().toString();
                i.putExtra("tahapan", tahapan);
                i.putExtra("values", valess);
                startActivity(i);
                break;
            case R.id.btnHistory:
                Intent in = new Intent(KomisiActivity.this, HistoryPencairanActivity.class);
                in.putExtra("values", valess);
                startActivity(in);
                break;
            case R.id.ic_home:
                Intent intent = new Intent(KomisiActivity.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
    }

}
