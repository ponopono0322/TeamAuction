package com.example.teamauction;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AuctionScreen extends AppCompatActivity {
    private Button moveSellingButton;
    private Button moveMyitemButton;

    String[] names = {"아이템 이름들"}; // 아이템 이름들
    Integer[] images = {R.drawable.ic_launcher_background}; // 아이템 이미지
    //경매장 리스트 만드는중
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_auction);

        ListView listView=(ListView)findViewById(R.id.list);


         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), names[+position], Toast.LENGTH_SHORT).show();
            }
         });
         class CustomList extends ArrayAdapter<String> {
             private final Activity context;
             public CustomList(Activity context){
                 super(context, R.layout.auction_list_item, names);
                 this.context = context;
             }
             @Override
             public View getView(int position, View view, ViewGroup parent) {
                 LayoutInflater inflater = context.getLayoutInflater();
                 View rowView= inflater.inflate(R.layout.auction_list_item, null, true);
                 ImageView imageView =(ImageView) rowView.findViewById(R.id.image);
                 TextView name=(TextView) rowView.findViewById(R.id.name);
                 TextView cost=(TextView) rowView.findViewById(R.id.cost);
                 name.setText(names[position]);
                 imageView.setImageResource(images[position]);
                 cost.setText(position);
                 return rowView;
             }
         }
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