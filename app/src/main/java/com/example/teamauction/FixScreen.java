package com.example.teamauction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FixScreen extends AppCompatActivity {
    private EditText editTextcost, editTextquantity;
    Button yes_btn, no_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_fix);

        //UI 객체생성
        yes_btn = findViewById(R.id.fix_check_yes);
        no_btn = findViewById(R.id.fix_check_no);

        editTextcost = findViewById(R.id.fixCostBox);
        editTextquantity = findViewById(R.id.fixQuantityBox);


        //예 버튼을 눌렀을때 값 저장
        yes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_textcost = editTextcost.getText().toString();
                String get_textquan = editTextquantity.getText().toString();

                Toast.makeText(getApplicationContext(), get_textcost, Toast.LENGTH_SHORT).show();
                finish();
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
