package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
 * 회원가입 액티비티
 * 사용자가 회원가입을 진행할 수 있다
 */
public class MainSignupActivity extends AppCompatActivity {

    // 회원가입에 필요한 데이터를 입력받는 필드들
    private EditText join_email, join_password, join_pwck, join_name, join_phone, join_id;
    // 아이디 중복검사시 성공 여부를 받는 boolean 값
    private boolean validate = false;
    // 경고창을 띄우는 변수
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        join_name = findViewById(R.id.signup_name);             // 이름을 받는 필드
        join_phone = findViewById(R.id.signup_phone);           // 핸드폰 번호를 받는 필드
        join_email = findViewById( R.id.signup_email );         // 이메일을 받는 필드
        join_id = findViewById(R.id.signup_id);                 // 아이디를 받는 필드
        join_password = findViewById( R.id.signup_password );   // 비밀번호를 받는 필드
        join_pwck = findViewById(R.id.confirm_pwbox);           // 비밀번호 재확인하는 필드

        ImageButton back_button = findViewById(R.id.back_signup); // 이미지 버튼을 정의
        back_button.setOnClickListener(new View.OnClickListener() { // 이미지 버튼을 클릭시 이벤트를 활성화 시킴
            @Override
            public void onClick(View view) {    // onClick을 재정의 하여 이벤트 수행
                // 이전 화면으로 가도록 하는 intent
                Intent intent = new Intent(MainSignupActivity.this, MainStartActivity.class);
                startActivity(intent);  // intent 실행
                finish();               // 현재 엑티비티는 종료시킴
            }
        });

        Button checkIdButton = findViewById(R.id.check_id);     // 아이디 중복검사를 수행하는 버튼
        checkIdButton.setOnClickListener(new View.OnClickListener() {   // 중복검사 버튼 클릭시 이벤트를 활성화
            @Override
            public void onClick(View view) {    // onClick 함수를 재정의 함
                String userID = join_id.getText().toString();   // 아이디를 String으로 받음
                if (validate) { return; }       // 만약 validate가 참이라면 이벤트 종료
                if (userID.equals("")) {        // 만약 값이 비어있다면
                    // 알림창 준비
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainSignupActivity.this);
                    // 알림창 세팅
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    // 알림창 띄우기
                    dialog.show();
                    // 그리고 이벤트 종료
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {  // volley 통신을 수행하려고 응답대기를 준비
                    @Override
                    public void onResponse(String response) {   // onResponse 재정의
                        try {   // 만약 반응이 왔고 정상적이라면,
                            JSONObject jsonResponse = new JSONObject(response);           //json 형태로 받기 위해 객체 준비
                            boolean success = jsonResponse.getBoolean("success");   // success라는 key 값으로 boolean value를 받음

                            if (success) {  // success가 참이라면
                                // 알림창 준비
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainSignupActivity.this);
                                // 알림창 세팅
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                // 알림창 띄우기
                                dialog.show();
                                join_id.setEnabled(false);      //아이디값 고정
                                checkIdButton.setVisibility(View.GONE);//버튼 비활성화
                                validate = true;                //검증 완료
                            } else {
                                // 알림창 준비
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainSignupActivity.this);
                                // 알림창 세팅
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                // 알림창 띄우기
                                dialog.show();
                                // 입력 필드 비워주기
                                join_id.setText(null);
                            }
                        } catch (JSONException e) { // 반응이 왔으나 오류라면,
                            e.printStackTrace();    // 에러를 출력해줌
                        }
                    }
                };
                String purl = "http://ualsgur98.dothome.co.kr/UserValidate.php";                // 통신할 php 주소
                PHPRequest validateRequest = new PHPRequest( purl, userID, responseListener);   // 데이터 전송을 위한 데이터 세팅
                RequestQueue queue = Volley.newRequestQueue(MainSignupActivity.this);   // 큐를 생성
                queue.add(validateRequest); // 큐에 추가
            }
        });

        Button SignUpButton = findViewById(R.id.signup_button);         // 회원가입 버튼
        SignUpButton.setOnClickListener(new View.OnClickListener() {    // 회원가입 버튼을 눌렀을 때 이벤트 생성
            @Override
            public void onClick(View view) {
                String userName = join_name.getText().toString();       // 이름 입력 필드에서 String 형태로 데이터를 가져옴
                String userPhone = join_phone.getText().toString();     // 핸드폰번호 입력 필드에서 String 형태로 데이터를 가져옴
                String userEmail = join_email.getText().toString();     // 이메일 입력 필드에서 String 형태로 데이터를 가져옴
                String userID = join_id.getText().toString();           // 아이디 입력 필드에서 String 형태로 데이터를 가져옴
                String userPass = join_password.getText().toString();   // 비밀번호 입력 필드에서 String 형태로 데이터를 가져옴
                String userPwck = join_pwck.getText().toString();       // 비밀번호 검증 입력 필드에서 String 형태로 데이터를 가져옴

                Response.Listener<String> responseListener = new Response.Listener<String>() {  // volley 통신을 수행하려고 응답대기를 준비
                    @Override
                    public void onResponse(String response) {           // onResponse 재정의
                        try {
                            JSONObject jsonObject = new JSONObject(response);           // json 형태로 받기 위해 객체 준비
                            boolean success = jsonObject.getBoolean("success");   // success라는 key 값으로 boolean value를 받음

                            if (success) {      // success가 참이라면
                                if(userPass.equals(userPwck)){  // 비밀번호 검증 입력 필드에서 비밀번호와 같다면
                                    // 토스트 메세지를 띄워주고
                                    Toast.makeText(getApplicationContext(), "계정이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                                    // MainStartActivity로 가는 Intent를 생성
                                    Intent intent = new Intent(MainSignupActivity.this, MainStartActivity.class);
                                    startActivity(intent);  // 새 엑티비티를 띄워줌
                                    finish();   // 그리고 현재 엑티비티는 종료
                                }
                                else{          // 만약 비밀번호 검증 필드에서 같지 않았다면
                                    // 토스트 메세지를 띄워주고
                                    Toast.makeText(getApplicationContext(), "비밀번호가 같지 않습니다", Toast.LENGTH_SHORT).show();
                                    return;     // 이벤트 종료
                                }

                            } else {            // php로 부터 success가 도착하지 않았다면
                                // 토스트 메세지를 띄워주고
                                Toast.makeText(getApplicationContext(), "계정 생성에 실패했습니다", Toast.LENGTH_SHORT).show();
                                return;         // 이벤트 종료
                            }

                        } catch (JSONException e) { // 만약 중간에 오류가 생겼다면,
                            e.printStackTrace();    // 오류 추적
                        }

                    }
                };

                String purl = "http://ualsgur98.dothome.co.kr/Register.php";    // 통신할 php 주소
                // 데이터 전송을 위한 데이터 세팅
                PHPRequest registerRequest = new PHPRequest(purl, userName, userPhone, userEmail, userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue( MainSignupActivity.this ); // 큐를 생성
                queue.add( registerRequest );   // 큐에 추가
            }
        });

        TextView goLoginButton = findViewById(R.id.go_login_txt);       // 로그인화면으로 바로 이동
        goLoginButton.setOnClickListener(new View.OnClickListener() {   // 텍스트뷰에 터치 이벤트를 생성
            @Override
            public void onClick(View view) {                            // onClick 함수 재정의
                // MainSignupActivity에서 MainLoginActivity으로 가는 intent 생성
                Intent intent = new Intent(MainSignupActivity.this, MainLoginActivity.class);
                startActivity(intent);  // intent 실행
                finish();               // 현재 엑티비티 종료
            }
        });
    }
}