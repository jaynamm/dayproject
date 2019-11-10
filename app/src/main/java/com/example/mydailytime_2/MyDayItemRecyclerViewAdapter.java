package com.example.mydailytime_2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.helper.DayItemVO;
import com.google.android.material.internal.CheckableImageButton;

import java.util.ArrayList;
import java.util.List;

public class MyDayItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDayItemRecyclerViewAdapter.ViewHolder> {

    private List<DayItemVO> data;
    private LayoutInflater layoutInflater;
    private Context context;

    private DayItemClickedListener myDayItemClickedListener;
    private DayItemLongClickedListener myDayItemLongClickedListener;
    private ItemImgClickedListener itemImgClickedListener;

    ArrayList<Integer> arrayImg;


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

    interface ItemImgClickedListener{
        void dayItemClicked(DayItemVO dayItemVO);
    }
    void setItemImgClickedListener(ItemImgClickedListener listener){
        itemImgClickedListener=listener;
    }


    public MyDayItemRecyclerViewAdapter(Context context) {
        data = new ArrayList<>();
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayImg=new ArrayList<Integer>();
        arrayImg.add(R.drawable.ic_good);
        arrayImg.add(R.drawable.ic_soso);
        arrayImg.add(R.drawable.ic_bad);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_dayitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        holder.bind(data.get(position));
        holder.dayItemVO=data.get(position);

        holder.dayItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDayItemClickedListener.dayItemClicked(holder.dayItemVO);

            }
        });
        holder.dayItemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myDayItemLongClickedListener.dayItemLongClicked(holder.dayItemVO);
                return true;
            }
        });
//                img clicked event
        holder.dayitemimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DayItemAdapter","imgClicked "+position);
                        itemImgClickedListener.dayItemClicked(holder.dayItemVO);
//                        imgRepalce(holder);
                    }
                });

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
        private DayItemVO dayItemVO;
        private final CheckableImageButton dayitemimg;


         ViewHolder(View view) {
            super(view);
            dayItemView = view;
            dayTitle = (TextView) view.findViewById(R.id.dayItmeTitle);
            dayItemcontent = (TextView) view.findViewById(R.id.dayItemContent);
            dayTime = (TextView) view.findViewById(R.id.dayTimeItem);
            dayitemimg = (CheckableImageButton)view.findViewById(R.id.dayItemImg);
        }

        void bind(final DayItemVO dayItemVO){

                dayTitle.setText(dayItemVO.getItemTitle());
                dayItemcontent.setText(dayItemVO.getItemContent());
                dayitemimg.setImageResource(arrayImg.get(dayItemVO.getItemImg()));
                dayTime.setText(dayItemVO.getItemTime());


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
