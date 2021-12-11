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
    private GameAccountInfo got_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        ImageButton CanalAdda = findViewById(R.id.cancel_account_add);
        CanalAdda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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
                    JSONArray games = jsonResponse.getJSONArray(got_data.getGameName());
                    for (int i = 0; i < games.length(); i++) {
                        JSONObject item = games.getJSONObject(i);
                        String characterName = item.getString("gameNickname");
                        //String gameNum = item.getString("GameNum");
                        adapter.addItem(ContextCompat.getDrawable(GameChActivity.this,
                                R.drawable.ic_baseline_account_box_24), characterName, "name");
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        Intent game_account_info = getIntent();
        got_data = (GameAccountInfo) game_account_info.getSerializableExtra("game_account");
        String purl = "http://ualsgur98.dothome.co.kr/CharacterList.php";
        PHPRequest validateRequest = new PHPRequest( purl, got_data.getGameName(), got_data.getGamePublisherID(), got_data.getGamePublisherPW(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(GameChActivity.this);
        queue.add(validateRequest);


        /*
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_1","") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_2","") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_3","") ;
        // 네 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_4","") ;
        */


        Button selectCharacter = findViewById(R.id.select_game_character);
        selectCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    String CharacterName = item.getText();
                    Toast.makeText(GameChActivity.this, CharacterName + " 을 선택했습니다" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(GameChActivity.this, MainActivity.class);
                    got_data.setCharacterName(CharacterName);
                    intent.putExtra("new_account", got_data);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(GameChActivity.this, "캐릭터를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}