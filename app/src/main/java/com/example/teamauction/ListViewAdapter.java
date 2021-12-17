package com.example.teamauction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
/**
 * 리스트뷰를 보여주는 클래스
 * 리스트를 구성하는 요소와 보여주는 요소를 지정할 수 있음
 */
public class ListViewAdapter extends BaseAdapter implements Filterable {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // 필터링된 결과 데이터를 저장하기 위한 ArrayList. 최초에는 전체 리스트 보유.
    private ArrayList<ListViewItem> filteredItemList = listViewItemList ;
    Filter listFilter ;
    // ListViewAdapter의 생성자
    public ListViewAdapter() {
    }

    // Adapter에 사용되는 데이터의 개수를 리턴
    @Override
    public int getCount() {
        return filteredItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position; //위치 값 받음
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.list_imageview); // 이미지의 위치 값 참조 획득
        TextView textTextView = (TextView) convertView.findViewById(R.id.list_textview1); // 첫번째 텍스트뷰의 위치 값 참조 획득
        TextView textViewView = (TextView) convertView.findViewById(R.id.list_textview2); // 두번째 텍스트뷰의 위치 값 참조 획득

        // Data Set(filteredItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = filteredItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIcon()); // 이미지 값 반영
        textTextView.setText(listViewItem.getText()); // 첫번째 텍스트뷰 텍스트 값 반영
        textViewView.setText(listViewItem.getMassage()); // 두번째 텍스트뷰 텍스트 값 반영

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position) ;
    }

    // 리스트뷰 아이템 데이터 추가를 위한 함수
    public void addItem(Drawable icon, String text, String msg) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon); //이미지 데이터 추가
        item.setText(text); //첫번째 텍스트 데이터 추가
        item.setMassage(msg); //두번째 텍스트 데이터 추가
        listViewItemList.add(item);
    }

    //getFilter() 함수를 override
    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }
        return listFilter ;
    }

    //Adapter 내부에 커스텀 Filter 클래스를 정의하고 검색창 기능구현
    private class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;
            if (constraint == null || constraint.length() == 0) { //내용물이 없을 경우
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>() ;
                for (ListViewItem item : listViewItemList) {
                    if (item.getText().toUpperCase().contains(constraint.toString().toUpperCase()) ||
                            item.getMassage().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item) ; //리스트뷰에 추가
                    }
                }
                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results; // 결과값 리턴
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            //리스트뷰에 데이터 업데이트
            filteredItemList = (ArrayList<ListViewItem>) results.values ;

            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }

    //DB에서 가져온 값 아이템 데이터 추가를 위한 함수
    public void addAuctionItem(Drawable icon, String text, String msg, String uninum, String regnum) {
        ListViewItem item = new ListViewItem();
        item.setIcon(icon);
        item.setText(text);
        item.setMassage(msg);
        item.setUninumber(uninum);
        item.setRegnumber(regnum);
        listViewItemList.add(item);
    }
}

