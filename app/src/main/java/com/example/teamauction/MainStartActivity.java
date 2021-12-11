package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// 시작 엑티비티
public class MainStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //로그인 버튼으로 화면전환
        Button goLoginButton = findViewById(R.id.go_login);
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainStartActivity.this, MainLoginActivity.class);
                startActivity(intent);
            }
        });
        //텍스트뷰로 화면전환
        Button goSignupButton = findViewById(R.id.go_signup);
        goSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainStartActivity.this, MainSignupActivity.class);
                startActivity(intent);
            }
        });
    }
}