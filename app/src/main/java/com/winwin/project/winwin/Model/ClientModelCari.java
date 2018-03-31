package com.winwin.project.winwin.Model;


public class ClientModelCari {

    String number, nama, detail;

    public ClientModelCari() {
    }

    public ClientModelCari(String number, String nama, String detail) {
        this.number = number;
        this.nama = nama;
        this.detail = detail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
