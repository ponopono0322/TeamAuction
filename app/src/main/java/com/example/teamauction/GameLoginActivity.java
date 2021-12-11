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

public class GameLoginActivity extends AppCompatActivity {
    private EditText game_id, game_pw;
    private GameAccountInfo accountInfo;
    TextView gameName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_login);

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        gameName = findViewById(R.id.game_name);
        gameName.setText(accountInfo.getGameName());

        game_id = findViewById(R.id.login_publisher_emailbox);
        game_pw = findViewById(R.id.login_publisher_pwbox);

        Button publisher_login_btn = findViewById(R.id.login_publisher_button);
        publisher_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameID = game_id.getText().toString();
                String gamePW = game_pw.getText().toString();

                if(gameID.equals("")) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {  // 로그인 성공시
                                String jsonID = jsonObject.getString("gameID");
                                String jsonPW = jsonObject.getString("gamePW");
                                accountInfo.setGamePublisherID(jsonID);
                                accountInfo.setGamePublisherPW(jsonPW);

                                Toast.makeText(getApplicationContext(), jsonID+" 님 환영합니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(GameLoginActivity.this, GameChActivity.class);
                                intent.putExtra("account_info", accountInfo);
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
                PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getGameName(),
                        gameID, gamePW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GameLoginActivity.this);
                queue.add(validateRequest);
            }
        });

        ImageButton back_add_publisher = findViewById(R.id.back_add_account);
        back_add_publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameLoginActivity.this, GameListActivity.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
                finish();
            }
        });

    }
}