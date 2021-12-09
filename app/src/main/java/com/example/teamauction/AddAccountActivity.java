package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        ImageButton go_main = findViewById(R.id.back_account);
        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAccountActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ListView listview ;
        CustomChoiceListViewAdapter adapter;

        // Adapter 생성
        adapter = new CustomChoiceListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray gamelist = jsonResponse.getJSONArray("gamelist");
                    if (success) {
                        for(int i=0; i<gamelist.length(); i++) {
                            JSONObject item = gamelist.getJSONObject(i);
                            String gameName = item.getString("gamename");
                            adapter.addItem(ContextCompat.getDrawable(AddAccountActivity.this, R.drawable.ic_baseline_account_box_24),
                                    gameName);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/GameList.php";
        RequestPHP validateRequest = new RequestPHP( purl, responseListener);
        RequestQueue queue = Volley.newRequestQueue(AddAccountActivity.this);
        queue.add(validateRequest);


        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "MapleStory");
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "DF");
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "LostArk");

        Button publisher_login = findViewById(R.id.login_button_publisher);
        publisher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    String gameName = item.getText();
                    Intent intent = new Intent(AddAccountActivity.this, PublisherLoginActivity.class);
                    intent.putExtra("data", gameName);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(AddAccountActivity.this, "게임을 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}