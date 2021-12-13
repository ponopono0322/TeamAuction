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

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    RecyclerViewAdapter mAdapter = null;
    ArrayList<ListViewItem> mList = new ArrayList<ListViewItem>();
    private ActivityResultLauncher<Intent> mStartForResult;
    private int last_pos;
    private GameAccountInfo accountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");

        //액티비티 콜백 함수
        mStartForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            int code_get = intent.getIntExtra("result",0);

                            if(code_get == 0){
                                Toast.makeText(MainActivity.this, getString(R.string.cancel_logout), Toast.LENGTH_SHORT).show();
                            }
                            else if(code_get == 1){
                                Toast.makeText(MainActivity.this, getString(R.string.confirm_logout), Toast.LENGTH_SHORT).show();
                                Intent back_login_page = new Intent(MainActivity.this, MainLoginActivity.class);
                                startActivity(back_login_page);
                                finish();
                            }
                            else if(code_get == 2){
                                Toast.makeText(MainActivity.this, getString(R.string.cancel_access_account), Toast.LENGTH_SHORT).show();
                            }
                            else if(code_get == 3){
                                Toast.makeText(MainActivity.this, getString(R.string.confirm_access_account), Toast.LENGTH_SHORT).show();
                            }
                            else {

                            }
                        }
                    }
                });

        // 로그아웃(뒤로가기) 이미지버튼 눌렀을때
        ImageButton go_logout = findViewById(R.id.go_logout);
        go_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("data", getString(R.string.wanttologout));
                mStartForResult.launch(intent);
            }
        });

        ImageView add_account = findViewById(R.id.add_account);
        add_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                intent.putExtra("account_info", accountInfo);
                startActivity(intent);
            }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        mRecyclerView = findViewById(R.id.linked_id_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(10));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(v.getContext(), "touched"+position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                intent.putExtra("data", getString(R.string.access_account));
                mStartForResult.launch(intent);
            }
        });

        // 로그인 버튼 눌렀을 때
        Button go_login = findViewById(R.id.login_account);
        go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last_pos = RecyclerViewAdapter.lastCheckedPos;
                ListViewItem item = mList.get(last_pos);
                Toast.makeText(MainActivity.this, item.getText()+ " " +
                        item.getMassage() +" data access", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AuctionScreen.class);
                startActivity(intent);
            }
        });


        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    boolean CheckFirst = true;
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray Ids = jsonResponse.getJSONArray("MyGameList");

                    for (int i = 0; i < Ids.length(); i++) {
                        JSONObject item = Ids.getJSONObject(i);
                        String gameName = item.getString("gameName");
                        String gameNickName = item.getString("gameNickname");
                        addItem(gameName, gameNickName, CheckFirst);
                        CheckFirst = false;
                    }
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) { // 접속 오류가 난 것이라면
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "no connection", Toast.LENGTH_SHORT).show();
                }
            }
        };
        String purl = "http://ualsgur98.dothome.co.kr/MyGameList.php";
        PHPRequest validateRequest = new PHPRequest( purl, accountInfo.getLoginID(), accountInfo.getLoginPW(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(validateRequest);
    }

    public void addItem(String title, String desc, Boolean checkbox) {
        ListViewItem item = new ListViewItem();

        // item.setIcon(icon);
        item.setText(title);
        item.setMassage(desc);
        item.setSelected(checkbox);

        mList.add(item);
    }

}