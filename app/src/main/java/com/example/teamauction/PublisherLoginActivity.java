package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PublisherLoginActivity extends AppCompatActivity {
    private EditText game_id, game_pw;
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

        game_id = findViewById(R.id.login_publisher_emailbox);
        game_pw = findViewById(R.id.login_publisher_pwbox);

        Button publisher_login_btn = findViewById(R.id.login_publisher_button);
        publisher_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameID = game_id.getText().toString();
                String gamePW = game_pw.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {  // 로그인 성공시
                                String jsonID = jsonObject.getString("gameID");
                                //String jsonPW = jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(), jsonID+" 님 환영합니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PublisherLoginActivity.this, CharacterSelectActivity.class);
                                GameAccountInfo new_account = new GameAccountInfo();
                                new_account.setGameName(gameName_data);
                                new_account.setGamePublisherID(gameID);
                                new_account.setGamePublisherPW(gamePW);
                                intent.putExtra("game_account", new_account);
                                startActivity(intent);
                                finish();

                            } else {        // success가 false일 때
                                Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) { // 로그인 오류가 난 것이라면
                            //e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "존재하지 않는 게임계정입니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                };
                String purl = "http://ualsgur98.dothome.co.kr/gamelogin.php";
                RequestPHP validateRequest = new RequestPHP( purl, gameName_data, gameID, gamePW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(PublisherLoginActivity.this);
                queue.add(validateRequest);
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