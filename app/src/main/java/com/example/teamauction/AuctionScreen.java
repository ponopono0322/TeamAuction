package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * 경매장 화면
 * 메인 화면에서 계정을 선택 한 뒤 화면
 * 경매장에서 아이템을 검색, 구매 할 아이템을 선택, 내 아이템 화면으로 이동, 판매중인 아이이템 화면으로 이동 할 수 있게 하는 액티비티
 */
public class AuctionScreen extends AppCompatActivity {
    private Button moveSellingButton, moveMyitemButton, buyingbutton; // 판매중인 아이템 화면 버튼, 내 아이템 화면 버튼, 구매하기 버튼
    private ImageButton backButton; // 뒤로가기 버튼
    private TextView UserCharName; // 유저 게임 닉네임 텍스트뷰
    private GameAccountInfo accountInfo; // 계정 정보에 대한 객체

    ListView listview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_auction);
        ListViewAdapter adapter; // 어뎁터 선언
        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info"); // account_info라는 key로 GameAccountInfo 객체를 받아옴
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        UserCharName = findViewById(R.id.UserCharName); //유저 닉네임 들어갈 공간 생성
        UserCharName.setText(myCharName); //로그인한 계정 캐릭터 이름으로 변경

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.auctionList);
        listview.setAdapter(adapter);

        //경매장 리스트 정보를 DB에서 받아와 리스트뷰에 보여줌
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { // onResponse 함수 재정의
                try {  // 만약 반응이 왔고 정상적이라면,
                    JSONObject jsonResponse = new JSONObject(response); // json 형태로 받기 위해 객체 준비
                    JSONArray games = jsonResponse.getJSONArray("GameAuction"); // GameAuction key 값으로 배열을 받음
                    for (int i = 0; i < games.length(); i++) { // games의 길이만큼 반복문 수행
                        JSONObject item = games.getJSONObject(i);  // games의 데이터를 가져옴
                        String UniNumber = item.getString("UniNum"); // UniNum 데이터 저장
                        String RegisterNumber = item.getString("RegisterNumber"); // RegisterNumber 데이터 저장
                        String ItemName = item.getString("ItemName"); // ItemName 데이터 저장
                        String Price  = item.getString("Price"); // Price 데이터 저장
                        adapter.addAuctionItem(ContextCompat.getDrawable(AuctionScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName,Price,UniNumber,RegisterNumber); //리스트뷰에 있는 아이템의 이름, 가격, UniNumber, RegisterNumber 정보 저장
                    }
                    adapter.notifyDataSetChanged(); // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace(); // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/GameAuction.php";  // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest = new PHPRequest( purl, myGameName,responseListener);
        RequestQueue queue = Volley.newRequestQueue(AuctionScreen.this); // 큐를 생성
        queue.add(validateRequest); // 큐에 추가


        //EditText(검색창)에 검색된 값을 받아 함수를 호출
        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() { // 검색창 입력 이벤트 생성
            @Override
            public void afterTextChanged(Editable edit) { // afterTextChanged 함수 재정의
                String filterText = edit.toString() ; // 입력값 받기
                ((ListViewAdapter)listview.getAdapter()).getFilter().filter(filterText); //ListView를 통하지 않고 Adapter로부터 직접 Filter 객체의 참조를 가져와서 filter() 함수를 호출 하여 텍스트 팝업이 안뜨게 함
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //리스트 뷰에 있는 아이템을 터치하고 구매하기 버튼을 누르면 BuyingScreen 창 띄우기
        buyingbutton = findViewById(R.id.buyButton);
        buyingbutton.setOnClickListener(new View.OnClickListener() { // 구매하기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) { // onClick 함수 재정의
                int pos = listview.getCheckedItemPosition(); // 리스트뷰 위치데이터 변수
                if (pos > -1) { // pos는 0이상이므로 그 이상일 때만 작동
                    ListViewItem item = (ListViewItem) adapter.getItem(pos); //터치한 아이템의 위치값 받아옴
                    String uninumber = item.getUninumber(); //아이템의 Uninumber 받아옴
                    String regnum = item.getRegnumber(); //아이템의 RegisterNumber 받아옴
                    // AuctionScreen에서 BuyingScreen으로 가는 intent 준비
                    Intent intent = new Intent(AuctionScreen.this, BuyingScreen.class);
                    intent.putExtra("account_info", accountInfo); // 구매하기 화면에 계정 정보 보내줌
                    intent.putExtra("myuninum", uninumber); // 구매하기 화면에  Uninumber 보내줌
                    intent.putExtra("myregnum", regnum); // 구매하기 화면에 RegisterNumber 보내줌
                    startActivity(intent); // BuyingScreen 실행
                    finish(); // 현재 엑티비티 종료
                }
            }
        });

        backButton = findViewById(R.id.back_button); // 뒤로가기 버튼 계정 선택 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() { //뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {  // onClick 함수 재정의
                // AuctionScreen에서 MainActivity으로 가는 intent 정의
                Intent intent = new Intent(AuctionScreen.this, MainActivity.class);
                intent.putExtra("account_info", accountInfo); // intent에 데이터를 넣음
                startActivity(intent); // intent 실행
                finish(); // 현재 엑티비티 종료
            }
        });

        //판매중, 내 아이템 이동버튼 구현
        moveSellingButton = (Button)findViewById(R.id.moveSellingButton); // 판매중인 아이템으로 이동버튼 생성
        moveMyitemButton = (Button)findViewById(R.id.moveMyitemButton); // 내 아이템으로 이동버튼 생성

        moveSellingButton.setOnClickListener(new View.OnClickListener(){ // 판매중인 아이템 이동 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View v) { // onClick 함수 재정의
                // AuctionScreen에서 SellingScreen으로 가는 intent 준비
                Intent intent = new Intent(getApplicationContext(), SellingItemScreen.class);
                intent.putExtra("account_info", accountInfo); // intent에 게임 계정 데이터 추가
                startActivity(intent);  // SellingItemScreen 실행
                finish(); // 현재 엑티비티 종료
            }
        });
        moveMyitemButton.setOnClickListener(new View.OnClickListener(){  // 내 아이템 이동 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View v) {
                // AuctionScreen에서 ItemCheckScreen으로 가는 intent 준비
                Intent intent = new Intent(getApplicationContext(), ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo); // intent에 게임 계정 데이터 추가
                startActivity(intent); // ItemCheckScreen 실행
                finish(); // 현재 엑티비티 종료
            }
        });

    }
}