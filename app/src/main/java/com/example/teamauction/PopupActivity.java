package com.example.teamauction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {

    TextView txtText;
    Button yes_btn, no_btn;
    int request_num_yes, request_num_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);

        //UI 객체생성
        txtText = findViewById(R.id.txtText);
        yes_btn = findViewById(R.id.check_yes);
        no_btn = findViewById(R.id.check_no);

        //데이터 가져오기
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        txtText.setText(data);

        if(data.equals(getString(R.string.wanttologout))) {
            request_num_yes = 1;
            request_num_no = 0;
        }
        else if(data.equals(getString(R.string.access_account))) {
            request_num_yes = 3;
            request_num_no = 2;
        }

        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClose(request_num_yes);
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnClose(request_num_no);
            }
        });

    }

    //확인 버튼 클릭
    public void mOnClose(int i){
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra("result", i);
        setResult(RESULT_OK, intent);

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}