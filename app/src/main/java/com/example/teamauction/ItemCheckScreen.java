package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 내 아이템 화면
 * 경매장 화면에서 내 아이템 아이템 버튼을 클릭한 뒤 화면
 * 내 아이템 중 판매 할 아이템을 선택할 수 있게 하는 액티비티
 */

public class ItemCheckScreen extends AppCompatActivity {
    private GameAccountInfo accountInfo; // 계정 정보에 대한 객체
    private TextView UserCharName; // 유저 게임 닉네임 텍스트뷰
    private ImageButton backButton; // 뒤로가기 버튼
    private TextView money; // 유저 게임 돈 텍스트뷰
    private Button sellingbutton; //판매하기 버튼

    ListView listview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_check_item);
        ListViewAdapter adapter; // 어뎁터 선언
        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info"); // account_info라는 key로 GameAccountInfo 객체를 받아옴
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        UserCharName = findViewById(R.id.MyCharName); //유저 닉네임 들어갈 공간 생성
        UserCharName.setText(myCharName); //로그인한 계정 캐릭터 이름으로 변경

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.my_item_list);
        listview.setAdapter(adapter);

        //내 아이템 정보를 DB에서 받아와 리스트뷰에 보여줌
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // onResponse 함수 재정의
                try { // 만약 반응이 왔고 정상적이라면,
                    JSONObject jsonResponse = new JSONObject(response); // json 형태로 받기 위해 객체 준비
                    JSONArray games = jsonResponse.getJSONArray("UserInfo"); // Selling 값으로 배열을 받음
                    for (int i = 0; i < games.length(); i++) { // games의 길이만큼 반복문 수행
                        JSONObject item = games.getJSONObject(i); // games의 데이터를 가져옴
                        String UniNum = item.getString("UniNum");  // UniNum 데이터 저장
                        String ItemName = item.getString("ItemName"); // ItemName 데이터 저장
                        String ItemQuantity = item.getString("ItemQuantity"); // ItemQuantity 데이터 저장
                        adapter.addAuctionItem(ContextCompat.getDrawable(ItemCheckScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName, ItemQuantity, UniNum,""); //리스트뷰에 있는 아이템의 이름, 가격, UniNumber, RegisterNumber 정보 저장
                    }
                    adapter.notifyDataSetChanged(); // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace(); // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/UserInfo.php"; // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest = new PHPRequest( purl, myGameName,myCharName,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ItemCheckScreen.this); // 큐를 생성
        queue.add(validateRequest); // 큐에 추가

        //내 돈 띄워주기 구현
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {  // onResponse 함수 재정의
                try { // 만약 반응이 왔고 정상적이라면
                    JSONObject jsonResponse = new JSONObject(response); // json 형태로 받기 위해 객체 준비
                    JSONArray games = jsonResponse.getJSONArray("Money"); // Money 값으로 배열을 받음
                    JSONObject item = games.getJSONObject(0); // games의 데이터를 가져옴
                    String Money1 = item.getString("gameMoney");  // gameMoney 데이터 저장
                    //DB에서 받아온 내 돈으로 변경
                    money = findViewById(R.id.moneyBox); //돈이 들어갈 공간 생성
                    money.setText(Money1); //유저의 돈으로 변경
                    adapter.notifyDataSetChanged(); // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace(); // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl1 = "http://ualsgur98.dothome.co.kr/Money.php"; // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest1 = new PHPRequest( purl1, myGameName,myCharName,responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(ItemCheckScreen.this); // 큐를 생성
        queue1.add(validateRequest1); // 큐에 추가



        backButton = findViewById(R.id.back_auctionScreen); // 뒤로가기 버튼 경매장 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {  // onClick 함수 재정의
                // ItemCheckScreen에서 AuctionScreen으로 가는 intent 준비
                Intent intent = new Intent(ItemCheckScreen.this, AuctionScreen.class);
                intent.putExtra("account_info", accountInfo); // 경매장 화면에 계정 정보 보내줌
                startActivity(intent);  // AuctionScreen 실행
                finish(); // 현재 엑티비티 종료
            }
        });



        //리스트 뷰에 있는 아이템을 터치하고 판매하기 버튼을 누르면 SellingScreen창 띄우기
        sellingbutton = findViewById(R.id.sellButton);
        sellingbutton.setOnClickListener(new View.OnClickListener() {  // 판매하기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) { // onClick 함수 재정의
                int pos = listview.getCheckedItemPosition(); // 리스트뷰 위치데이터 변수
                if (pos > -1) {  // pos는 0이상이므로 그 이상일 때만 작동
                    ListViewItem item = (ListViewItem) adapter.getItem(pos); //터치한 아이템의 위치값 받아옴
                    String uninumber = item.getUninumber(); //아이템의 Uninumber 받아옴
                    String regnum = item.getRegnumber(); //아이템의 RegisterNumber 받아옴
                    // ItemCheckScreen에서 SellingScreen으로 가는 intent 준비
                    Intent intent = new Intent(ItemCheckScreen.this, SellingScreen.class);
                    intent.putExtra("account_info", accountInfo); // 구매하기 화면에 계정 정보 보내줌
                    intent.putExtra("myuninum", uninumber); // 구매하기 화면에  Uninumber 보내줌
                    startActivity(intent); // SellingScreen 실행
                    finish(); // 현재 엑티비티 종료
                }
            }
        });
    }
}
