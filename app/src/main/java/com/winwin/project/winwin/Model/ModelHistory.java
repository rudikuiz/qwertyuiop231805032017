package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 15/02/2018.
 * Updated by Muhammad Iqbal on 15/02/2018.
 */

public class ModelHistory {
    private String waktu,pembayaran,via,catatan;

    public ModelHistory() {
    }

    public ModelHistory(String waktu, String pembayaran, String via, String catatan) {
        this.waktu = waktu;
        this.pembayaran = pembayaran;
        this.via = via;
        this.catatan = catatan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(String pembayaran) {
        this.pembayaran = pembayaran;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
