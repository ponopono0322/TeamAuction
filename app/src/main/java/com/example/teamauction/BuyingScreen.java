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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyingScreen extends AppCompatActivity {
    private EditText editTextquantity;
    private GameAccountInfo accountInfo;
    private TextView ItemName, ItemInfo, ItemPrice;
    Button yes_btn, no_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_buying);

        Intent intent = getIntent();
        accountInfo = (GameAccountInfo) intent.getSerializableExtra("account_info"); //게임 닉네임, 게임 종류 정보 받아옴
        String Uninum = intent.getExtras().getString("myuninum"); //구매하려는 아이템의 Uninum 받아옴
        String Regnum = intent.getExtras().getString("myregnum"); //구매하려는 아이템의 Reginum 받아옴

        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName();  // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        ItemName = findViewById(R.id.buyingItemName); //게임 아이템 이름 들어갈 공간 생성
        ItemInfo = findViewById(R.id.buyingItemInfo); //게임 아이템 고유정보 들어갈 공간 생성
        ItemPrice = findViewById(R.id.buyCostBox); //게임 아이템 가격정보 들어갈 공간 생성

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
        String purl = "http://ualsgur98.dothome.co.kr/BuyItemInfo.php";
        PHPRequest validateRequest = new PHPRequest(purl, myGameName, Uninum, responseListener);
        RequestQueue queue = Volley.newRequestQueue(BuyingScreen.this);
        queue.add(validateRequest);


        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {  // 서버 통신
                        String cost = jsonResponse.getString("Price");
                        ItemPrice.setText(cost);
                    } else {        // success가 false일 때
                        Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl2 = "http://ualsgur98.dothome.co.kr/CostInfo.php";
        PHPRequest validateRequest2 = new PHPRequest(purl2, myGameName, Regnum, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(BuyingScreen.this);
        queue2.add(validateRequest2);


        //UI 객체생성
        yes_btn = findViewById(R.id.buy_check_yes);
        no_btn = findViewById(R.id.buy_check_no);

        editTextquantity = findViewById(R.id.buyQuantityBox); //게임 아이템 수량 입력할 공간 생성

        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTextQuan = editTextquantity.getText().toString();

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
                PHPRequest validateRequest3 = new PHPRequest(purl3, myGameName, Regnum, getTextQuan, myCharName, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(BuyingScreen.this);
                queue3.add(validateRequest3);

                Toast.makeText(getApplicationContext(), ItemName+getTextQuan+"개를 구매하셨습니다.", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "돈이 부족합니다", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), "전체 개수 이하로 적어주세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AuctionScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });
        //아니오 버튼을 눌렀을때 구매 취소, 경매장 화면으로 다시 돌아감
        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "구매를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), AuctionScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });
    }


}

