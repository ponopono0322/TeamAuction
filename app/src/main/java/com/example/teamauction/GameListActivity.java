package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class GameListActivity extends AppCompatActivity {

    private ListViewAdapter adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        ImageButton go_main = findViewById(R.id.back_account);
        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview_choose_game);
        listview.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray games = jsonResponse.getJSONArray("GameList");
                    for (int i = 0; i < games.length(); i++) {
                        JSONObject item = games.getJSONObject(i);
                        String gameName = item.getString("GameName");
                        //String gameNum = item.getString("GameNum");
                        adapter.addItem(ContextCompat.getDrawable(GameListActivity.this,
                                R.drawable.ic_baseline_account_box_24), gameName, "name");
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/GameList.php";
        PHPRequest validateRequest = new PHPRequest( purl, responseListener);
        RequestQueue queue = Volley.newRequestQueue(GameListActivity.this);
        queue.add(validateRequest);

        Button publisher_login = findViewById(R.id.login_button_publisher);
        publisher_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    String gameName = item.getText();
                    Intent intent = new Intent(GameListActivity.this, GameLoginActivity.class);
                    intent.putExtra("data", gameName);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(GameListActivity.this, "게임을 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}