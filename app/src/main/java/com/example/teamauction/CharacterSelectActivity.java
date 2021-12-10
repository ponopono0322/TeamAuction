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

public class CharacterSelectActivity extends AppCompatActivity {
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
        AuctionAdapter adapter;

        // Adapter 생성
        adapter = new AuctionAdapter() ;

        // 리스트뷰 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addGameItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_1","") ;
        // 두 번째 아이템 추가.
        adapter.addGameItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_2","") ;
        // 세 번째 아이템 추가.
        adapter.addGameItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_3","") ;
        // 네 번째 아이템 추가.
        adapter.addGameItem(ContextCompat.getDrawable(this, R.drawable.ic_baseline_account_box_24),
                "Character_4","") ;

        Intent game_account_info = getIntent();
        got_data = (GameAccountInfo) game_account_info.getSerializableExtra("game_account");

        Button selectCharacter = findViewById(R.id.select_game_character);
        selectCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    String CharacterName = item.getText();
                    Toast.makeText(CharacterSelectActivity.this, CharacterName + " 을 선택했습니다" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CharacterSelectActivity.this, MainActivity.class);
                    got_data.setCharacterName(CharacterName);
                    intent.putExtra("new_account", got_data);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(CharacterSelectActivity.this, "캐릭터를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}