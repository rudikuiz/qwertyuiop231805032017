package com.winwin.project.winwin.Model;

/**
 * Created by Ayo Maju on 15/02/2018.
 * Updated by Muhammad Iqbal on 15/02/2018.
 */

public class ModelItemMenu {
    private int Img;
    private String title;

    public ModelItemMenu() {
    }

    public ModelItemMenu(int img, String title) {
        Img = img;
        this.title = title;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
