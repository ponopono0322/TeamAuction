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

// 회원가입 액티비티
public class SignupActivity extends AppCompatActivity {

    private EditText signup_email, signup_password, confirm_pwbox;
    private Button signup_button;
    private TextView go_login_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 아이디 값 찾아주기
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        confirm_pwbox = findViewById(R.id.confirm_pwbox);

        // 회원가입 버튼을 클릭할 시 수행
        signup_button = findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view){
               // EditText에 현재 입력되어있는 값을 get해온다.
               String userID = signup_email.getText().toString();
               String userPassword = signup_password.getText().toString();
               String confirmPassword = confirm_pwbox.getText().toString();
               if(!userPassword.equals(confirmPassword)){// userPassword와 confirmPassword의 문자열 비교. 둘이 같지 않다면 "비밀번호 재입력부분을 확인해주세요." 문구가 나옴.
                   Toast.makeText(getApplicationContext(), "비밀번호 재입력부분을 확인해주세요.", Toast.LENGTH_SHORT).show();
                   return;
               }

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        try {
                            JSONObject jsonObject = new JSONObject(response);//회원가입 요청을 하면 결과값을 JSONObject로 받는 받아서 성공여부를 알기 위함
                            boolean success = jsonObject.getBoolean("success");
                            if(success){// 회원 등록에 성공한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{// 회원 등록에 실패한 경우
                                Toast.makeText(getApplicationContext(), "회원 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
               // 서버로 Volley를 이용해서 요청을 함.
               RegisterRequest registerRequest = new RegisterRequest(userID, userPassword, responseListener);
               RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
               queue.add(registerRequest);
           }
        });

        ImageButton back_button = findViewById(R.id.back_signup);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
        TextView goLoginButton = findViewById(R.id.go_login_txt);
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);//로그인 화면으로 이동함.
                startActivity(intent);
                finish();
            }
        });

    }
}