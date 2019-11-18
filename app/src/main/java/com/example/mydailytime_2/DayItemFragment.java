package com.example.mydailytime_2;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mydailytime_2.dialog.InputDayItemDialog;
import com.example.mydailytime_2.helper.DayItemVO;
import com.example.mydailytime_2.viewModel.DayItemViewModel;

import java.util.Objects;


public class DayItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TODAY_DATE ="today_date";
    private static final String TAG = "DayItemFragment";
    private static DayItemFragment dayItemFragment;
    MyDayItemRecyclerViewAdapter mDayItemAdapter;
    DayItemViewModel dayItemModel;

    TextView dayItemDate;
    String selectDate;
    public DayItemFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            selectDate=((MainActivity) Objects.requireNonNull(getActivity())).selectDate;
            dayItemDate.setText(selectDate);

            //observe data change
            dayItemModel.setInputDate(selectDate);
//            dayItemModel.selectDateInsert(selectDate);
//            mDayItemAdapter.notifyDataSetChanged();
            //해당 프레그먼트가 열리는 순간 $$이때 내부에 있는 데이터들을 바꿔야한다.
        }

        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectDate=((MainActivity) Objects.requireNonNull(getActivity())).selectDate;
        dayItemModel = ViewModelProviders.of(this).get(DayItemViewModel.class);
        dayItemModel.setInputDate(selectDate);
        dayItemModel.mSelectDate.observe(this, dayItemVOS -> {
            Log.d(TAG, "observe 쿼리 실행 .....dayItemVOS"+dayItemVOS.toString());
            if (!mDayItemAdapter.setData(dayItemVOS)){
                Log.i("DayItemFregment","selectDateInsert 실행");
                dayItemModel.selectDateInsert(selectDate);
                //todo transformation에서 dayItemVOS를 최신화 시키지 못해서 오류가 나는 것같음....

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dayitem_list, container, false);

        //viewmodel 생성
        dayItemDate = (TextView)view.findViewById(R.id.dayItem_List_Date);
        TextView fragment2Title = (TextView)view.findViewById(R.id.fragment2Title);
//        TextView fragment2Memo = (TextView)view.findViewById(R.id.fragment2memo);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.list);

            Context context = view.getContext();
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mDayItemAdapter = new MyDayItemRecyclerViewAdapter(context);
            recyclerView.setAdapter(mDayItemAdapter);



//         final Observer<List<DayItemVO>> dateObserver = new Observer<List<DayItemVO>>() {
//             @Override
//             public void onChanged(List<DayItemVO> dayItemVOS) {
//
//             }
//         };

        //day item 데이터 베이스의 데이터 교체시 콜백함수 (데이터 관찰)
//        dayItemModel.getDateData(selectDate).observe(this, dayItemVOS -> {
//            mDayItemAdapter.setData(dayItemVOS);
//            if (mDayItemAdapter.getItemCount()==0){
//                Log.i("DayItemFregment","selectDateInsert 실행");
//                dayItemModel.selectDateInsert(selectDate);
//            }
//        });



        //클릭리스너
        mDayItemAdapter.setMyDayItemClickedListener(new MyDayItemRecyclerViewAdapter.DayItemClickedListener() {
            @Override
            public void dayItemClicked(DayItemVO dayItemVO) {
                Log.d("DayItemFragment", "dayItemClicked:"+dayItemVO.getItemTime());
                showInputDayItemDialog(dayItemVO);
                Toast.makeText(context, ""+dayItemVO.getItemDate(), Toast.LENGTH_SHORT).show();
            }
        });

        mDayItemAdapter.setItemImgClickedListener(new MyDayItemRecyclerViewAdapter.ItemImgClickedListener() {
            @Override
            public void dayItemClicked(DayItemVO dayItemVO) {
                int tempNum = dayItemVO.getItemImg();
                if(tempNum>=2){
                    tempNum=0;
                }else{
                    tempNum++;
                }

                dayItemVO.setItemImg(tempNum);
                dayItemModel.update(dayItemVO);
            }
        });

//        //롱클릭 리스너 delete 구문인데 필요할까?
//        mDayItemAdapter.setMyDayItemLongClickedListener(new MyDayItemRecyclerViewAdapter.DayItemLongClickedListener() {
//            @Override
//            public void dayItemLongClicked(DayItemVO dayItemVO) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("메모 삭제");
//                builder.setMessage("메모를 삭제하시겠습니까?");
//                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dayItemModel.delete(dayItemVO);
//                    }
//                });
//                builder.setNegativeButton("취소" ,null);
//                builder.show();
//
//            }
//        });
        return view;
    }

    private void showInputDayItemDialog(DayItemVO dayItemVO) {
        InputDayItemDialog inputDayItemDialog = InputDayItemDialog.newInstance(dayItemVO);
        inputDayItemDialog.setOnSaveButtonClickListener(new InputDayItemDialog.onSaveButtonClickListener() {
            @Override
            public void onSaveButtonClick(DayItemVO dayItemVO) {
                dayItemModel.update(dayItemVO);
            }

            @Override
            public void onCancelButtonClick() {
                Toast.makeText(getActivity(), "cancel button", Toast.LENGTH_SHORT).show();
            }
        });
        inputDayItemDialog.show(getFragmentManager(), "inputDayItemDialog_new");
            }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
