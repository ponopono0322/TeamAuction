package com.example.teamauction;

import android.graphics.drawable.Drawable;

/**
 * 리스트뷰 값에 관련된 클래스
 */

public class ListViewItem {
    private Drawable icon ; //리스트뷰 이미지
    private String text ; //리스트뷰 첫번째 텍스트박스 입력값
    private String massage; //리스트뷰 두번째 텍스트박스 입력값
    private String uninumber; // DB에서 아이템  uninumber 받은값
    private String regnumber;  // DB에서 아이템 regnumber 받은값
    private boolean isSelected; // 리스트뷰 아이템 이 선택 되었는지 값

    public void setIcon(Drawable icon) {
        this.icon = icon ;
    } //리스트뷰 이미지 지정
    public void setText(String text) {
        this.text = text ;
    } //리스트뷰 첫번째 텍스트박스 입력값 지정
    public void setMassage(String massage) {
        this.massage = massage;
    } //리스트뷰 두번째 텍스트박스 입력값 지정
    public void setUninumber(String uninumber) { this.uninumber = uninumber;} // DB에서 아이템  uninumber 받은값 지정
    public void setRegnumber(String regnumber) { this.regnumber = regnumber;} // DB에서 아이템 regnumber 받은값 지정
    public void setSelected(boolean selected) { isSelected = selected; } // 리스트뷰 아이템 이 선택 되었는지 값 지정

    public Drawable getIcon() {
        return this.icon ;
    } //리스트뷰 이미지 리턴
    public String getText() {
        return this.text ;
    }  //리스트뷰 첫번째 텍스트박스 입력값 리턴
    public String getMassage() {
        return massage;
    } //리스트뷰 두번째 텍스트박스 입력값 리턴
    public String getUninumber() {return uninumber;} // DB에서 아이템  uninumber 받은값 리턴
    public String getRegnumber() {return regnumber;} // DB에서 아이템 regnumber 받은값 리턴
    public boolean getSelected() { return isSelected; } // 리스트뷰 아이템 이 선택 되었는지 값 리턴
}