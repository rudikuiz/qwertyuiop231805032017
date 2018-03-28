package com.winwin.project.winwin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.winwin.project.winwin.Adapter.AdapterListNotification;
import com.winwin.project.winwin.Model.ListNotifModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListNotification extends AppCompatActivity {

    @BindView(R.id.ic_home)
    ImageView icHome;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    private ArrayList<ListNotifModel> list = new ArrayList<>();
    AdapterListNotification adapterListNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notification);
        ButterKnife.bind(this);

        dataku();

        GridLayoutManager layoutManager = new GridLayoutManager(ListNotification.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManager);

        adapterListNotification = new AdapterListNotification(list,ListNotification.this);
        rvNotification.setAdapter(adapterListNotification);
    }

    private void dataku() {
        list.add(new ListNotifModel("Waylon Dalton","Jl Sunandar"));
    }

    @OnClick({R.id.ic_home, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ic_home:
                Intent intent = new Intent(ListNotification.this, MenuActivity.class);
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
