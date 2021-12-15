package com.example.teamauction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 게임 로그인을 완료한 상황에서 나오는 액티비티
 * 게임 계정 내에 있는 게임 캐릭터를 선택할 수 있다.
 */
public class GameChActivity extends AppCompatActivity {

    private GameAccountInfo accountInfo;    // 계정 정보를 받기 위한 GameAccountInfo 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        Intent account_info = getIntent();  // intent를 받아옴
        // account_info라는 key로 GameAccountInfo 객체를 받아옴
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        ImageButton CanalAdda = findViewById(R.id.cancel_account_add);  // 뒤로가기 버튼
        CanalAdda.setOnClickListener(new View.OnClickListener() {       // 뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {        // onClick 함수 재정의
                // GameChActivity에서 GameLoginActivity으로 가는 intent 정의
                Intent intent = new Intent(GameChActivity.this, GameLoginActivity.class);
                intent.putExtra("account_info", accountInfo);   // intent에 데이터를 넣음
                startActivity(intent);  // intent 실행
                finish();               // 현재 엑티비티 종료
            }
        });

        ListView listview;          // 리스트뷰 변수
        ListViewAdapter adapter;    // 리스트뷰 어뎁터

        // Adapter 생성
        adapter = new ListViewAdapter();    // 리스트뷰 어뎁터 생성

        // 리스트뷰 참조 및 Adapter 달기
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);   // 리스트뷰 어뎁터 설정

        // volley 통신을 수행하려고 응답대기를 준비
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {           // onResponse 함수 재정의
                try {                                           // 만약 반응이 왔고 정상적이라면,
                    JSONObject jsonResponse = new JSONObject(response);                 // json 형태로 받기 위해 객체 준비
                    JSONArray Ids = jsonResponse.getJSONArray("CharacterList");   // CharacterList key 값으로 배열을 받음
                    for (int i = 0; i < Ids.length(); i++) {    // Ids 길이만큼 반복문 수행
                        JSONObject item = Ids.getJSONObject(i); // Ids의 데이터를 가져옴
                        String characterName = item.getString("gameNickname");    // gameNickname 데이터 저장
                        String characterMony = item.getString("gameMoney");       // gameMoney 데이터 저장
                        // 리스트뷰에 데이터 추가
                        adapter.addItem(ContextCompat.getDrawable(GameChActivity.this,
                                R.drawable.ic_baseline_account_box_24), characterName, characterMony);
                    }
                    adapter.notifyDataSetChanged();     // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();    // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };

        String purl = "http://ualsgur98.dothome.co.kr/CharacterList.php";           // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getGameName(),
                accountInfo.getGamePublisherID(), accountInfo.getGamePublisherPW(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(GameChActivity.this);   // 큐를 생성
        queue.add(validateRequest);     // 큐에 추가

        Button selectCharacter = findViewById(R.id.select_game_character);          // 캐릭터 선택 버튼
        selectCharacter.setOnClickListener(new View.OnClickListener() {             // 선택버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {                                        // onClick 함수 재정의
                int pos = listview.getCheckedItemPosition();                        // 리스트뷰 위치데이터 변수
                if (pos > -1) {     // pos는 0이상이므로 그 이상일 때만 작동
                    ListViewItem item = (ListViewItem) adapter.getItem(pos);        // 리스트뷰의 아이테을 가져옴
                    accountInfo.setCharacterName(item.getText());                   // GameAccountInfo 객체에 캐릭터 이름 저장

                    // volley 통신을 수행하려고 응답대기를 준비
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {                   // onResponse 함수 재정의
                            try {                                                   // 만약 반응이 왔고 정상적이라면,
                                JSONObject jsonResponse = new JSONObject(response); // json 형태로 받기 위해 객체 준비
                                boolean success = jsonResponse.getBoolean("success");   // success라는 key 값으로 boolean value를 받음
                                if(success) {                                       // success가 참이라면
                                    // 토스트 메세지를 띄워주고
                                    Toast.makeText(getApplicationContext(), "계정이 정상적으로 연동되었습니다", Toast.LENGTH_SHORT).show();
                                    // GameChActivity에서 MainActivity으로 가는 intent 생성
                                    Intent intent = new Intent(GameChActivity.this, MainActivity.class);
                                    accountInfo.resetData();    // 필요한 정보를 빼고는 비워줌
                                    intent.putExtra("account_info", accountInfo);   // intent에 데이터 추가
                                    startActivity(intent);      // intent 실행
                                    finish();                   // 현재 엑티비티 종료
                                }
                                else {      // success가 참이 아니라면
                                    // 토스트 메세지를 띄워줌
                                    Toast.makeText(getApplicationContext(), "서버 접속 오류", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) { // 접속 오류가 난 것이라면
                                e.printStackTrace();    // 오류 추적
                                // 토스트 메세지를 띄워줌
                                Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };

                    String purl = "http://ualsgur98.dothome.co.kr/MyGameAndCharacter.php";  // 통신할 php 주소
                    // 데이터 전송을 위한 데이터 세팅
                    PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getLoginID(), accountInfo.getLoginPW(), accountInfo.getGameName(),
                            accountInfo.getGamePublisherID(), accountInfo.getGamePublisherPW(), accountInfo.getCharacterName(), responseListener);
                    RequestQueue queue = Volley.newRequestQueue(GameChActivity.this);// 큐를 생성
                    queue.add(validateRequest);     // 큐에 추가
                }
                else {  // pos가 -1 이하 이므로 리스트뷰 영역을 터치한 적이 없음
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(GameChActivity.this, "캐릭터를 선택해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}