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
import org.w3c.dom.Text;

public class SellingItemScreen extends AppCompatActivity {
    private GameAccountInfo accountInfo;
    private TextView UserCharName;
    private ImageButton backButton;
    private TextView money;
    ListView listview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_item_selling);

        ListViewAdapter adapter; // 어뎁터 선언
        Intent account_info = getIntent(); // 로그인 계정 정보 받기
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName(); // 게임 이름 정보 가져오기
        String myCharName =accountInfo.getCharacterName(); // 게임 캐릭터 닉네임 정보 가져오기

        UserCharName = findViewById(R.id.MyUserCharName); //유저 닉네임 들어갈 공간 생성
        UserCharName.setText(myCharName); //로그인한 계정 캐릭터 이름으로 변경

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.selling_list);
        listview.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("Selling");

                    for (int i = 0; i < games.length(); i++) {

                        JSONObject item = games.getJSONObject(i);
                        String RegisterNumber = item.getString("RegisterNumber");
                        String ItemName = item.getString("ItemName");
                        String Price = item.getString("ItemPrice");
                        String Quantity = item.getString("Quantity");

                        adapter.addAuctionItem(ContextCompat.getDrawable(SellingItemScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName,Price,Quantity,RegisterNumber );
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/Selling.php";
        PHPRequest validateRequest = new PHPRequest( purl, myGameName,myCharName,responseListener);
        RequestQueue queue = Volley.newRequestQueue(SellingItemScreen.this);
        queue.add(validateRequest);

        //내 돈 띄워주기 구현
       Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("Money");

                    JSONObject item = games.getJSONObject(0);
                    String Money1 = item.getString("gameMoney");
                    //DB에서 받아온 내 돈으로 변경
                    money = findViewById(R.id.myMoneyBox);
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
        RequestQueue queue1 = Volley.newRequestQueue(SellingItemScreen.this);
        queue1.add(validateRequest1);


        backButton = findViewById(R.id.back_auctionScreen2); // 뒤로가기 버튼 경매장 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellingItemScreen.this, AuctionScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

        //리스트 뷰에 있는 아이템을 터치하고 판매하기 버튼을 누르면 DeleteScreen창 띄우기
        Button dropingbutton = findViewById(R.id.dropButton);
        dropingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    Intent intent = new Intent(SellingItemScreen.this, DeleteScreen.class);
                    intent.putExtra("account_info", accountInfo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //리스트 뷰에 있는 아이템을 터치하고 판매하기 버튼을 누르면 FixScreen창 띄우기
        Button fixingbutton = findViewById(R.id.fixButton);
        fixingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    Intent intent = new Intent(SellingItemScreen.this, FixScreen.class);
                    intent.putExtra("account_info", accountInfo);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
