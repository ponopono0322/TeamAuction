package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FixScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;
    private GameAccountInfo accountInfo;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_fix);

        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        //UI 객체생성
        yes_btn = findViewById(R.id.fix_check_yes);
        no_btn = findViewById(R.id.fix_check_no);

        editTextcost = findViewById(R.id.fixCostBox);  //수정하려는 가격 입력하는 공간 생성
        editTextquantity = findViewById(R.id.fixQuantityBox);  //수정하려는 수량 입력하는 공간 생성


        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();

                Toast.makeText(getApplicationContext(), "판매 중인 아이템을 수정하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FixScreen.this, SellingItemScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), get_textcost, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "아이템 수정을 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FixScreen.this, SellingItemScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });
    }
}
