package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 03/03/2018.
 * Updated by Muhammad Iqbal on 03/03/2018.
 */

public class ModelJatuhTempo {
    String nama,nilai,tempo;

    public ModelJatuhTempo() {
    }

    public ModelJatuhTempo(String nama, String nilai, String tempo) {
        this.nama = nama;
        this.nilai = nilai;
        this.tempo = tempo;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
