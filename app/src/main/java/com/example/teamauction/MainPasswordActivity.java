package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
/**
 * 비밀번호 찾는 액티비티
 * 비밀번호가 기억나지 않는 경우 자신이 등록했던 이메일로 비밀번호를 재발급 받을 수 있다
 */
public class MainPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ImageButton back_button = findViewById(R.id.back_password);     // 뒤로가기 버튼
        back_button.setOnClickListener(new View.OnClickListener() {     // 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // MainPasswordActivity에서 MainLoginActivity으로 가는 intent 생성
                Intent intent = new Intent(MainPasswordActivity.this, MainLoginActivity.class);
                startActivity(intent);  // intent 실행
                finish();               // 현재 엑티비티 종료
            }
        });

        Button send_email = findViewById(R.id.find_pw_button);          // 이메일 전송 버튼
        send_email.setOnClickListener(new View.OnClickListener() {      // 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // MainPasswordActivity에서 MainLoginActivity으로 가는 intent 생성
                Intent intent = new Intent(MainPasswordActivity.this, MainLoginActivity.class);
                startActivity(intent);  // intent 실행
                finish();               // 현재 엑티비티 종료
            }
        });
    }
}