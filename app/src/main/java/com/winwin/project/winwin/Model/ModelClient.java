package com.winwin.project.winwin.Model;

/**
 */

public class ModelClient {
    String kode, nama, nohp, alamat, ktp, perusahaan, posisi, telp_perusahaan,
            alamat_perusahaan, pengajuan_tgl, pgj_nilai_pgj, pgjtotal, denda_biaya,
            komisi, operasional;

    public ModelClient() {
    }

    public ModelClient(String kode, String nama, String nohp, String alamat, String ktp, String perusahaan, String posisi, String telp_perusahaan, String alamat_perusahaan, String pengajuan_tgl, String pgj_nilai_pgj, String pgjtotal, String denda_biaya, String komisi, String operasional) {
        this.kode = kode;
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
        this.ktp = ktp;
        this.perusahaan = perusahaan;
        this.posisi = posisi;
        this.telp_perusahaan = telp_perusahaan;
        this.alamat_perusahaan = alamat_perusahaan;
        this.pengajuan_tgl = pengajuan_tgl;
        this.pgj_nilai_pgj = pgj_nilai_pgj;
        this.pgjtotal = pgjtotal;
        this.denda_biaya = denda_biaya;
        this.komisi = komisi;
        this.operasional = operasional;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getTelp_perusahaan() {
        return telp_perusahaan;
    }

    public void setTelp_perusahaan(String telp_perusahaan) {
        this.telp_perusahaan = telp_perusahaan;
    }

    public String getAlamat_perusahaan() {
        return alamat_perusahaan;
    }

    public void setAlamat_perusahaan(String alamat_perusahaan) {
        this.alamat_perusahaan = alamat_perusahaan;
    }

    public String getPengajuan_tgl() {
        return pengajuan_tgl;
    }

    public void setPengajuan_tgl(String pengajuan_tgl) {
        this.pengajuan_tgl = pengajuan_tgl;
    }

    public String getPgj_nilai_pgj() {
        return pgj_nilai_pgj;
    }

    public void setPgj_nilai_pgj(String pgj_nilai_pgj) {
        this.pgj_nilai_pgj = pgj_nilai_pgj;
    }

    public String getPgjtotal() {
        return pgjtotal;
    }

    public void setPgjtotal(String pgjtotal) {
        this.pgjtotal = pgjtotal;
    }

    public String getDenda_biaya() {
        return denda_biaya;
    }

    public void setDenda_biaya(String denda_biaya) {
        this.denda_biaya = denda_biaya;
    }

    public String getKomisi() {
        return komisi;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }

    public String getOperasional() {
        return operasional;
    }

    public void setOperasional(String operasional) {
        this.operasional = operasional;
    }
}
