package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.DetailTagihan;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 02/02/2018.
 */


public class AdapterMenu extends RecyclerView.Adapter<AdapterMenu.AdapterMenuHolder> {


    private ArrayList<ModelMenu> lisd_data;
    private Context context;

    public class AdapterMenuHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.nama_item)
        TextView namaItem;
        @BindView(R.id.detail_item)
        TextView detailItem;
        @BindView(R.id.id_client)
        TextView idClient;

        public AdapterMenuHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public AdapterMenu(ArrayList<ModelMenu> lisd_data, Context context) {
        this.lisd_data = lisd_data;
        this.context = context;
    }

    @Override
    public AdapterMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daftar_client, parent, false);
        return new AdapterMenuHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterMenuHolder holder, final int position) {
        int i = position+1;
        holder.number.setText(String.valueOf(i));
        holder.namaItem.setText(lisd_data.get(position).getCli_nama());
        holder.idClient.setText(lisd_data.get(position).getCli_id());
        holder.detailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailTagihan.class);
                intent.putExtra("id_client", lisd_data.get(position).getCli_id());
                context.startActivity(intent);
            }
        });

        if (lisd_data.get(position).getPeng_lunas().equals("t")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.hijau_btn));
            holder.status.setText("Lunas");
        } else if (lisd_data.get(position).getPeng_cicilan().equals("1")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.maron));
            holder.status.setText("Cicilan Aktif");
        } else if (lisd_data.get(position).getPeng_janji().equals("1")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.status.setText("Janji Bayar Aktif");
        } else {
            holder.status.setTextColor(context.getResources().getColor(R.color.white));
            holder.status.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return lisd_data.size();
    }


}
