package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 게임 목록이 나오는 엑티비티
 * 연동 가능한 게임 목록이 나오고 선택할 수 있다
 */
public class GameListActivity extends AppCompatActivity {

    private ListViewAdapter adapter;        // 리스트뷰 어뎁터
    private ListView listview;              // 리스트뷰
    private GameAccountInfo accountInfo;    // 계정 정보를 받기 위한 GameAccountInfo 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        Intent account_info = getIntent();  // intent를 받아옴
        // account_info라는 key로 GameAccountInfo 객체를 받아옴
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        ImageButton go_main = findViewById(R.id.back_account);      // 뒤로가기 버튼
        go_main.setOnClickListener(new View.OnClickListener() {     // 뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // GameListActivity MainActivity으로 가는 intent 정의
                Intent intent = new Intent(GameListActivity.this, MainActivity.class);
                intent.putExtra("account_info", accountInfo); // intent에 데이터를 넣음
                startActivity(intent);      // intent 실행
                finish();                   // 현재 엑티비티 종료
            }
        });

        adapter = new ListViewAdapter();   // Adapter 생성

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview_choose_game);
        listview.setAdapter(adapter);

        // volley 통신을 수행하려고 응답대기를 준비
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {                               // onResponse 함수 재정의
                try {   // 만약 반응이 왔고 정상적이라면,
                    JSONObject jsonResponse = new JSONObject(response);             // json 형태로 받기 위해 객체 준비
                    JSONArray games = jsonResponse.getJSONArray("GameList");  // GameList key 값으로 배열을 받음
                    for (int i = 0; i < games.length(); i++) {              // games 길이만큼 반복문 수행
                        JSONObject item = games.getJSONObject(i);           // games의 데이터를 가져옴
                        String gameName = item.getString("GameName"); // GameName 데이터 저장
                        // 리스트뷰에 데이터 추가
                        adapter.addItem(ContextCompat.getDrawable(GameListActivity.this,
                                R.drawable.ic_baseline_account_box_24), gameName, "name");
                    }
                    adapter.notifyDataSetChanged();     // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) {             // 접속 오류가 난 것이라면
                    e.printStackTrace();                // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/GameList.php";                // 통신할 php 주소
        PHPRequest validateRequest = new PHPRequest( purl, responseListener);       // 데이터 전송을 위한 데이터 세팅
        RequestQueue queue = Volley.newRequestQueue(GameListActivity.this); // 큐를 생성
        queue.add(validateRequest);  // 큐에 추가

        Button publisher_login = findViewById(R.id.login_button_publisher);         // 로그인 버튼
        publisher_login.setOnClickListener(new View.OnClickListener() {             // 로그인 클릭 이벤트 생성
            @Override
            public void onClick(View view) {                                        // onClick 함수 재정의
                int pos = listview.getCheckedItemPosition();                        // 리스트뷰 위치데이터 변수
                if (pos > -1) {     // pos는 0이상이므로 그 이상일 때만 작동
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);        // 리스트뷰의 아이테을 가져옴
                    accountInfo.setGameName(item.getText());                        // GameAccountInfo 객체에 게임 이름 저장
                    // GameListActivity에서 GameLoginActivity으로 가는 intent 생성
                    Intent intent = new Intent(GameListActivity.this, GameLoginActivity.class);
                    intent.putExtra("account_info", accountInfo);             // intent에 데이터 추가
                    startActivity(intent);      // intent 실행
                    finish();                   // 현재 엑티비티 종료
                }
                else {  // pos가 -1 이하 이므로 리스트뷰 영역을 터치한 적이 없음
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(GameListActivity.this, "게임을 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}