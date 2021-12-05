package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PublisherLoginActivity extends AppCompatActivity {

    TextView gameName;  // 메이플이면 메이플, 던파면 던파 이 명칭을 기준으로 해당 데이터베이스에 접근하여야한다.(구현 아직)
    private String gameName_data;
    private EditText login_publisher_emailbox, login_publisher_pwbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_login);

        login_publisher_emailbox = findViewById(R.id.login_publisher_emailbox);
        login_publisher_pwbox= findViewById(R.id.login_publisher_pwbox);
        gameName = findViewById(R.id.game_name);
        Intent intent = getIntent();
        gameName_data = intent.getStringExtra("data");
        gameName.setText(gameName_data);


        Button publisher_login_btn = findViewById(R.id.login_publisher_button);
        publisher_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameID = login_publisher_emailbox.getText().toString();
                String gamePW = login_publisher_pwbox.getText().toString();
                String gameNamee = gameName_data;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){// 로그인에 성공한 경우
                                String gameID = jsonObject.getString("gameID");
                                String gamePass = jsonObject.getString("gamePW");

                                Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PublisherLoginActivity.this, CharacterSelectActivity.class);
                                intent.putExtra("gameID", gameID);// 서버로부터 userID와 PW가 맞는지 검사를 한 후 로그인을 해줄지 정함.
                                intent.putExtra("gamePW", gamePass);
                                startActivity(intent);
                            }else{// 로그인에 실패한 경우
                                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GameLoginRequest gameloginRequest = new GameLoginRequest(gameNamee, gameID, gamePW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PublisherLoginActivity.this);
                queue.add(gameloginRequest);
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