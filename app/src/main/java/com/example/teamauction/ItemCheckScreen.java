package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ItemCheckScreen extends AppCompatActivity {
    private GameAccountInfo accountInfo;
    private TextView UserCharName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_check_item);

        ListView listview ;
        ListViewAdapter adapter;

        Intent account_info = getIntent();
        accountInfo = (GameAccountInfo) account_info.getSerializableExtra("account_info");
        String myCharName =accountInfo.getCharacterName();

        UserCharName = findViewById(R.id.MyCharName);
        UserCharName.setText(myCharName);

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.my_item_list);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
                    "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
                    "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_launcher_background),
                    "Ind", "Assignment Ind Black 36dp") ;

        //리스트 뷰에 있는 아이템 터치시 BuyingScreen popup창 띄우기
        Button sellingbutton = findViewById(R.id.sellButton);
        sellingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    //ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    //String ItemCode = item.getText();
                    Intent intent = new Intent(ItemCheckScreen.this, SellingScreen.class);
                    //intent.putExtra("data", ItemCode);
                    startActivity(intent);
                    //finish();
                }
            }
        });
    }
}
