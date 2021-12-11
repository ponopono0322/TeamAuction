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

// 로그인화면 엑티비티
public class LoginActivity extends AppCompatActivity {

    private EditText login_id, login_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_id = findViewById(R.id.login_idbox);
        login_pw = findViewById(R.id.login_pwbox);

        ImageButton back_button = findViewById(R.id.back_login);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainStartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button = findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userID = login_id.getText().toString();
                String userPW = login_pw.getText().toString();

                if(userID.equals("")) {
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
                                String jsonID = jsonObject.getString("userID");
                                //String jsonPW = jsonObject.getString("userPassword");

                                Toast.makeText(getApplicationContext(), jsonID+" 님 환영합니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {        // success가 false일 때
                                Toast.makeText(getApplicationContext(), "서버와 연결이 끊겼습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) { // 로그인 오류가 난 것이라면
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "등록되지 않은 계정입니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                };

                String purl = "http://ualsgur98.dothome.co.kr/Login.php";
                PHPRequest validateRequest = new PHPRequest( purl, userID, userPW, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(validateRequest);
            }
        });

        TextView return_signup_textview = findViewById(R.id.return_signup);
        return_signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        TextView return_find_password = findViewById(R.id.go_fpassword_text);
        return_find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}