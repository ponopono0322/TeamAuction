package com.example.teamauction;

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable icon ;
    private String text ;
    private String massage;
    private String uninumber;
    private String regnumber;
    private boolean isSelected;

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    }
    public void setText(String text) {
        this.text = text ;
    }
    public void setMassage(String massage) {
        this.massage = massage;
    }
    public void setUninumber(String uninumber) { this.uninumber = uninumber;}
    public void setRegnumber(String regnumber) { this.regnumber = regnumber;}
    public void setSelected(boolean selected) { isSelected = selected; }

    public Drawable getIcon() {
        return this.icon ;
    }
    public String getText() {
        return this.text ;
    }
    public String getMassage() {
        return massage;
    }
    public String getUninumber() {return uninumber;}
    public String getRegnumber() {return regnumber;}
    public boolean getSelected() { return isSelected; }
}