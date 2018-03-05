package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelHistoryaPencairan;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 23/02/2018.
 * Updated by Muhammad Iqbal on 23/02/2018.
 */

public class AdapterHistoryPencairan extends RecyclerView.Adapter<AdapterHistoryPencairan.PencairanHolder> {

    private ArrayList<ModelHistoryaPencairan> arrayList;
    private Context context;

    public class PencairanHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tanggal)
        TextView tanggal;
        @BindView(R.id.danamasuk)
        TextView danamasuk;
        @BindView(R.id.komisi)
        TextView komisi;
        @BindView(R.id.status)
        TextView status;

        public PencairanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public AdapterHistoryPencairan(ArrayList<ModelHistoryaPencairan> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public PencairanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_history_pencairan, parent, false);
        return new PencairanHolder(view);
    }

    @Override
    public void onBindViewHolder(PencairanHolder holder, int position) {
        holder.tanggal.setText(arrayList.get(position).getTanggal());
        holder.danamasuk.setText(arrayList.get(position).getDanamasuk());
        holder.komisi.setText(arrayList.get(position).getKomisi());
        holder.status.setText(arrayList.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
