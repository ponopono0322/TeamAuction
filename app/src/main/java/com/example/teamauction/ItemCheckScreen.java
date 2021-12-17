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

public class ItemCheckScreen extends AppCompatActivity {
    private GameAccountInfo accountInfo;
    private TextView UserCharName;
    private ImageButton backButton;
    private TextView money;
    private Button sellingbutton;

    ListView listview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_check_item);
        ListViewAdapter adapter; // 어뎁터 선언
        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
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
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("UserInfo");
                    for (int i = 0; i < games.length(); i++) {
                        JSONObject item = games.getJSONObject(i);
                        String UniNum = item.getString("UniNum");
                        String ItemName = item.getString("ItemName");
                        String ItemQuantity = item.getString("ItemQuantity");
                        //String Money = item.getString("Money");
                        String Regnum = item.getString("RegisterNumber");
                        adapter.addAuctionItem(ContextCompat.getDrawable(ItemCheckScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName, ItemQuantity, UniNum,Regnum);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/UserInfo.php";
        PHPRequest validateRequest = new PHPRequest( purl, myGameName,myCharName,responseListener);
        RequestQueue queue = Volley.newRequestQueue(ItemCheckScreen.this);
        queue.add(validateRequest);

        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("Money");

                        JSONObject item = games.getJSONObject(0);
                        String Money1 = item.getString("gameMoney");

                        //DB에서 받아온 내 돈으로 변경
                        money = findViewById(R.id.moneyBox);
                        money.setText(Money1);

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl1 = "http://ualsgur98.dothome.co.kr/Money.php";
        PHPRequest validateRequest1 = new PHPRequest( purl1, myGameName,myCharName,responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(ItemCheckScreen.this);
        queue1.add(validateRequest1);



        backButton = findViewById(R.id.back_auctionScreen); // 뒤로가기 버튼 경매장 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemCheckScreen.this, AuctionScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });



        //리스트 뷰에 있는 아이템을 터치하고 판매하기 버튼을 누르면 SellingScreen창 띄우기
        sellingbutton = findViewById(R.id.sellButton);
        sellingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    Intent intent = new Intent(ItemCheckScreen.this, SellingScreen.class);
                    intent.putExtra("account_info", accountInfo); // 구매하기 화면에 계정 정보 보내줌
                    //intent.putExtra("myuninum", uninumber); // 구매하기 화면에  Uninumber 보내줌
                    //intent.putExtra("myregnum", regnum); // 구매하기 화면에 RegisterNumber 보내줌
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
