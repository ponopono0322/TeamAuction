package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
// 비밀번호 찾는 액티비티
public class MainPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ImageButton back_button = findViewById(R.id.back_password);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPasswordActivity.this, MainLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button send_email = findViewById(R.id.find_pw_button);
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPasswordActivity.this, MainLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}