package com.winwin.project.winwin.Model;

/**
 */

public class ModelDashboard {
    private int Image;
    private String Title;

    public ModelDashboard(int image, String title) {
        Image = image;
        Title = title;
    }

    public ModelDashboard() {
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
