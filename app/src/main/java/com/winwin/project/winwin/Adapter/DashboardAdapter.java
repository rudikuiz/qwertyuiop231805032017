package com.winwin.project.winwin.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwin.project.winwin.AnalistDebt;
import com.winwin.project.winwin.DaftarClientTagih;
import com.winwin.project.winwin.JanjiBayar;
import com.winwin.project.winwin.KomisiActivity;
import com.winwin.project.winwin.ListBadCollector;
import com.winwin.project.winwin.ListJanjiBayar;
import com.winwin.project.winwin.Model.ModelDashboard;
import com.winwin.project.winwin.PengambilanVisit;
import com.winwin.project.winwin.ProfileActivity;
import com.winwin.project.winwin.R;
import com.winwin.project.winwin.RequestCicilan;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardHolder> {

    private ArrayList<ModelDashboard> arrayList;
    private Context context;

    public DashboardAdapter(ArrayList<ModelDashboard> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public DashboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_menu_dashboard, parent, false);
        return new DashboardHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardHolder holder, final int position) {
        holder.btnProfil.setImageResource(arrayList.get(position).getImage());
        holder.etTitle.setText(arrayList.get(position).getTitle());
        holder.btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0) {
                    context.startActivity(new Intent(context, DaftarClientTagih.class));
                }

                if (position == 1) {
                    context.startActivity(new Intent(context, KomisiActivity.class));
                }

                if (position == 2) {
//                    context.startActivity(new Intent(context, ProfileActivity.class));
                }

                if (position == 3) {
//                    context.startActivity(new Intent(context, AnalistDebt.class));
                }

                if (position == 4) {
//                    context.startActivity(new Intent(context, ListBadCollector.class));
                }

                if (position == 5) {
//                    context.startActivity(new Intent(context, ListJanjiBayar.class));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DashboardHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btnProfil)
        ImageView btnProfil;
        @BindView(R.id.etTitle)
        TextView etTitle;

        public DashboardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
