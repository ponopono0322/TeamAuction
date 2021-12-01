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

// 로그인화면 엑티비티
public class LoginActivity extends AppCompatActivity {
    private EditText login_emailbox, login_pwbox;
    private Button login_button;    // 로그인 버튼
    private TextView go_fpassword_text;   // 사용자 계정을 찾는 버튼
    private TextView return_signup;   // 회원가입쪽으로 가는 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_emailbox= findViewById(R.id.login_emailbox);
        login_pwbox= findViewById(R.id.login_pwbox);
        login_button= findViewById(R.id.login_button);  // 로그인 버튼
        go_fpassword_text= findViewById(R.id.go_fpassword_text);    // 계정찾기 버튼
        return_signup= findViewById(R.id.return_signup);    // 계정이 없으신가요? 버튼

        // 계정이 없으신가요? 클릭 시 수행(회원가입 창으로 넘어감)
        return_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        // 로그인버튼 클릭 시 수행
        login_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String userID = login_emailbox.getText().toString();
                String userPassword = login_pwbox.getText().toString();

               Response.Listener<String> responseListener = new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject jsonObject = new JSONObject(response);//회원가입 요청을 하면 결과값을 JSONObject로 받는 받아서 성공여부를 알기 위함
                           boolean success = jsonObject.getBoolean("success");
                           if(success){// 로그인에 성공한 경우
                               String userID = jsonObject.getString("userID");
                               String userPass = jsonObject.getString("userPassword");

                               Toast.makeText(getApplicationContext(), "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                               intent.putExtra("userID", userID);// 서버로부터 userID와 PW가 맞는지 검사를 한 후 로그인을 해줄지 정함.
                               intent.putExtra("userPass", userPass);
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
               LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        ImageButton back_button = findViewById(R.id.back_login);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });
    /*
        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
*/
        TextView return_signup_textview = findViewById(R.id.return_signup);
        return_signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView return_find_password = findViewById(R.id.go_fpassword_text);
        return_find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}