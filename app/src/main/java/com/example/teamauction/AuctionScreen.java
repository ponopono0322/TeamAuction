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


public class AuctionScreen extends AppCompatActivity {
    private Button moveSellingButton;
    private Button moveMyitemButton;
    private ImageButton backButton;
    private TextView UserCharName;
    private GameAccountInfo accountInfo;

    ListView listview = null;
    //경매장 리스트 만드는중
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_auction);

        ListViewAdapter adapter;

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName();
        String myCharName =accountInfo.getCharacterName();


        UserCharName = findViewById(R.id.UserCharName);
        UserCharName.setText(myCharName); //로그인한 계정 캐릭터 이름으로 변경

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.auctionList);
        listview.setAdapter(adapter);

        // 리스트 뷰 아이템 추가.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("GameAuction");


                    for (int i = 0; i < games.length(); i++) {

                        JSONObject item = games.getJSONObject(i);
                        String UniNumber = item.getString("UniNum");
                        String RegisterNumber = item.getString("RegisterNumber");
                        String ItemName = item.getString("ItemName");
                        String Quantity = item.getString("Quantity");
                        String Price  = item.getString("Price");
                        adapter.addAuctionItem(ContextCompat.getDrawable(AuctionScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName,Price,UniNumber,RegisterNumber);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/GameAuction.php";
        PHPRequest validateRequest = new PHPRequest( purl, myGameName,responseListener);
        RequestQueue queue = Volley.newRequestQueue(AuctionScreen.this);
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

                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl1 = "http://ualsgur98.dothome.co.kr/Money.php";
        PHPRequest validateRequest1 = new PHPRequest( purl1, myGameName,myCharName,responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(AuctionScreen.this);
        queue1.add(validateRequest1);

        //EditText(검색창)에 검색된 값을 받아 함수를 호출
        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                /*
                if (filterText.length() > 0) {
                    listview.setFilterText(filterText) ;
                } else {
                    listview.clearTextFilter() ;
                }
                 */
                ((ListViewAdapter)listview.getAdapter()).getFilter().filter(filterText); //ListView를 통하지 않고 Adapter로부터 직접 Filter 객체의 참조를 가져와서 filter() 함수를 호출 하여 텍스트 팝업이 안뜨게 함
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //리스트 뷰에 있는 아이템 터치시 BuyingScreen 창 띄우기
        Button buyingbutton = findViewById(R.id.buyButton);
        buyingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    String cost = item.getMassage();
                    String uninumber = item.getUninumber();
                    String regnum = item.getRegnumber();
                    Intent intent = new Intent(AuctionScreen.this, BuyingScreen.class);
                    intent.putExtra("account_info", accountInfo);
                    intent.putExtra("cost", cost);
                    intent.putExtra("myuninum", uninumber);
                    intent.putExtra("myregnum", regnum);
                    startActivity(intent);
                    finish();
                }
            }
        });

        ImageButton backButton = findViewById(R.id.back_button); // 뒤로가기 버튼 계정 선택 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuctionScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //판매중, 내 아이템 이동버튼 구현
        moveSellingButton = (Button)findViewById(R.id.moveSellingButton); // 판매중인 아이템으로 이동버튼 정의
        moveMyitemButton = (Button)findViewById(R.id.moveMyitemButton); // 내 아이템으로 이동버튼 정의

        moveSellingButton.setOnClickListener(new View.OnClickListener(){ // 판매중인 아이템으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SellingItemScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });
        moveMyitemButton.setOnClickListener(new View.OnClickListener(){  // 내 아이템으로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemCheckScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

    }
}