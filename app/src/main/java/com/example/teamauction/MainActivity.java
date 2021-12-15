package com.example.teamauction;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 메인화면
 * 앱 실행시 첫화면
 * 회원로그인 혹은 회원가입 페이지로 이동할 수 있게 해주는 엑티비티
 */
public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null;          // 리사이클러뷰 정의
    RecyclerViewAdapter mAdapter = null;        // 리사이클러뷰 어뎁터 정의
    ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();  // 리사이클러뷰 안에 들어갈 객체 정의
    private ActivityResultLauncher<Intent> mStartForResult;         // 엑티비티 콜백 변수, intent에 대한 응답을 받아줌
    private int last_pos;                       // 리사이클러뷰 안에 정의된 체크박스의 마지막 위치 변수
    private GameAccountInfo accountInfo;        // 계정 정보에 대한 객체

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent account_info = getIntent();      // intent를 받아옴
        // account_info라는 key로 GameAccountInfo 객체를 받아옴
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        //액티비티 콜백 함수
        mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {       // 다른 엑티비티에서 받아온 결과
                        if (result.getResultCode() == RESULT_OK) {              // ResultCode가 RESULT_OK라면,
                            Intent intent = result.getData();                   // intent로 부터 데이터를 가져옴
                            int code_get = intent.getIntExtra("result",0);  // 그 데이터 안의 result라는 key로 정의된 데이터를 꺼냄

                            if(code_get == 0){      // 그 코드가 0이라면
                                // 토스트 메세지를 띄움
                                Toast.makeText(MainActivity.this, getString(R.string.cancel_logout), Toast.LENGTH_SHORT).show();
                            }
                            else if(code_get == 1){ // 그 코드가 1이라면
                                // 토스트 메세지를 띄우고
                                Toast.makeText(MainActivity.this, getString(R.string.confirm_logout), Toast.LENGTH_SHORT).show();
                                // 로그인 화면으로 갈 준비를 한 후
                                Intent back_login_page = new Intent(MainActivity.this, MainLoginActivity.class);
                                startActivity(back_login_page); // intent 실행
                                finish();       // 그리고 현재 엑티비티를 종료
                            }
                            else if(code_get == 2){ // 그 코드가 2이라면
                                // 토스트 메세지를 띄움
                                Toast.makeText(MainActivity.this, getString(R.string.cancel_access_account), Toast.LENGTH_SHORT).show();
                            }
                            else if(code_get == 3){ // 그 코드가 3이라면
                                // 토스트 메세지를 띄움
                                Toast.makeText(MainActivity.this, getString(R.string.confirm_access_account), Toast.LENGTH_SHORT).show();
                            }
                            else {  // 그 외의 코드가 들어왔다면

                            }
                        }
                    }
                });

        ImageButton go_logout = findViewById(R.id.go_logout);       // 뒤로가기 버튼
        go_logout.setOnClickListener(new View.OnClickListener() {   // 뒤로가기 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View v) {       // onClick 함수 재정의
                // MainActivity에서 PopupActivity을 띄우는 intent 준비
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("data", getString(R.string.wanttologout));  // intent에 데이터를 넣음
                mStartForResult.launch(intent); // mStartForResult를 통한 intent 실행
            }
        });

        ImageView add_account = findViewById(R.id.add_account);     // 새로운 연동 계정 추가 버튼
        add_account.setOnClickListener(new View.OnClickListener() { // 계정연동 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {    // onClick 함수 재정의
                // MainActivity에서 GameListActivity으로 가는 intent 준비
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                intent.putExtra("account_info", accountInfo);   // intent에 데이터를 넣음
                startActivity(intent);  // intent 실행
            }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        mRecyclerView = findViewById(R.id.linked_id_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));        // 생성 위치 지정
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(10));    // 높이 지정

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter); // 어뎁터 설정
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) { // onItemClick 함수 재정의
                //Toast.makeText(v.getContext(), "touched"+position, Toast.LENGTH_SHORT).show();
                // PopupActivity를 띄우는 intent 생성
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("data", getString(R.string.access_account));    // intent에 데이터 추가
                mStartForResult.launch(intent);     // mStartForResult를 통한 intent 실행
            }
        });

        Button go_login = findViewById(R.id.login_account);          // 로그인 버튼
        go_login.setOnClickListener(new View.OnClickListener() {     // 로그인 버튼 클릭 이벤트 생성
            @Override
            public void onClick(View view) {                    // onClick 함수 재정의
                last_pos = RecyclerViewAdapter.lastCheckedPos;  // 마지막 위치를 받음
                ListViewItem item = mList.get(last_pos);        // 그 위치로 데이터를 가져옴
                // 토스트 메세지를 띄워주고
                Toast.makeText(MainActivity.this, item.getText()+ " " +
                        item.getMassage() +" data access", Toast.LENGTH_SHORT).show();
                // AuctionScreen으로 갈 intent 생성 후
                Intent intent = new Intent(MainActivity.this, AuctionScreen.class);
                accountInfo.setGameName(item.getText());            // GameAccountInfo 객체에 게임이름 저장
                accountInfo.setCharacterName(item.getMassage());    // GameAccountInfo 객체에 게임 캐릭터이름 저장
                intent.putExtra("account_info", accountInfo); // intent에 데이터 추가
                startActivity(intent);      // AuctionScreen 실행
            }
        });

        // volley 통신을 수행하려고 응답대기를 준비
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {       // onResponse 함수 재정의
                try {   // 만약 반응이 왔고 정상적이라면,
                    boolean CheckFirst = true;              // 첫번째 데이터인 경우 미리 체크해두기 위한 boolean
                    JSONObject jsonResponse = new JSONObject(response);             // json 형태로 받기 위해 객체 준비
                    JSONArray Ids = jsonResponse.getJSONArray("MyGameList");  // MyGameList key 값으로 배열을 받음

                    for (int i = 0; i < Ids.length(); i++) {        // Ids 길이만큼 반복문 수행
                        JSONObject item = Ids.getJSONObject(i);     // Ids의 데이터를 가져옴
                        String gameName = item.getString("gameName");           // gameName 데이터 저장
                        String gameNickName = item.getString("gameNickname");   // gameNickName 데이터 저장
                        addItem(gameName, gameNickName, CheckFirst);// 리사이클러뷰에 데이터 추가
                        CheckFirst = false;     // 첫번째 수행 후에는 다 false
                    }
                    mAdapter.notifyDataSetChanged();    // 반복문 수행 후에 리스트가 업데이트 됨을 알림
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();    // 오류 추적
                    // 토스트 메세지를 띄워줌
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/MyGameList.php";          // 통신할 php 주소
        // 데이터 전송을 위한 데이터 세팅
        PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getLoginID(), accountInfo.getLoginPW(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this); // 큐를 생성
        queue.add(validateRequest);     // 큐에 추가
    }

    public void addItem(String title, String desc, Boolean checkbox) {  // 리사이클러뷰에 데이터 추가를 위한 함수
        ListViewItem item = new ListViewItem();     // ListViewItem 클래스로부터 객체 생성

        // item.setIcon(icon);      // 이미지 아이콘
        item.setText(title);        // 텍스트1(게임이름)
        item.setMassage(desc);      // 텍스트2(게임캐릭터이름)
        item.setSelected(checkbox); // 체크박스

        mList.add(item);            // 리스트에 아이템 추가
    }

}