package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 23/02/2018.
 * Updated by Muhammad Iqbal on 23/02/2018.
 */

public class ModelHistoryaPencairan {
    String Tanggal,Danamasuk,Komisi,Status;

    public ModelHistoryaPencairan() {
    }

    public ModelHistoryaPencairan(String tanggal, String danamasuk, String komisi, String status) {
        Tanggal = tanggal;
        Danamasuk = danamasuk;
        Komisi = komisi;
        Status = status;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getDanamasuk() {
        return Danamasuk;
    }

    public void setDanamasuk(String danamasuk) {
        Danamasuk = danamasuk;
    }

    public String getKomisi() {
        return Komisi;
    }

    public void setKomisi(String komisi) {
        Komisi = komisi;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
