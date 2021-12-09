package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 액티비티
public class SignupActivity extends AppCompatActivity {

    private EditText join_email, join_password, join_pwck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        join_email = findViewById( R.id.signup_email );
        join_password = findViewById( R.id.signup_password );
        join_pwck = findViewById(R.id.confirm_pwbox);

        ImageButton back_button = findViewById(R.id.back_signup);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button SignUpButton = findViewById(R.id.signup_button);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = join_email.getText().toString();
                String userPass = join_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            //회원가입 성공시
                            if (success) {
                                Toast.makeText(getApplicationContext(), "계정이 정상적으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, StartActivity.class);
                                startActivity(intent);
                                finish();
                                //회원가입 실패시
                            } else {
                                Toast.makeText(getApplicationContext(), "계정 생성에 실패했습니다", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                //서버로 Volley를 이용해서 요청
                RegisterRequest registerRequest = new RegisterRequest( userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue( SignupActivity.this );
                queue.add( registerRequest );
            }
        });

        TextView goLoginButton = findViewById(R.id.go_login_txt);
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}