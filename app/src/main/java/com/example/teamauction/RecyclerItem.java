package com.example.teamauction;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;
    private boolean isSelected;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setTitle(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) { descStr = desc ; }
    public void setSelected(boolean selected) { isSelected = selected; }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
    public boolean getSelected() { return isSelected; }
}