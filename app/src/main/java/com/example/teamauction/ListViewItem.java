package com.example.teamauction;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class ListViewItem {
    private Drawable icon ;
    private String text ;
    private String massage;

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public Drawable getIcon() {
        return this.icon ;
    }

    public String getText() {
        return this.text ;
    }

    public String getMassage() {
        return massage;
    }
}