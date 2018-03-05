package com.winwin.project.winwin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.winwin.project.winwin.Config.http.TAG_USERNAME;

public class OperasionalActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    String username;

    @BindView(R.id.txtUser)
    TextView txtUser;
    @BindView(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operasional);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(LoginPage.my_shared_preferences, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(TAG_USERNAME, "");
        txtUser.setText(username);
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        finish();
    }
}
