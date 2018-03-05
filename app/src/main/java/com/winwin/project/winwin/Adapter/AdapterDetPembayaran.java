package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelDetailPembayaran;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 03/03/2018.
 * Updated by Muhammad Iqbal on 03/03/2018.
 */

public class AdapterDetPembayaran extends RecyclerView.Adapter<AdapterDetPembayaran.DetPembayaranHolder> {

    private ArrayList<ModelDetailPembayaran> arrayList;
    private Context context;

    public AdapterDetPembayaran(ArrayList<ModelDetailPembayaran> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public DetPembayaranHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pop_up_detail_pembayaran, parent, false);
        return new DetPembayaranHolder(view);
    }

    @Override
    public void onBindViewHolder(DetPembayaranHolder holder, int position) {
        holder.Nama.setText(arrayList.get(position).getNama());
        holder.jumlahNilai.setText(arrayList.get(position).getJumlah());
        holder.Tanggal.setText(arrayList.get(position).getTanggal());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class DetPembayaranHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.jumlahNilai)
        TextView jumlahNilai;
        @BindView(R.id.Tanggal)
        TextView Tanggal;
        @BindView(R.id.Nama)
        TextView Nama;

        public DetPembayaranHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
