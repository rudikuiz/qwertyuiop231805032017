package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 02/02/2018.
 */

public class ModelMenu {
    private String number, cli_nama, cli_id, pengajuan_id, detail, status,peng_lunas,peng_cicilan,peng_janji,alamat,kecamatan,kota;

    public ModelMenu() {
    }

    public ModelMenu(String number, String cli_nama, String cli_id) {
        this.number = number;
        this.cli_nama = cli_nama;
        this.cli_id = cli_id;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getPeng_lunas() {
        return peng_lunas;
    }

    public void setPeng_lunas(String peng_lunas) {
        this.peng_lunas = peng_lunas;
    }

    public String getPeng_cicilan() {
        return peng_cicilan;
    }

    public void setPeng_cicilan(String peng_cicilan) {
        this.peng_cicilan = peng_cicilan;
    }

    public String getPeng_janji() {
        return peng_janji;
    }

    public void setPeng_janji(String peng_janji) {
        this.peng_janji = peng_janji;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCli_nama() {
        return cli_nama;
    }

    public void setCli_nama(String cli_nama) {
        this.cli_nama = cli_nama;
    }

    public String getCli_id() {
        return cli_id;
    }

    public void setCli_id(String cli_id) {
        this.cli_id = cli_id;
    }

    public String getPengajuan_id() {
        return pengajuan_id;
    }

    public void setPengajuan_id(String pengajuan_id) {
        this.pengajuan_id = pengajuan_id;
    }
}
