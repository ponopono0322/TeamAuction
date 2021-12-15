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

public class SellingItemScreen extends AppCompatActivity {
    private GameAccountInfo accountInfo;
    private TextView UserCharName;
    private ImageButton backButton;
    ListView listview = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_item_selling);

        //ListView listview ;
        ListViewAdapter adapter;

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myGameName =accountInfo.getGameName();
        String myCharName =accountInfo.getCharacterName();

        UserCharName = findViewById(R.id.MyUserCharName);
        UserCharName.setText(myCharName);

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


        ImageButton backButton = findViewById(R.id.back_auctionScreen2); // 뒤로가기 버튼 경매장 화면으로 이동
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellingItemScreen.this, AuctionScreen.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

        //리스트 뷰에 있는 아이템 터치시 DeleteScreen popup창 띄우기
        Button dropingbutton = findViewById(R.id.dropButton);
        dropingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    //ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    //String ItemCode = item.getText();
                    Intent intent = new Intent(SellingItemScreen.this, DeleteScreen.class);
                    //intent.putExtra("data", ItemCode);
                    startActivity(intent);
                    //finish();
                }
            }
        });

        //리스트 뷰에 있는 아이템 터치시 FixScreen popup창 띄우기
        Button fixingbutton = findViewById(R.id.fixButton);
        fixingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    //ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    //String ItemCode = item.getText();
                    Intent intent = new Intent(SellingItemScreen.this, FixScreen.class);
                    //intent.putExtra("data", ItemCode);
                    startActivity(intent);
                    //finish();
                }
            }
        });
    }
}
