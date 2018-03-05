package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelHistory;
import com.winwin.project.winwin.R;
import com.winwin.project.winwin.Setting.DecimalsFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 15/02/2018.
 * Updated by Muhammad Iqbal on 15/02/2018.
 */

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoryHolder> {

    private ArrayList<ModelHistory> arrayList = new ArrayList<>();
    private Context context;
    String Rupiah = "Rp. ";
    String desimal = " ,-";

    public AdapterHistory(ArrayList<ModelHistory> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_history, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, int position) {
        holder.hari.setText(arrayList.get(position).getWaktu());
        holder.pembayaran.setText(arrayList.get(position).getPembayaran());
        holder.via.setText(Rupiah + DecimalsFormat.priceWithoutDecimal(arrayList.get(position).getVia())+ desimal);
        holder.catatan.setText(arrayList.get(position).getCatatan());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hari)
        TextView hari;
        @BindView(R.id.pembayaran)
        TextView pembayaran;
        @BindView(R.id.via)
        TextView via;
        @BindView(R.id.catatan)
        TextView catatan;

        public HistoryHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
