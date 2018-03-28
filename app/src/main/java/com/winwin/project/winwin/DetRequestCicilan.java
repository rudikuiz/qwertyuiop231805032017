package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetRequestCicilan extends AppCompatActivity {

    @BindView(R.id.etTotalSisa)
    TextView etTotalSisa;
    @BindView(R.id.etNominal)
    TextView etNominal;
    @BindView(R.id.etJmlCicilan)
    TextView etJmlCicilan;
    @BindView(R.id.etJatuhTempo)
    TextView etJatuhTempo;
    String sisattotal, cicilan, brpx, jatuhtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_det_request_cicilan);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        sisattotal = intent.getStringExtra("sisatotaltagihan");
        cicilan = intent.getStringExtra("cicilan");
        brpx = intent.getStringExtra("brpxcicilan");
        jatuhtemp = intent.getStringExtra("jatuhtempo");

        etTotalSisa.setText(sisattotal);
        etNominal.setText(cicilan);
        etJmlCicilan.setText(brpx);
        etJatuhTempo.setText(jatuhtemp);
    }

    @OnClick({R.id.ic_home, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(DetRequestCicilan.this, MenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.img_back:
                finish();
                break;
        }
    }
}
