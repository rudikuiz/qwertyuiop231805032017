package com.winwin.project.winwin.Setting;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.winwin.project.winwin.R;

/**
 * Created by Lenovo on 2/7/2018.
 */

public class OwnProgressDialog {

    public AlertDialog.Builder builder;
    public Dialog dialog;
    public Context context;

    public OwnProgressDialog(Context context) {
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
        this.builder.setView(R.layout.progress);
        this.dialog = builder.create();
        this.dialog.setCancelable(false);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
