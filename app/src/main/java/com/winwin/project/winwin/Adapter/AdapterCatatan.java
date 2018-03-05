package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelCatatan;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ayo Maju on 09/02/2018.
 * Updated by Muhammad Iqbal on 09/02/2018.
 */

public class AdapterCatatan extends RecyclerView.Adapter<AdapterCatatan.CatatanHolder> {

    private Context context;
    private ArrayList<ModelCatatan> listCatatan;

    public AdapterCatatan(Context context, ArrayList<ModelCatatan> listCatatan) {
        this.context = context;
        this.listCatatan = listCatatan;
    }


    public class CatatanHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.xxx)
        TextView xxx;
        @BindView(R.id.txtTanggal)
        TextView txtTanggal;
        @BindView(R.id.txtJam)
        TextView txtJam;

        public CatatanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public CatatanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_tulis_catatan, parent, false);
        return new CatatanHolder(view);
    }


    @Override
    public void onBindViewHolder(CatatanHolder holder, int position) {
        holder.xxx.setText(listCatatan.get(position).getXxx());
        holder.txtJam.setText(listCatatan.get(position).getTxtjam());
        holder.txtTanggal.setText(listCatatan.get(position).getTxtTanggal());
    }

    @Override
    public int getItemCount() {
        return listCatatan.size();
    }


}
