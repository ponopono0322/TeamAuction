package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameChActivity extends AppCompatActivity {

    private GameAccountInfo accountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        ImageButton CanalAdda = findViewById(R.id.cancel_account_add);
        CanalAdda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameChActivity.this, GameLoginActivity.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

        ListView listview;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray Ids = jsonResponse.getJSONArray("CharacterList");
                    for (int i = 0; i < Ids.length(); i++) {
                        JSONObject item = Ids.getJSONObject(i);
                        String characterName = item.getString("gameNickname");
                        String characterMony = item.getString("gameMoney");
                        adapter.addItem(ContextCompat.getDrawable(GameChActivity.this,
                                R.drawable.ic_baseline_account_box_24), characterName, characterMony);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/CharacterList.php";
        PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getGameName(),
                accountInfo.getGamePublisherID(), accountInfo.getGamePublisherPW(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(GameChActivity.this);
        queue.add(validateRequest);

        Button selectCharacter = findViewById(R.id.select_game_character);
        selectCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    accountInfo.setCharacterName(item.getText());

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success) {
                                    Toast.makeText(getApplicationContext(), "계정이 정상적으로 연동되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(GameChActivity.this, MainActivity.class);
                                    accountInfo.resetData();
                                    intent.putExtra("account_info", accountInfo);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "서버 접속 오류", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) { // 접속 오류가 난 것이라면
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    String purl = "http://ualsgur98.dothome.co.kr/MyGameAndCharacter.php";
                    PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getLoginID(), accountInfo.getLoginPW(), accountInfo.getGameName(),
                            accountInfo.getGamePublisherID(), accountInfo.getGamePublisherPW(), accountInfo.getCharacterName(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(GameChActivity.this);
                    queue.add(validateRequest);
                }
                else {
                    Toast.makeText(GameChActivity.this, "캐릭터를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}