package com.example.mydailytime_2;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydailytime_2.DayItemFragment.OnListFragmentInteractionListener;
import com.example.mydailytime_2.dummy.DayItemContent.DayItemVO;
import com.google.android.material.internal.CheckableImageButton;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DayItemVO} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyDayItemRecyclerViewAdapter extends RecyclerView.Adapter<MyDayItemRecyclerViewAdapter.ViewHolder> {

    private final List<DayItemVO> dayItemVl;
    private final OnListFragmentInteractionListener mListener;

    public MyDayItemRecyclerViewAdapter(List<DayItemVO> items, OnListFragmentInteractionListener listener) {
        dayItemVl = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_dayitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = dayItemVl.get(position);
        holder.dayTitle.setText(dayItemVl.get(position).itemTitle);
        holder.dayItemcontent.setText(dayItemVl.get(position).itemContent);
        holder.dayTime.setText(dayItemVl.get(position).itemTime);

        holder.dayItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayItemVl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View dayItemView;
        public final TextView dayTitle;
        public final TextView dayItemcontent;
        public final TextView dayTime;
        public final CheckableImageButton dayitemimg;
        public DayItemVO mItem;

        public ViewHolder(View view) {
            super(view);
            dayItemView = view;
            dayTitle = (TextView) view.findViewById(R.id.dayItmeTitle);
            dayItemcontent = (TextView) view.findViewById(R.id.dayItemContent);
            dayTime = (TextView) view.findViewById(R.id.dayTimeItem);
            dayitemimg = (CheckableImageButton)view.findViewById(R.id.dayItemImg);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + dayItemcontent.getText() + "'";
        }
    }
}
