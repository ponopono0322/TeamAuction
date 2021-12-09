package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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

// 회원가입 액티비티
public class SignupActivity extends AppCompatActivity {

    private EditText join_email, join_password, join_pwck, join_name, join_phone, join_id;
    private boolean validate = false;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        join_name = findViewById(R.id.signup_name);
        join_phone = findViewById(R.id.signup_phone);
        join_email = findViewById( R.id.signup_email );
        join_id = findViewById(R.id.signup_id);
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

        Button checkIdButton = findViewById(R.id.check_id);
        checkIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = join_id.getText().toString();
                if (validate) { return; }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                join_email.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                checkIdButton.setBackgroundColor(Color.WHITE);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                String purl = "http://ualsgur98.dothome.co.kr/UserValidate.php";
                RequestPHP validateRequest = new RequestPHP( purl, userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                queue.add(validateRequest);
            }
        });

        Button SignUpButton = findViewById(R.id.signup_button);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = join_name.getText().toString();
                String userPhone = join_phone.getText().toString();
                String userEmail = join_email.getText().toString();
                String userID = join_id.getText().toString();
                String userPass = join_password.getText().toString();
                String userPwck = join_pwck.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            //회원가입 성공시
                            if (success) {

                                if(userPass.equals(userPwck)){

                                }

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

                // 서버 전송
                String purl = "http://ualsgur98.dothome.co.kr/Register.php";
                RequestPHP registerRequest = new RequestPHP(purl, userName, userPhone, userEmail, userID, userPass, responseListener);
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