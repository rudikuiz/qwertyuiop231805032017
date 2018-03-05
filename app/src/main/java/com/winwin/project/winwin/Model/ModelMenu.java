package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 02/02/2018.
 */

public class ModelMenu {
    private String number, cli_nama, cli_id;

    public ModelMenu() {
    }

    public ModelMenu(String number, String cli_nama, String cli_id) {
        this.number = number;
        this.cli_nama = cli_nama;
        this.cli_id = cli_id;
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
}
