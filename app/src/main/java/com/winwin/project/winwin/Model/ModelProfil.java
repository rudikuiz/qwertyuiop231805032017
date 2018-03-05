package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 03/03/2018.
 * Updated by Muhammad Iqbal on 03/03/2018.
 */

public class ModelProfil {
    String namadepan,namalengkap,notelp,email,password,norek,bank,cabang,atasnama;

    public ModelProfil() {
    }

    public ModelProfil(String namadepan, String namalengkap, String notelp, String email, String password, String norek, String bank, String cabang, String atasnama) {
        this.namadepan = namadepan;
        this.namalengkap = namalengkap;
        this.notelp = notelp;
        this.email = email;
        this.password = password;
        this.norek = norek;
        this.bank = bank;
        this.cabang = cabang;
        this.atasnama = atasnama;
    }

    public String getNamadepan() {
        return namadepan;
    }

    public void setNamadepan(String namadepan) {
        this.namadepan = namadepan;
    }

    public String getNamalengkap() {
        return namalengkap;
    }

    public void setNamalengkap(String namalengkap) {
        this.namalengkap = namalengkap;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNorek() {
        return norek;
    }

    public void setNorek(String norek) {
        this.norek = norek;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public void setAtasnama(String atasnama) {
        this.atasnama = atasnama;
    }
}
