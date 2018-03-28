package com.winwin.project.winwin.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.DetailPelanggan;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterAnalistdebt extends RecyclerView.Adapter<AdapterAnalistdebt.AnalistdebtHolder> {


    private ArrayList<ModelMenu> lisd_data;
    private Context context;

    public AdapterAnalistdebt(ArrayList<ModelMenu> lisd_data, Context context) {
        this.lisd_data = lisd_data;
        this.context = context;
    }

    @Override
    public AnalistdebtHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_analis_debt, parent, false);
        return new AnalistdebtHolder(view);
    }

    @Override
    public void onBindViewHolder(AnalistdebtHolder holder, int position) {
        int nomer = position +1;
        final int pos = position;
        holder.number.setText(String.valueOf(nomer));
        holder.namaItem.setText(lisd_data.get(position).getCli_nama());
        holder.idClient.setText(lisd_data.get(position).getCli_id());
        holder.status.setText("Not Verified");
        holder.detailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPelanggan.class);
                intent.putExtra("id_client", lisd_data.get(pos).getCli_id());
                intent.putExtra("pengajuan_id", lisd_data.get(pos).getPengajuan_id());
                context.startActivity(intent);
            }
        });
        if (holder.status.getText().equals("Not Verified")){
            holder.status.setTextColor(context.getResources().getColor(R.color.notverified));
        }else if (holder.status.getText().equals("Verified")){
            holder.status.setTextColor(context.getResources().getColor(R.color.hijau_btn));
        }
    }

    @Override
    public int getItemCount() {
        return lisd_data.size();
    }

    public class AnalistdebtHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.nama_item)
        TextView namaItem;
        @BindView(R.id.detail_item)
        TextView detailItem;
        @BindView(R.id.id_client)
        TextView idClient;
        @BindView(R.id.status)
        TextView status;

        public AnalistdebtHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
