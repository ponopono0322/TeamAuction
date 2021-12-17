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

/**
 * 판매하기 화면
 * 내 아이템 화면에서 리스트뷰 목록중 판매할 아이템을 누르고 판매하기 버튼을 클릭한 뒤 화면
 * 판매하기를 실질적으로 할 수 있게 하는 액티비티
 */

public class SellingScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;  //가격, 수량을 입력할 수 있는 에티트 텍스트 뷰
    private GameAccountInfo accountInfo; // 계정 정보에 대한 객체
    private TextView ItemName, ItemInfo; // 아이템 이름, 정보가 들어갈 텍스트 뷰
    Button yes_btn, no_btn; // 확인, 취소 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_selling);

        Intent intent = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) intent.getSerializableExtra("account_info"); // intent라는 key로 GameAccountInfo 객체를 받아옴
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기
        String Uninum = intent.getExtras().getString("myuninum"); //구매하려는 아이템의 Uninum 받아옴
        String Regnum = intent.getExtras().getString("myregnum"); //구매하려는 아이템의 Reginum 받아옴

        //UI 객체생성
        yes_btn = findViewById(R.id.sell_check_yes);
        no_btn = findViewById(R.id.sell_check_no);

        ItemName = findViewById(R.id.SellingItemName); //게임 아이템 이름 들어갈 공간 생성
        ItemInfo = findViewById(R.id.SellingItemInfo); //게임 아이템 고유정보 들어갈 공간 생성

        editTextcost = findViewById(R.id.sellCostBox); //판매하려는 가격 입력하는 공간 생성
        editTextquantity = findViewById(R.id.sellQuantityBox); //판매하려는 수량 입력하는 공간 생성

        //판매하려는 아이템 정보를 DB에서 받아와 화면에 보여줌
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // onResponse 함수 재정의
                try { // 만약 반응이 왔고 정상적이라면
                    JSONObject jsonResponse = new JSONObject(response); // json 형태로 받기 위해 객체 준비
                    boolean success = jsonResponse.getBoolean("success"); // success라는 key 값으로 boolean value를 받음
                    if (success) { // success가 참이라면
                        String BuyItemName = jsonResponse.getString("ItemName");  // ItemName 데이터 저장
                        String BuyItemInfo = jsonResponse.getString("ItemInfo"); // ItemInfo 데이터 저장
                        ItemName.setText(BuyItemName); //판매하려는 아이템 이름으로 변경
                        ItemInfo.setText(BuyItemInfo); //판매하려는 아이템 정보로 변경
                    } else {//success가 false일 때
                        // 토스트 메세지를 띄워줌
                        Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (JSONException e) {// 접속 오류가 난 것이라면
                    e.printStackTrace(); // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/SellItemInfo.php"; // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest = new PHPRequest(purl, myGameName, Uninum, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SellingScreen.this); // 큐를 생성
        queue.add(validateRequest); // 큐에 추가

        //판매 버튼을 눌렀을때 값 저장, 내 아이템 화면으로 돌아감
        yes_btn.setOnClickListener(new View.OnClickListener() { // 판매 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString(); //입력받은 가격의 값을 저장
                String get_textquan = editTextquantity.getText().toString(); //입력받은 수량의 값을 저장

                //판매하려는 아이템 정보 DB에 보냄
                Response.Listener<String> responseListener3 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // onResponse 함수 재정의
                        try { // 만약 반응이 왔고 정상적이라면
                            JSONObject jsonResponse = new JSONObject(response); // json 형태로 보내기 위해 객체 준비
                            boolean success = jsonResponse.getBoolean("success"); // success라는 key 값으로 boolean value를 받음
                            if (success) {  // success가 참이라면

                            } else { // success가 false일 때
                                // 토스트 메세지를 띄워줌
                                Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) { // 접속 오류가 난 것이라면
                            e.printStackTrace();  // 오류 추적
                            // 토스트 메세지를 띄워줌
                            Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                String purl3 = "http://ualsgur98.dothome.co.kr/.php"; // 통신할 php 주소
                // 데이터 전송을 위한 데이터 세팅
                PHPRequest validateRequest3 = new PHPRequest(purl3, myGameName, Regnum, get_textcost, get_textquan, myCharName, responseListener3);
                RequestQueue queue3 = Volley.newRequestQueue(SellingScreen.this); // 큐를 생성
                queue3.add(validateRequest3); // 큐에 추가

                //Toast.makeText(getApplicationContext(), get_textcost, Toast.LENGTH_SHORT).show();
                // SellingScreen에서 ItemCheckScreen으로 가는 intent 준비
                Intent intent = new Intent(SellingScreen.this, ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo);  // intent에 게임 계정 데이터 추가
                startActivity(intent); // ItemCheckScreen 실행
                finish(); // 현재 엑티비티 종료
            }
        });

        //아니오 버튼을 눌렀을때 취소, 내 아이템 화면으로 돌아감
        no_btn.setOnClickListener(new View.OnClickListener() {  //아니오 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {
                // 토스트 메세지를 띄워줌
                Toast.makeText(getApplicationContext(), "판매를 취소하셨습니다.", Toast.LENGTH_SHORT).show();
                // SellingScreen에서 ItemCheckScreen으로 가는 intent 준비
                Intent intent = new Intent(SellingScreen.this, ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo); // intent에 게임 계정 데이터 추가
                startActivity(intent); // ItemCheckScreen 실행
                finish(); // 현재 엑티비티 종료
            }
        });
    }


}

