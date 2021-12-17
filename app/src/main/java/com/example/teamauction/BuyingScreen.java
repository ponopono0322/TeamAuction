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
    private TextView buyCostBox;
    private EditText editTextquantity;
    private GameAccountInfo accountInfo;
    private TextView ItemName, ItemInfo, ItemPrice;
    ListViewAdapter adapter;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_buying);


        Intent intent = getIntent();
        accountInfo = (GameAccountInfo) intent.getSerializableExtra("account_info");
        String Uninum = intent.getExtras().getString("myuninum");
        String Regnum = intent.getExtras().getString("myregnum");

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName();

        ItemName = findViewById(R.id.buyingItemName);
        ItemInfo = findViewById(R.id.buyingItemInfo);
        ItemPrice = findViewById(R.id.buyCostBox);

      /*  ItemPrice = findViewById(R.id.BuyingItemPrice);
        ItemPrice.setText();*/

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {  // 서버 통신
                        String BuyItemName = jsonResponse.getString("");
                        String BuyItemInfo = jsonResponse.getString("");
                        ItemName.setText(BuyItemName);
                        ItemInfo.setText(BuyItemInfo);
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
        String purl = "http://ualsgur98.dothome.co.kr/BuyItemInfo.php";
        PHPRequest validateRequest = new PHPRequest(purl, myGameName, responseListener);
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
        String purl2 = "http://ualsgur98.dothome.co.kr/.php";
        PHPRequest validateRequest2 = new PHPRequest(purl2, myGameName, Regnum, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(BuyingScreen.this);
        queue2.add(validateRequest2);


        //UI 객체생성
        yes_btn = findViewById(R.id.buy_check_yes);
        no_btn = findViewById(R.id.buy_check_no);

        editTextquantity = findViewById(R.id.buyQuantityBox);
        //buyCostBox.setText(Price); //클릭한 리스트뷰의 아이템으로 가격 변경

        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textquan = editTextquantity.getText().toString();
                /*if(Integer.parseInt(get_textquan)<=Integer.parseInt(Quantity))//사려는 아이템 전체 수량 > 내가 사려는 수량
                {
                    if()//아이템 가격<내 돈
                    {
                        Toast.makeText(getApplicationContext(), ItemName+get_textquan+"개를 "+Price+"에 구매하셨습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if()//아이템 가격>내 돈
                    {
                        Toast.makeText(getApplicationContext(), "돈이 부족합니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Integer.parseInt(Quantity)<=Integer.parseInt(get_textquan)) //사려는 아이템 전체 수량 < 내가 사려는 수량
                {
                    Toast.makeText(getApplicationContext(), "전체 개수 이하로 적어주세요.", Toast.LENGTH_SHORT).show();
                }

                 */
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

