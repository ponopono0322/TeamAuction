package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BuyingScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_buying);

        //UI 객체생성
        yes_btn = findViewById(R.id.check_yes);
        no_btn = findViewById(R.id.check_no);

        editTextcost = findViewById(R.id.costBox);
        editTextquantity = findViewById(R.id.quantityBox);


        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();

                Toast.makeText(getApplicationContext(), get_textcost, Toast.LENGTH_SHORT).show();

            }
        });

        no_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

