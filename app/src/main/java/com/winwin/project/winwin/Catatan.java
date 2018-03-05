package com.winwin.project.winwin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.winwin.project.winwin.Adapter.AdapterItemMenu;
import com.winwin.project.winwin.Model.ModelItemMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Catatan extends AppCompatActivity {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rvDaftarMenu)
    RecyclerView rvDaftarMenu;

    AdapterItemMenu adapterItemMenu;
    ArrayList<ModelItemMenu> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);
        ButterKnife.bind(this);

        DataMenu();

        rvDaftarMenu.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(Catatan.this, 1,
                GridLayoutManager.VERTICAL, false);
        rvDaftarMenu.setLayoutManager(layoutManager);

        adapterItemMenu = new AdapterItemMenu(arrayList, Catatan.this);
        rvDaftarMenu.setAdapter(adapterItemMenu);
    }

    private void DataMenu() {
        arrayList.add(new ModelItemMenu(R.drawable.catatan, "Komisi"));
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        finish();
    }
}
