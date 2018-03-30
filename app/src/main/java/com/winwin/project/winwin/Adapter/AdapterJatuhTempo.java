package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelJatuhTempo;
import com.winwin.project.winwin.R;
import com.winwin.project.winwin.Setting.DecimalsFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 03/03/2018.
 * Updated by Muhammad Iqbal on 03/03/2018.
 */

public class AdapterJatuhTempo extends RecyclerView.Adapter<AdapterJatuhTempo.JatuhTempoHolder> {

    private ArrayList<ModelJatuhTempo> arrayList;
    private Context context;

    public AdapterJatuhTempo(ArrayList<ModelJatuhTempo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public JatuhTempoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_jatuh_tempo_berikutnya, parent, false);
        return new JatuhTempoHolder(view);
    }

    @Override
    public void onBindViewHolder(JatuhTempoHolder holder, int position) {
        holder.nama.setText(arrayList.get(position).getNama());
        holder.danamasuk.setText("Rp. "+DecimalsFormat.priceWithoutDecimal(arrayList.get(position).getNilai()));
        holder.tanggal.setText(arrayList.get(position).getTempo());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class JatuhTempoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.danamasuk)
        TextView danamasuk;
        @BindView(R.id.tanggal)
        TextView tanggal;

        public JatuhTempoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
