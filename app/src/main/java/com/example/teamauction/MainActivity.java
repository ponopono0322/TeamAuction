package com.example.teamauction;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    SimpleTextAdapter mAdapter = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();
    private ActivityResultLauncher<Intent> mStartForResult;
    private int last_pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                                Intent back_login_page = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(back_login_page);
                                finish();
                            }
                            else if(code_get == 2){
                                Toast.makeText(MainActivity.this, getString(R.string.cancel_access_account), Toast.LENGTH_SHORT).show();
                            }
                            else if(code_get == 3){
                                Toast.makeText(MainActivity.this, getString(R.string.confirm_access_account), Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(MainActivity.this, AddAccountActivity.class);
                startActivity(intent);
            }
        });

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        mRecyclerView = findViewById(R.id.linked_id_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerViewDecoration(10));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter mAdapter = new SimpleTextAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SimpleTextAdapter.OnItemClickListener() {
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
                last_pos = SimpleTextAdapter.lastCheckedPos;
                RecyclerItem item = mList.get(last_pos);
                Toast.makeText(MainActivity.this, item.getTitle()+" data access", Toast.LENGTH_SHORT).show();
            }
        });

        // 아이템 추가.
        addItem("Box", "Account Box Black 36dp", Boolean.TRUE);
        // 두 번째 아이템 추가.
        addItem("Circle", "Account Circle Black 36dp", Boolean.FALSE);
        // 세 번째 아이템 추가.
        addItem("Ind", "Assignment Ind Black 36dp", Boolean.FALSE);
        // 네 번째 아이템 추가.
        // addItem(getDrawable(R.drawable.ic_baseline_account_box_24), "Ret", "Account Rect Balck 36dp");
        addItem("RCT", "ic_baseline_account_box_24", Boolean.FALSE);

        mAdapter.notifyDataSetChanged() ;
    }

    public void addItem(String title, String desc, Boolean checkbox) {
        RecyclerItem item = new RecyclerItem();

        // item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);
        item.setSelected(checkbox);

        mList.add(item);
    }

}