package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * 앱 실행시 첫화면
 * 로그인과 회원가입을 선택할 수 있는 액티비티
 */
public class MainStartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button goLoginButton = findViewById(R.id.go_login); // 로그인화면 전환 버튼
        goLoginButton.setOnClickListener(new View.OnClickListener() { // 로그인 전환 버튼 클릭시 이벤트 발생
            @Override
            public void onClick(View view) {    // 버튼에 대한 함수 재정의
                // Intent로 엑티비티 간 데이터 전송
                Intent intent = new Intent(MainStartActivity.this, MainLoginActivity.class);
                startActivity(intent);  // 로그인 엑티비티 실행
            }
        });

        Button goSignupButton = findViewById(R.id.go_signup); // 회원가입 화면 전환 버튼
        goSignupButton.setOnClickListener(new View.OnClickListener() { // 회원가입 버튼 클릭시 이벤트 발생
            @Override
            public void onClick(View view) {    // 버튼에 대한 함수 재정의
                // Intent로 엑티비티 간 데이터 전송
                Intent intent = new Intent(MainStartActivity.this, MainSignupActivity.class);
                startActivity(intent);  // 회원가입 엑티비티 실행
            }
        });
    }
}