package com.example.mydailytime_2;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydailytime_2.calender.MonthAdapter;
import com.example.mydailytime_2.calender.MonthItem;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment{
//    int yearTemp,monthTemp,dayOfMonthTemp;
    private static final String TODAY_DATE = "today_date";


     // 월별 캘린더 뷰 객체
    GridView monthView;
    //월별 캘린더 어댑터
    MonthAdapter monthViewAdapter;
     //월을 표시하는 텍스트뷰
    TextView monthText;
    //현재 연도,월
    int curYear;
    int curMonth;




    public CalenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        ((MainActivity)getActivity()).pager.setCurrentItem(1);
//        MutableLiveData<ArrayList<Object>> mCalendarList= new MutableLiveData<>();
//        CalendarView calendarView = (CalendarView)view.findViewById(R.id.calendarView);
//
////        FragmentManager manager = getChildFragmentManager();
////        ScheduleFragment scheduleFragment = (ScheduleFragment)manager.findFragmentById(R.id.scheduleFragment);
//
//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//
//                String todayYearTemp = Integer.toString(year);
//                String todayMonthTemp = String.format("%02d",month+1);
//                String todayDayOfMonthTemp = Integer.toString(dayOfMonth);
//                String selectDate = todayYearTemp+todayMonthTemp+todayDayOfMonthTemp;
//            //같은날짜가 선택됐을때
//            if(yearTemp == year && monthTemp == month && dayOfMonthTemp==dayOfMonth){
//
////                Bundle bundle = new Bundle();
//
////                DayItemFragment dayItemFragment = DayItemFragment.newInstance(selectDate);
//
//                Toast.makeText(getActivity(), "같은 날짜 선택 : "+todayDateNum, Toast.LENGTH_SHORT).show();
//
//                //todayDate를 MainActivity에 저장 후 //DayItemFragment로 이동
//                ((MainActivity)getActivity()).selectDate = todayDateNum;
//                ((MainActivity)getActivity()).pager.setCurrentItem(1);
//            }else{
//                Toast.makeText(getActivity(), todayDateNum, Toast.LENGTH_SHORT).show();
//                Fragment fm = ScheduleFragment.newInstance(todayDateNum);
//                setChildFragment(fm);
//            }
//
//            yearTemp = year;
//            monthTemp = month;
//            dayOfMonthTemp=dayOfMonth;
//            }
//        });


// 월별 캘린더 뷰 객체 참조
        monthView = (GridView)view.findViewById(R.id.monthView);
        monthViewAdapter = new MonthAdapter(getActivity());
        monthView.setAdapter(monthViewAdapter);
// 오늘날짜 그리드뷰에 색상
        monthViewAdapter.setSelectedPosition(Integer.parseInt(((MainActivity) getActivity()).todayDateNum));

        // 리스너 설정
        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                int day = curItem.getDay();
                String selectItemDate = curYear+"-"+String.format("%02d",curMonth+1)+"-"+day;
                Toast.makeText(getActivity(), ""+selectItemDate, Toast.LENGTH_SHORT).show();
                monthViewAdapter.setSelectedPosition(position);
                monthViewAdapter.notifyDataSetChanged();
                ((MainActivity) Objects.requireNonNull(getActivity())).selectDate = selectItemDate;

            }
        });


        monthText = (TextView)view.findViewById(R.id.monthText);
        setMonthText();
//
//        // 이전 월로 넘어가는 이벤트 처리
//        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
//        monthPrevious.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                monthViewAdapter.setPreviousMonth();
//                monthViewAdapter.notifyDataSetChanged();
//
//                setMonthText();
//            }
//        });
//
//        // 다음 월로 넘어가는 이벤트 처리
//        Button monthNext = (Button) findViewById(R.id.monthNext);
//        monthNext.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                monthViewAdapter.setNextMonth();
//                monthViewAdapter.notifyDataSetChanged();
//
//                setMonthText();
//            }
//        });


        return view;
    }




    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }

}
