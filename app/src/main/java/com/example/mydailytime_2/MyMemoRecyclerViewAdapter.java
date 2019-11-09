package com.example.mydailytime_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.helper.MemoVO;

import java.util.ArrayList;
import java.util.List;

public class MyMemoRecyclerViewAdapter extends RecyclerView.Adapter<MyMemoRecyclerViewAdapter.ViewHolder>{

    private final LayoutInflater layoutInflater;
    private Context context;
    //    private final List<MemoItemVO> mValues;
//    private final OnListFragmentInteractionListener mListener;
    private memoItemLongClickedListener myMemoItemLongClickedListener;
    private memoItemClickedListener myMemoItemClickedListener;
    private List<MemoVO> data;

    public MyMemoRecyclerViewAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    interface memoItemClickedListener{
        void memoItemClicked(MemoVO memoVO);
    }
    void setMyMemoItemClickedListener(memoItemClickedListener listener){
        myMemoItemClickedListener=listener;
    }

    interface memoItemLongClickedListener{
        void memoItemLongClicked(MemoVO memoVO);
    }
    void setMyMemoItemLongClickedListener(memoItemLongClickedListener listener){
        myMemoItemLongClickedListener=listener;
    }

    //레이아웃을 지정해서 BindViewHolder로 넘겨줌
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_memo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<MemoVO> newData) {
        if (data != null) {
            PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            data = newData;
        }
    }

    class PostDiffCallback extends DiffUtil.Callback {

        private final List<MemoVO> oldPosts, newPosts;

        public PostDiffCallback(List<MemoVO> oldPosts, List<MemoVO> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).getMemoId() == newPosts.get(newItemPosition).getMemoId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View memoView;
        public final TextView memoTitle;
        public final TextView memoContent;
        public final TextView memoDate;
        public MemoVO memoItemVO;

        public ViewHolder(View view) {
            super(view);
            memoView = view;
            memoTitle = (TextView) view.findViewById(R.id.memoTitle);
            memoContent = (TextView) view.findViewById(R.id.memoContent);
            memoDate = (TextView)view.findViewById(R.id.memoTimeItem);
        }

    void bind(final MemoVO memoVo){

        memoTitle.setText(memoVo.getMemoTitle());
        memoContent.setText(memoVo.getMemoContent());
        memoDate.setText(memoVo.getMemoDate());

        memoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMemoItemClickedListener.memoItemClicked(memoItemVO);
            }
        });
        memoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myMemoItemLongClickedListener.memoItemLongClicked(memoItemVO);
                return true;
            }
        });
    }


        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + memoContent.getText() + "'";
        }
    }

}
