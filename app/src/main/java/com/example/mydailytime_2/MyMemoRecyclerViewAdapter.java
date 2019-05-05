package com.example.mydailytime_2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydailytime_2.dummy.MemoItemContent;
import com.example.mydailytime_2.dummy.MemoItemContent.MemoItemVO;
import com.example.mydailytime_2.helper.MemoContract;
import com.example.mydailytime_2.library.CursorRecyclerViewAdapter;

public class MyMemoRecyclerViewAdapter extends CursorRecyclerViewAdapter<MyMemoRecyclerViewAdapter.ViewHolder>{

//    private final List<MemoItemVO> mValues;
//    private final OnListFragmentInteractionListener mListener;
private memoItemLongClickedListener myMemoItemLongClickedListener;


    private memoItemClickedListener myMemoItemClickedListener;


    interface memoItemClickedListener{
        void memoItemClicked(MemoItemContent.MemoItemVO memoitemVO);
    }
    void setMyMemoItemClickedListener(memoItemClickedListener listener){
        myMemoItemClickedListener=listener;
    }

    interface memoItemLongClickedListener{
        void memoItemLongClicked(MemoItemContent.MemoItemVO memoItemVO);
    }
    void setMyMemoItemLongClickedListener(memoItemLongClickedListener listener){
        myMemoItemLongClickedListener=listener;
    }



    public MyMemoRecyclerViewAdapter(Context context, Cursor cursor) {
        super(context, cursor);

    }
    //레이아웃을 지정해서 BindViewHolder로 넘겨줌
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_memo, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, Cursor cursor) {
        MemoItemVO smemoItemVO = MemoItemVO.fromCursor(cursor);
//        viewHolder.memoItemVO = mValues.get(position);
//        viewHolder.memoTitle.setText(mValues.get(position).memoTitle);
//        viewHolder.memoContent.setText(mValues.get(position).memoContent);
//        viewHolder.memoDate.setText(mValues.get(position).memodate);
        viewHolder.memoItemVO = smemoItemVO;
        viewHolder.memoTitle.setText(smemoItemVO.getMemoTitle());
        viewHolder.memoContent.setText(smemoItemVO.getMemoContent());
        viewHolder.memoDate.setText(smemoItemVO.getMemodate());

        viewHolder.memoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMemoItemClickedListener.memoItemClicked(viewHolder.memoItemVO);
            }
        });
        viewHolder.memoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myMemoItemLongClickedListener.memoItemLongClicked(viewHolder.memoItemVO);
                return true;
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View memoView;
        public final TextView memoTitle;
        public final TextView memoContent;
        public final TextView memoDate;
        public MemoItemVO memoItemVO;

        public ViewHolder(View view) {
            super(view);
            memoView = view;
            memoTitle = (TextView) view.findViewById(R.id.memoTitle);
            memoContent = (TextView) view.findViewById(R.id.memoContent);
            memoDate = (TextView)view.findViewById(R.id.memoTimeItem);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + memoContent.getText() + "'";
        }
    }

}
