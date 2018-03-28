package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.JanjiBayar;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.R;
import com.winwin.project.winwin.RequestCicilan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**

 */

public class AdapterListJanji extends RecyclerView.Adapter<AdapterListJanji.ListJanjiHolder> {

    private ArrayList<ModelMenu> lisd_data;
    private Context context;

    public AdapterListJanji(ArrayList<ModelMenu> lisd_data, Context context) {
        this.lisd_data = lisd_data;
        this.context = context;
    }

    @Override
    public ListJanjiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_debt, parent, false);
        return new ListJanjiHolder(view);
    }

    @Override
    public void onBindViewHolder(ListJanjiHolder holder, final int position) {
        holder.number.setText(lisd_data.get(position).getNumber());
        holder.namaItem.setText(lisd_data.get(position).getCli_nama());
        holder.idClient.setText(lisd_data.get(position).getCli_id());
        holder.detailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, JanjiBayar.class);
                intent.putExtra("id_client", lisd_data.get(position).getCli_id());
                intent.putExtra("nama", lisd_data.get(position).getCli_nama());
                intent.putExtra("pengajuan_id", lisd_data.get(position).getPengajuan_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lisd_data.size();
    }

    public class ListJanjiHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.nama_item)
        TextView namaItem;
        @BindView(R.id.detail_item)
        TextView detailItem;
        @BindView(R.id.id_client)
        TextView idClient;
        public ListJanjiHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
