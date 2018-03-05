package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 03/03/2018.
 * Updated by Muhammad Iqbal on 03/03/2018.
 */

public class ModelDetailPembayaran {
    String no,jumlah,tanggal,nama;

    public ModelDetailPembayaran() {
    }

    public ModelDetailPembayaran(String no, String jumlah, String tanggal, String nama) {
        this.no = no;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.nama = nama;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
