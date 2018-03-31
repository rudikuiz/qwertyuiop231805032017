package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ClientModelCari;
import com.winwin.project.winwin.Model.ModelMenu;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {

    private ArrayList<ClientModelCari> mExampleList;
    Context context;

    public class SearchHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.nama_item)
        TextView namaItem;
        @BindView(R.id.detail_item)
        TextView detailItem;

        public SearchHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public SearchAdapter(ArrayList<ClientModelCari> mExampleList, Context context) {
        this.mExampleList = mExampleList;
        this.context = context;
    }

    @Override
    public SearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daftar_client, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchHolder holder, int position) {
        ClientModelCari currentItem = mExampleList.get(position);
        int i = position + 1;
        holder.number.setText(i);
        holder.namaItem.setText(currentItem.getNama());
        holder.detailItem.setText(currentItem.getDetail());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterlist(ArrayList<ClientModelCari> filteredList){
        mExampleList = filteredList;
        notifyDataSetChanged();
    }

}
