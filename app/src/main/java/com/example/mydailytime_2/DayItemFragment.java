package com.example.mydailytime_2;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mydailytime_2.dummy.DayItemContent;
import com.example.mydailytime_2.dummy.DayItemContent.DayItemVO;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DayItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TODAY_DATE ="today_date";
    private static DayItemFragment dayItemFragment;
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;

    TextView dayItemDate;
    String selectDate;
    public DayItemFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            selectDate=((MainActivity)getActivity()).selectDate;
            dayItemDate.setText(selectDate);
            //해당 프레그먼트가 열리는 순간 $$이때 내부에 있는 데이터들을 바꿔야한다.
        }
        else{

            //해당프레그먼트가 보이지 않게 됐을때
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static DayItemFragment newInstance(String todayDate) {
        if (dayItemFragment != null) {dayItemFragment = new DayItemFragment();}

        Bundle args = new Bundle();
        args.putString(TODAY_DATE,todayDate);
        dayItemFragment.setArguments(args);
        return dayItemFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dayitem_list, container, false);


        dayItemDate = (TextView)view.findViewById(R.id.dayItem_List_Date);
        TextView fragment2Title = (TextView)view.findViewById(R.id.fragment2Title);
        TextView fragment2Memo = (TextView)view.findViewById(R.id.fragment2memo);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.list);
        // Set the adapter
        if (recyclerView instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyDayItemRecyclerViewAdapter(DayItemContent.ITEMS, mListener));
        }

//        if(getArguments()!= null){
//        String selectDate=getArguments().getString(TODAY_DATE);
//        dayItemDate.setText(selectDate);
//        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DayItemVO item);
    }
}
