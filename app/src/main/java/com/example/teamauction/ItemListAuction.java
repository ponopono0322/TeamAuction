package com.example.teamauction;
import android.graphics.drawable.Drawable;

public class ItemListAuction {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setName(String title) {
        titleStr = title ;
    }
    public void setDesc(String desc) {
        descStr = desc ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getName() {
        return this.titleStr ;
    }
    public String getDesc() {
        return this.descStr ;
    }
}
