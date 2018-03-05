package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 23/02/2018.
 * Updated by Muhammad Iqbal on 23/02/2018.
 */

public class ModelTableKomisi {
    String id,nama,biayaope,komisi;

    public ModelTableKomisi() {
    }

    public ModelTableKomisi(String id, String nama, String biayaope, String komisi) {
        this.id = id;
        this.nama = nama;
        this.biayaope = biayaope;
        this.komisi = komisi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBiayaope() {
        return biayaope;
    }

    public void setBiayaope(String biayaope) {
        this.biayaope = biayaope;
    }

    public String getKomisi() {
        return komisi;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }
}
