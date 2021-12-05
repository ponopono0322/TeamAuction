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
                    intent.putExtra("data", gameName);//게임의 이름과 함께 그 게임 자체의 로그인 창으로 가야한다.
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