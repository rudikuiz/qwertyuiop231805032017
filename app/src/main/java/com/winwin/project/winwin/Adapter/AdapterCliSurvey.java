package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.DetailPelanggan;
import com.winwin.project.winwin.DetailTagihan;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**

 */

public class AdapterCliSurvey extends RecyclerView.Adapter<AdapterCliSurvey.CliHolder> {

    private ArrayList<ModelMenu> modelMenus;
    private Context context;

    public AdapterCliSurvey(ArrayList<ModelMenu> modelMenus, Context context) {
        this.modelMenus = modelMenus;
        this.context = context;
    }

    @Override
    public CliHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daftar_client, parent, false);
        return new CliHolder(view);
    }

    @Override
    public void onBindViewHolder(CliHolder holder, final int position) {
        int number = position + 1;
        holder.number.setText(number);
        holder.namaItem.setText(modelMenus.get(position).getCli_nama());
        holder.detailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailPelanggan.class);
                intent.putExtra("id_client", modelMenus.get(position).getCli_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelMenus.size();
    }

    public class CliHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.nama_item)
        TextView namaItem;
        @BindView(R.id.detail_item)
        TextView detailItem;
        @BindView(R.id.id_client)
        TextView idClient;

        public CliHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
