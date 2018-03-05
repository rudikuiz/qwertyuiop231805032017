package com.winwin.project.winwin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.winwin.project.winwin.Model.ModelItemMenu;
import com.winwin.project.winwin.OperasionalActivity;
import com.winwin.project.winwin.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ayo Maju on 15/02/2018.
 * Updated by Muhammad Iqbal on 15/02/2018.
 */

public class AdapterItemMenu extends RecyclerView.Adapter<AdapterItemMenu.AdapterItemHolder> {

    private ArrayList<ModelItemMenu> arrayList;
    private Context context;

    public class AdapterItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_catatan)
        CircleImageView imgCatatan;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.card_menu)
        CardView cardMenu;

        @OnClick(R.id.card_menu)
        public void onViewClicked() {
            Intent i = new Intent(context, OperasionalActivity.class);
            context.startActivity(i);
        }

        public AdapterItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public AdapterItemMenu(ArrayList<ModelItemMenu> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public AdapterItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_daftar_menu, parent, false);

        return new AdapterItemHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterItemHolder holder, int position) {
        holder.imgCatatan.setImageResource(arrayList.get(position).getImg());
        holder.title.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}
