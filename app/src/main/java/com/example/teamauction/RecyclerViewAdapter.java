package com.example.teamauction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
/**
 * 리사이클러뷰를 보여주는 클래스
 * 리스트를 구성하는 요소와 보여주는 요소를 지정할 수 있음
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<ListViewItem> mData = null;
    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null;
    // 체크박스 하나만 고르게 하는 것들
    private static CheckBox lastChecked = null;
    protected static int lastCheckedPos = 0;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        // ImageView icon;
        TextView title, desc;       // 텍스트뷰들
        CheckBox checkBox;          // 체크박스
        ImageButton deleteBox;      // 삭제버튼

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            // icon = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);              // 텍스트뷰1
            desc = itemView.findViewById(R.id.desc);                // 텍스트뷰2
            checkBox = itemView.findViewById(R.id.select_account);  // 체크박스
            deleteBox = itemView.findViewById(R.id.deletbox);       // 삭제버튼

            itemView.setOnClickListener(new View.OnClickListener() {    // 아이템 뷰 클릭 이벤트 생성
                @Override
                public void onClick(View view) {        // onClick 함수 재정의
                    int pos = getAdapterPosition();     // 현재 위치 받음
                    if (pos != RecyclerView.NO_POSITION) {  // 리사이클러뷰 내의 위치라면
                        if (mListener!=null) { mListener.onItemClick(view, pos); }  // 이벤트 받음
                    }
                }
            });
            deleteBox.setOnClickListener(new View.OnClickListener() {   // 삭제 버튼 클릭 이벤트 생성
                @Override
                public void onClick(View view) {        // onClick 함수 재정의
                    // 알림창 생성
                    AlertDialog.Builder oDialog = new AlertDialog.Builder(view.getContext());
                    // 알림창 구성
                    oDialog.setMessage("정말로 계정을 삭제하시겠어요?")
                            .setTitle("계정 삭제 알림")
                            .setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(view.getContext(), "계정 삭제를 취소했습니다", Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNeutralButton("예", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(view.getContext(), "계정을 삭제했습니다", Toast.LENGTH_LONG).show();
                                    removeAt(getAdapterPosition());
                                }
                            })
                            .setCancelable(false) // 백버튼으로 팝업창이 닫히지 않도록 한다.
                            .show();              // 알림창 띄우기

                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    RecyclerViewAdapter(ArrayList<ListViewItem> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();      // 부모 객체 받음
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        ListViewItem item = mData.get(position) ;
        //holder.icon.setImageDrawable(item.getIcon()) ;
        holder.title.setText(item.getText()) ;
        holder.desc.setText(item.getMassage()) ;

        BoxChanger(holder, position);

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void BoxChanger(RecyclerViewAdapter.ViewHolder holder, int position) {
        ListViewItem item = mData.get(position) ;
        holder.checkBox.setChecked(item.getSelected());
        holder.checkBox.setTag(new Integer(position));

        //for default check in first item
        if(position == 0 && mData.get(0).getSelected() && holder.checkBox.isChecked()) {
            lastChecked = holder.checkBox;
            lastCheckedPos = 0;
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox)v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked()) {
                    if(lastChecked != null) {
                        lastChecked.setChecked(false);
                        mData.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                mData.get(clickedPos).setSelected(cb.isChecked());
            }
        });
    }

    public void removeAt(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

}

