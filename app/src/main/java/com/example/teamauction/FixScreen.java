package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FixScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;
    private GameAccountInfo accountInfo;
    private TextView ItemName, ItemInfo;
    Button yes_btn, no_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_fix);

        Intent intent = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) intent.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기
        String Uninum = intent.getExtras().getString("myuninum"); //구매하려는 아이템의 Uninum 받아옴
        String Regnum = intent.getExtras().getString("myregnum"); //구매하려는 아이템의 Reginum 받아옴

        //UI 객체생성
        yes_btn = findViewById(R.id.fix_check_yes);
        no_btn = findViewById(R.id.fix_check_no);

        editTextcost = findViewById(R.id.fixCostBox);  //수정하려는 가격 입력하는 공간 생성
        editTextquantity = findViewById(R.id.fixQuantityBox);  //수정하려는 수량 입력하는 공간 생성

        ItemName = findViewById(R.id.buyingItemName); //게임 아이템 이름 들어갈 공간 생성
        ItemInfo = findViewById(R.id.buyingItemInfo); //게임 아이템 고유정보 들어갈 공간 생성

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) { // 서버 통신
                        String BuyItemName = jsonResponse.getString("ItemName");
                        String BuyItemInfo = jsonResponse.getString("ItemInfo");
                        ItemName.setText(BuyItemName);
                        ItemInfo.setText(BuyItemInfo);
                    } else {//success가 false일 때
                        Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {// 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/.php";
        PHPRequest validateRequest = new PHPRequest(purl, myGameName, Uninum, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FixScreen.this);
        queue.add(validateRequest);

        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();
                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {  // 서버 통신

                            } else { // success가 false일 때
                                Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                                    return;
                            }
                        } catch (JSONException e) { // 접속 오류가 난 것이라면
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                String purl3 = "http://ualsgur98.dothome.co.kr/.php";
                PHPRequest validateRequest3 = new PHPRequest(purl3, myGameName, Regnum, get_textcost, get_textquan, myCharName, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(FixScreen.this);
                queue3.add(validateRequest3);

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
