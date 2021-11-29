package com.example.teamauction;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null;
    SimpleTextAdapter mAdapter = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton go_logout = findViewById(R.id.go_logout);
        go_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
                //Toast.makeText(v.getContext(), String.valueOf(position) , Toast.LENGTH_SHORT).show();
            }
        });

        // 아이템 추가.
        addItem("Box", "Account Box Black 36dp");
        // 두 번째 아이템 추가.
        addItem("Circle", "Account Circle Black 36dp");
        // 세 번째 아이템 추가.
        addItem("Ind", "Assignment Ind Black 36dp");
        // 네 번째 아이템 추가.
        // addItem(getDrawable(R.drawable.ic_baseline_account_box_24), "Ret", "Account Rect Balck 36dp");


        mAdapter.notifyDataSetChanged() ;
    }

    public void addItem(String title, String desc) {
        RecyclerItem item = new RecyclerItem();

        // item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        mList.add(item);
    }

}