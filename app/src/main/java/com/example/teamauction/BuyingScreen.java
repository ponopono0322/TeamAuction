package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuyingScreen extends AppCompatActivity {
    private TextView buyCostBox;
    private EditText editTextquantity;
    private EditText editTextcost;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_buying);


        Intent intent = getIntent();
        String ItemName = intent.getExtras().getString("data");

        //UI 객체생성
        yes_btn = findViewById(R.id.buy_check_yes);
        no_btn = findViewById(R.id.buy_check_no);

        buyCostBox = findViewById(R.id.buyCostBox);
        editTextquantity = findViewById(R.id.buyQuantityBox);
        buyCostBox.setText(""); //클릭한 리스트뷰의 아이템으로 이름 변경

        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();

                Toast.makeText(getApplicationContext(), ItemName+get_textquan+"개를 "+get_textcost+"에 구매하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "구매를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}

