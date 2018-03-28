package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ListNotifModel;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class AdapterListNotification extends RecyclerView.Adapter<AdapterListNotification.ListHolder> {

    private ArrayList<ListNotifModel> list;
    private Context context;

    public AdapterListNotification(ArrayList<ListNotifModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_notification, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        int number = position+1;
        holder.no.setText(String.valueOf(number));
        holder.nama.setText(list.get(position).getNama());
        holder.alamat.setText(list.get(position).getAlamat());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.no)
        TextView no;
        @BindView(R.id.nama)
        TextView nama;
        @BindView(R.id.alamat)
        TextView alamat;
        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
