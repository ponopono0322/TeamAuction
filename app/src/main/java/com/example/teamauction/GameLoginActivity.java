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

/**
 * 게임사 로그인을 하는 엑티비티
 * 게임 목록을 선택한 후 나오는 엑티비티이며
 * 게임사 로그인을 통해 게임 캐릭터를 선택할 수 있도록 도와주는 엑티비티이다
 */
public class GameLoginActivity extends AppCompatActivity {
    private EditText game_id, game_pw;      // 게임 로그인 아이디, 비밀번호 입력 필드
    private GameAccountInfo accountInfo;    // 계정 정보를 받기 위한 GameAccountInfo 객체 생성
    TextView gameName;  // 게임 이름 텍스트뷰
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_login);

        Intent account_info = getIntent();  // intent를 받아옴
        // account_info라는 key로 GameAccountInfo 객체를 받아옴
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        gameName = findViewById(R.id.game_name);    // 게임 이름 텍스트뷰
        gameName.setText(accountInfo.getGameName());// 게임 이름 설정

        game_id = findViewById(R.id.login_publisher_emailbox);  // 아이디 입력 필드
        game_pw = findViewById(R.id.login_publisher_pwbox);     // 비밀번호 입력 필드

        Button publisher_login_btn = findViewById(R.id.login_publisher_button); // 로그인 버튼
        publisher_login_btn.setOnClickListener(new View.OnClickListener() {     // 로그인 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {                    // onClick 함수 재정의
                String gameID = game_id.getText().toString();   // 아이디 입력 필드에서 String 형태로 데이터를 가져옴
                String gamePW = game_pw.getText().toString();   // 비밀번호 입력 필드에서 String 형태로 데이터를 가져옴

                if(gameID.equals("")) {     // 만약 값이 비어있다면
                    // 토스트 메세지를 띄워주고
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return; // 이벤트 종료
                }

                // volley 통신을 수행하려고 응답대기를 준비
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {                           // onResponse 함수 재정의
                        try {       // 만약 반응이 왔고 정상적이라면,
                            JSONObject jsonObject = new JSONObject(response);           // json 형태로 받기 위해 객체 준비
                            boolean success = jsonObject.getBoolean("success");   // GameList key 값으로 배열을 받음

                            if (success) {  // success가 참이라면
                                String jsonID = jsonObject.getString("gameID");   // gameID 데이터 저장
                                String jsonPW = jsonObject.getString("gamePW");   // gamePW 데이터 저장
                                accountInfo.setGamePublisherID(jsonID);                 // GameAccountInfo 객체에 아이디 저장
                                accountInfo.setGamePublisherPW(jsonPW);                 // GameAccountInfo 객체에 비밀번호 저장
                                // 토스트 메세지를 띄워줌
                                Toast.makeText(getApplicationContext(), jsonID+" 님 환영합니다", Toast.LENGTH_SHORT).show();
                                // GameLoginActivity에서 GameChActivity으로 가는 intent 생성
                                Intent intent = new Intent(GameLoginActivity.this, GameChActivity.class);
                                intent.putExtra("account_info", accountInfo);     // intent에 데이터를 넣음
                                startActivity(intent);      // intent 실행
                                finish();                   // 현재 엑티비티 종료

                            } else {        // success가 false일 때
                                // 토스트 메세지를 띄워주고
                                Toast.makeText(getApplicationContext(), "서버와 연결에 실패했습니다", Toast.LENGTH_SHORT).show();
                                return;     // 이벤트 종료
                            }

                        } catch (JSONException e) { // 로그인 오류가 난 것이라면
                            e.printStackTrace();    // 오류 추적
                            // 토스트 메세지를 띄워줌
                            Toast.makeText(getApplicationContext(), "존재하지 않는 게임계정입니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                String purl = "http://ualsgur98.dothome.co.kr/gamelogin.php";                   // 통신할 php 주소
                // 데이터 전송을 위한 데이터 세팅
                PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getGameName(),
                        gameID, gamePW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GameLoginActivity.this);    // 큐를 생성
                queue.add(validateRequest);     // 큐에 추가
            }
        });

        ImageButton back_add_publisher = findViewById(R.id.back_add_account);   // 뒤로가기 버튼
        back_add_publisher.setOnClickListener(new View.OnClickListener() {      // 뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {                                    // onClick 함수 재정의
                // GameLoginActivity에서 GameListActivity으로 가는 intent 생성
                Intent intent = new Intent(GameLoginActivity.this, GameListActivity.class);
                intent.putExtra("account_info", accountInfo);             // intent에 데이터를 넣음
                startActivity(intent);      // intent 실행
                finish();                   // 현재 엑티비티 종료
            }
        });

    }
}