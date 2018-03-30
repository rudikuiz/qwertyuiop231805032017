package com.winwin.project.winwin.Model;

/**
 */

public class ListNotifModel {
    String nama,alamat,aksi,pengajuan_id;

    public ListNotifModel(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    public ListNotifModel() {
    }

    public String getPengajuan_id() {
        return pengajuan_id;
    }

    public void setPengajuan_id(String pengajuan_id) {
        this.pengajuan_id = pengajuan_id;
    }

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
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
