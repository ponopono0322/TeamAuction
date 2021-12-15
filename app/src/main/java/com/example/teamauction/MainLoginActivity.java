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
 * 회원 로그인화면 엑티비티
 * 회원은 로그인하여 경매장 서비스를 이용할 수 있다
 * 계정이 기억나지 않는 경우 비밀번호 찾기 엑티비티로 이동 가능하고
 * 계정이 없을 경우 계정 만들기 엑티비티로 넘어갈 수 있다
 */
public class MainLoginActivity extends AppCompatActivity {

    private EditText login_id, login_pw;    // 로그인을 위한 아이디와 비밀번호를 입력받는 필드
    private GameAccountInfo account_data;   // 계정 정보를 갖고 있기 위한 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = findViewById(R.id.login_idbox);  // 아이디 입력 필드
        login_pw = findViewById(R.id.login_pwbox);  // 비밀번호 입력 필드

        ImageButton back_button = findViewById(R.id.back_login);    // 뒤로가기를 위한 이미지 버튼
        back_button.setOnClickListener(new View.OnClickListener() { // 이미지 버튼에 대한 이벤트 생성
            @Override
            public void onClick(View view) {    // onClick 함수 재정의
                // MainLoginActivity에서 MainStartActivity으로 가기 위한 intent 생성
                Intent intent = new Intent(MainLoginActivity.this, MainStartActivity.class);
                startActivity(intent);          // intent 실행
                finish();                       // 현재 화면 종료
            }
        });
        account_data = new GameAccountInfo();   // GameAccountInfo 객체 생성
        Button button = findViewById(R.id.login_button);    // 로그인 버튼
        button.setOnClickListener(new View.OnClickListener() {  // 로그인 버튼에 대한 이벤트 생성
            @Override
            public void onClick(View view) {    // onClick 함수 재정의

                String userID = login_id.getText().toString();      // 아이디 입력필드 String 형태로 가져오기
                String userPW = login_pw.getText().toString();      // 비밀번호 입력필드 String 형태로 가져오기

                if(userID.equals("")) {     // 아이디 입력필드가 비어있다면
                    // 토스트 메세지를 띄워주고
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return; // 이벤트 종료
                }
                // volley 통신을 수행하려고 응답대기를 준비
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {        // onResponse 함수 재정의
                        try {   // 만약 반응이 왔고 정상적이라면,
                            JSONObject jsonObject = new JSONObject(response);           //json 형태로 받기 위해 객체 준비
                            boolean success = jsonObject.getBoolean("success");   // success라는 key 값으로 boolean value를 받음

                            if (success) {  // success가 참이라면
                                String jsonID = jsonObject.getString("userID");         // jsonObject 객체로부터 userID를 가져옴
                                String jsonPW = jsonObject.getString("userPassword");   // jsonObject 객체로부터 userPassword 가져옴
                                // 토스트 메세지를 띄워주고
                                Toast.makeText(getApplicationContext(), jsonID+" 님 환영합니다", Toast.LENGTH_SHORT).show();
                                // MainLoginActivity에서 MainActivity으로 가는 intent 생성 후
                                Intent intent = new Intent(MainLoginActivity.this, MainActivity.class);
                                account_data.setLoginID(jsonID);    // 아이디를 GameAccountInfo 객체에 저장
                                account_data.setLoginPW(jsonPW);    // 비밀번호를 GameAccountInfo 객체에 저장
                                intent.putExtra("account_info", account_data);  // intent에 데이터를 추가한 후
                                startActivity(intent);              // MainActivity 실행
                                finish();   // 현재 엑티비티 종료

                            } else {        // success가 false일 때
                                // 토스트 메세지를 띄워주고
                                Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                                return;     // 이벤트 종료
                            }

                        } catch (JSONException e) { // 로그인 오류가 난 것이라면
                            e.printStackTrace();    // 에러 추적하고
                            // 토스트 메세지를 띄워준다
                            Toast.makeText(getApplicationContext(), "등록되지 않은 계정입니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                };

                String purl = "http://ualsgur98.dothome.co.kr/Login.php";                               // 통신할 php 주소
                PHPRequest validateRequest = new PHPRequest( purl, userID, userPW, responseListener);   // 데이터 전송을 위한 데이터 세팅
                RequestQueue queue = Volley.newRequestQueue(MainLoginActivity.this);            // 큐를 생성
                queue.add(validateRequest); // 큐에 추가
            }
        });

        TextView return_signup_textview = findViewById(R.id.return_signup);     // 회원가입 텍스트뷰
        return_signup_textview.setOnClickListener(new View.OnClickListener() {  // 텍스트뷰 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // MainLoginActivity에서 MainSignupActivity으로 가는 intent 생성
                Intent intent = new Intent(MainLoginActivity.this, MainSignupActivity.class);
                startActivity(intent);              // intent 실행
                finish();       // 현재 엑티비티 종료
            }
        });

        TextView return_find_password = findViewById(R.id.go_fpassword_text);   // 비밀번호 찾기 텍스트뷰
        return_find_password.setOnClickListener(new View.OnClickListener() {    // 텍스트뷰 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // MainLoginActivity에서 MainPasswordActivity으로 가는 intent 생성
                Intent intent = new Intent(MainLoginActivity.this, MainPasswordActivity.class);
                startActivity(intent);              // intent 실행
                finish();       // 현재 엑티비티 종료
            }
        });
    }
}