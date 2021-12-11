package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class AuctionScreen extends AppCompatActivity {
    private Button moveSellingButton;
    private Button moveMyitemButton;

    ListView listview = null;
    //경매장 리스트 만드는중
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_auction);

        ListViewAdapter adapter;



        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.auctionList);
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

        //EditText에 검색된 값을 받아 함수를 호출
        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString() ;
                /*
                if (filterText.length() > 0) {
                    listview.setFilterText(filterText) ;
                } else {
                    listview.clearTextFilter() ;
                }
                 */
                ((ListViewAdapter)listview.getAdapter()).getFilter().filter(filterText); //ListView를 통하지 않고 Adapter로부터 직접 Filter 객체의 참조를 가져와서 filter() 함수를 호출 하여 텍스트 팝업이 안뜨게 함
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        }) ;

        //리스트 뷰에 있는 아이템 터치시 BuyingScreen popup창 띄우기
        Button buyingbutton = findViewById(R.id.buyButton);
        buyingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = listview.getCheckedItemPosition();
                if (pos > -1) {
                    //ListViewItem item = (ListViewItem) adapter.getItem(pos);
                    //String ItemCode = item.getText();
                    Intent intent = new Intent(AuctionScreen.this, BuyingScreen.class);
                    //intent.putExtra("data", ItemCode);
                    startActivity(intent);
                    //finish();
                }
            }
        });

        //판매중, 내 아이템 이동버튼 구현
        moveSellingButton = (Button)findViewById(R.id.moveSellingButton); // 판매중인 아이템으로 이동버튼
        moveMyitemButton = (Button)findViewById(R.id.moveMyitemButton); // 내 아이템으로 이동버튼

        moveSellingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SellingItemScreen.class);
                startActivity(intent);
            }
        });
        moveMyitemButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ItemCheckScreen.class);
                startActivity(intent);
            }
        });

    }
}