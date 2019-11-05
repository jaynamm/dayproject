package com.example.mydailytime_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.helper.DayItemVO;

import java.util.ArrayList;
import java.util.List;

public class MyDayItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDayItemRecyclerViewAdapter.ViewHolder> {

    private List<DayItemVO> data;
    private LayoutInflater layoutInflater;
    private Context context;

    DayItemClickedListener myDayItemClickedListener;
    DayItemLongClickedListener myDayItemLongClickedListener;

    interface DayItemClickedListener{
        void dayItemClicked(DayItemVO dayItemVO);
    }
    void setMyDayItemClickedListener(DayItemClickedListener listener){
        myDayItemClickedListener=listener;
    }

    interface DayItemLongClickedListener {
        void dayItemLongClicked(DayItemVO dayItemVO);
    }
    void setMyDayItemLongClickedListener(DayItemLongClickedListener listener){
        myDayItemLongClickedListener=listener;
    }


    public MyDayItemRecyclerViewAdapter(Context context) {
        data = new ArrayList<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_dayitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View dayItemView;
        private final TextView dayTitle;
        private final TextView dayItemcontent;
        private final TextView dayTime;
//        private final CheckableImageButton dayitemimg;


         ViewHolder(View view) {
            super(view);
            dayItemView = view;
            dayTitle = (TextView) view.findViewById(R.id.dayItmeTitle);
            dayItemcontent = (TextView) view.findViewById(R.id.dayItemContent);
            dayTime = (TextView) view.findViewById(R.id.dayTimeItem);
//            dayitemimg = (CheckableImageButton)view.findViewById(R.id.dayItemImg);
        }

        void bind(DayItemVO dayItemVO){

            if(dayItemVO != null);{
                dayTitle.setText(dayItemVO.getItemTitle());
                dayItemcontent.setText(dayItemVO.getItemContent());
//                dayitemimg.setImageAlpha(dayItemVO.getItemImg());
                dayTime.setText(dayItemVO.getItemTime());
                dayItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDayItemClickedListener.dayItemClicked(dayItemVO);

                    }
                });
                dayItemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        myDayItemLongClickedListener.dayItemLongClicked(dayItemVO);
                        return true;
                    }
                });
//                img clicked event
//                dayitemimg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
            }
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dayItemcontent.getText() + "'";
        }
    }

    public void setData(List<DayItemVO> newData) {
        if (data != null) {
            DayItemDiffCallback dayItemDiffCallback = new DayItemDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(dayItemDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            data = newData;
        }
    }

    class DayItemDiffCallback extends DiffUtil.Callback{

         private final List<DayItemVO> newDayItem, oldDayItem;

        DayItemDiffCallback(List<DayItemVO> newDayItem, List<DayItemVO> oldDayItem) {
            this.newDayItem = newDayItem;
            this.oldDayItem = oldDayItem;
        }

        @Override
        public int getOldListSize() {
            return oldDayItem.size();
        }

        @Override
        public int getNewListSize() {
            return newDayItem.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldDayItem.get(oldItemPosition).getId() == newDayItem.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldDayItem.get(oldItemPosition).equals(newDayItem.get(newItemPosition));
        }
    }
}
