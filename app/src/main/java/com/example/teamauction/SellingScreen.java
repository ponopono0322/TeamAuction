package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SellingScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;
    private GameAccountInfo accountInfo;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_selling);

        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        //UI 객체생성
        yes_btn = findViewById(R.id.sell_check_yes);
        no_btn = findViewById(R.id.sell_check_no);

        editTextcost = findViewById(R.id.sellCostBox);
        editTextquantity = findViewById(R.id.sellQuantityBox);


        //예 버튼을 눌렀을때 값 저장, 내 아이템 화면으로 돌아감
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();


                //Toast.makeText(getApplicationContext(), get_textcost, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SellingScreen.this, ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

        //아니오 버튼을 눌렀을때 취소, 내 아이템 화면으로 돌아감
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "판매를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SellingScreen.this, ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });
    }


}

