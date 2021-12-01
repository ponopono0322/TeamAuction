package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PublisherLoginActivity extends AppCompatActivity {

    TextView gameName;
    private String gameName_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_login);

        gameName = findViewById(R.id.game_name);
        Intent intent = getIntent();
        gameName_data = intent.getStringExtra("data");
        gameName.setText(gameName_data);

        Button publisher_login_btn = findViewById(R.id.login_publisher_button);
        publisher_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PublisherLoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PublisherLoginActivity.this, CharacterSelectActivity.class);
                GameAccountInfo new_account = new GameAccountInfo();
                new_account.setGameName(gameName_data);
                new_account.setGamePublisherID("tes1");
                new_account.setGamePublisherPW("tes1pw");
                intent.putExtra("game_account", new_account);
                startActivity(intent);
                finish();
            }
        });

        ImageButton back_add_publisher = findViewById(R.id.back_add_account);
        back_add_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublisherLoginActivity.this, AddAccountActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}