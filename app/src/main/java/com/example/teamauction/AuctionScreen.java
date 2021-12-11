package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

    //경매장 리스트 만드는중
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_auction);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("GameAuctionList");

                    for (int i = 0; i < games.length(); i++) {

                        JSONObject item = games.getJSONObject(i);
                        String GameName = item.getString("GameName");
                        String ItemName = item.getString("ItemName");
                        String ItemQuantity = item.getString("ItemQuantity");
                        String ItemPrice  = item.getString("ItemPrice");
                        String GameID = item.getString("GameID");
                        adapter.addItem(ContextCompat.getDrawable(AuctionScreen.this,
                                R.drawable.ic_baseline_account_box_24),ItemName,ItemPrice);

                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };

        String purl = "http://ualsgur98.dothome.co.kr/GameAuctionList.php";
        PHPRequest validateRequest = new PHPRequest( purl, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AuctionScreen.this);
        queue.add(validateRequest);

        // 첫 번째 아이템 추가.
     //   adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
            //   "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
       // adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
          //      "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
           //     "Ind", "Assignment Ind Black 36dp") ;

        //리스트 뷰에 있는 아이템 터치시 BuyingScreen popup창 띄우기
        Button buyingbutton = findViewById(R.id.buyButton);
        buyingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    //ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    //String ItemCode = item.getText();
                    Intent intent = new Intent(AuctionScreen.this, BuyingScreen.class);
                    //intent.putExtra("data", ItemCode);
                    startActivity(intent);
                    //finish();
                }
            }
        });

         //판매중, 내 아이템 이동버튼 구현
        moveSellingButton = (Button)findViewById(R.id.moveSellingButton); // 판매중인 아이템으로 이동버튼
        moveMyitemButton = (Button)findViewById(R.id.moveMyitemButton); // 내 아이템으로 이동버튼

        moveSellingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SellingItemScreen.class);
                startActivity(intent);
            }
        });
        moveMyitemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemCheckScreen.class);
                startActivity(intent);
            }
        });

    }
}