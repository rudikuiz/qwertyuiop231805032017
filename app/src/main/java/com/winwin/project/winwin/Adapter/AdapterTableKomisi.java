package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelTableKomisi;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 23/02/2018.
 * Updated by Muhammad Iqbal on 23/02/2018.
 */

public class AdapterTableKomisi extends RecyclerView.Adapter<AdapterTableKomisi.TableKomisiHolder> {

    private ArrayList<ModelTableKomisi> arrayList;
    private Context context;

    public class TableKomisiHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.Nama)
        TextView Nama;
        @BindView(R.id.Biaya)
        TextView Biaya;
        @BindView(R.id.Komisi)
        TextView Komisi;

        public TableKomisiHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public AdapterTableKomisi(ArrayList<ModelTableKomisi> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public TableKomisiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tabel_komisi, parent, false);
        return new TableKomisiHolder(view);
    }

    @Override
    public void onBindViewHolder(TableKomisiHolder holder, int position) {
        holder.number.setText(arrayList.get(position).getId());
        holder.Nama.setText(arrayList.get(position).getNama());
        holder.Biaya.setText(arrayList.get(position).getBiayaope());
        holder.Komisi.setText(arrayList.get(position).getKomisi());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
