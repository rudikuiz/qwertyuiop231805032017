package com.winwin.project.winwin.Model;

/**
 */

public class ListNotifModel {
    String nama,alamat;

    public ListNotifModel(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    public ListNotifModel() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
