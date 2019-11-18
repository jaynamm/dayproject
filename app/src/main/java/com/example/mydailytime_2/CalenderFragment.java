package com.example.mydailytime_2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mydailytime_2.calender.MonthGridAdapter;
import com.example.mydailytime_2.viewModel.DayItemViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment{

    private DayItemViewModel dayItemModel;
    private MonthGridAdapter monthGridAdapter;
    private TextView monthText;

    int clickCount;
    private Date date;
    private SimpleDateFormat curCurrentFormat;
    private SimpleDateFormat curMonthFormat;
    private String TAG = "CalenderFragment";
////    int yearTemp,monthTemp,dayOfMonthTemp;
//    private static final String TODAY_DATE = "today_date";
//     // 월별 캘린더 뷰 객체
//    GridView monthView;
//    //월별 캘린더 어댑터
//    MonthAdapter monthViewAdapter;
//     //월을 표시하는 텍스트뷰
//    TextView monthText;
//    //현재 연도,월
//    int curYear;
//    int curMonth;

    public CalenderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        date = new Date(now);
        //연,월,일을 따로 저장
        curCurrentFormat= new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
        curMonthFormat = new SimpleDateFormat("MM",Locale.KOREA);
        String today = curCurrentFormat.format(date);
        clickCount=0;
//        dayItemModel = ViewModelProviders.of(this).get(DayItemViewModel.class);
//        dayItemModel.setInputDate(today);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        monthText=(TextView)view.findViewById(R.id.monthText);
        ImageButton monthNext = (ImageButton) view.findViewById(R.id.nextMonthButton);
        ImageButton monthPrevious = (ImageButton) view.findViewById(R.id.beforeMonthButton);
        // 연/월 텍스트뷰
        TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        //일 저장 할 리스트
        GridView gridView = (GridView) view.findViewById(R.id.gridview);

        //현재 날짜을 상단 텍스트뷰에 뿌려줌
        String today = curCurrentFormat.format(date);
        tvDate.setText(today);

        //달력 공백을 만들어줄 arraylist

        monthGridAdapter = new MonthGridAdapter(getActivity());
        gridView.setAdapter(monthGridAdapter);
        monthText.setText(monthGridAdapter.getYyyy()+"."+monthGridAdapter.getMonth());


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), "position:"+position+", id"+id, Toast.LENGTH_SHORT).show();
                int myDay =(position+1)-(monthGridAdapter.getDayNum()-1);
                if(myDay>0){
                    String selectDay = monthGridAdapter.getYyyy()+"-"+monthGridAdapter.getMonth()+"-"+String.valueOf(myDay);
                    Log.d(TAG, "onItemClick: selectDay ="+ selectDay);
                    ((MainActivity) Objects.requireNonNull(getActivity())).selectDate=selectDay;
                    clickCount++;
                    if (clickCount==2){
                        clickCount=0;
                        ((MainActivity)getActivity()).pager.setCurrentItem(1);
                    }

                }
            }
        });


        gridView.setOnTouchListener(new View.OnTouchListener() {
            int distance = 250;
            int startY=0;
            int endY=0;
            boolean down,move,up = false;

            private boolean MyMotionEvent(int startY, int endY ){

                if (startY<endY && (endY-startY)>distance ){
                    monthGridAdapter.setNextMonth();
                    Log.d(TAG, "monthNext: "+monthGridAdapter.getMonth());
                    monthText.setText(monthGridAdapter.getYyyy()+"."+monthGridAdapter.getMonth());

                    startY=0;
                    endY=0;
                    down = false;
                    move=false;
                    up = false;
                    return false;
                }
                else if (startY>endY&& (startY-endY)>distance){
                    monthGridAdapter.setPreviousMonth();
                    Log.d(TAG, "monthPrevious: "+monthGridAdapter.getMonth());
                    monthText.setText(monthGridAdapter.getYyyy()+"."+monthGridAdapter.getMonth());

                    startY=0;
                    endY=0;
                    down = false;
                    move=false;
                    up = false;
                    return false;
                }
                return false;
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int px = (int) event.getX();
                int py = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(" MotionEvent " ," ACTION_DOWN");
                        Log.d(TAG, "onTouch: px ="+px+" py ="+ py+"------------------------start---------------------------------");
                        startY = py;
                        down = true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.i(" MotionEvent " ," ACTION_MOVE");
                        move = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.i(" MotionEvent " ," ACTION_UP");
                        Log.d(TAG, "onTouch: px ="+px+" py ="+ py+"-------------------------end--------------------------------");
                        endY=py;
                        up = true;
                        break;
                }
                if (down && move&& up ){
                    return MyMotionEvent(startY,endY);
                }else return  false;
            }

        });

        monthPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthGridAdapter.setPreviousMonth();
                Log.d(TAG, "monthPrevious: "+monthGridAdapter.getMonth());
                monthText.setText(monthGridAdapter.getYyyy()+"."+monthGridAdapter.getMonth());
            }
        });

        monthNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthGridAdapter.setNextMonth();
                Log.d(TAG, "monthNext: "+monthGridAdapter.getMonth());
                monthText.setText(monthGridAdapter.getYyyy()+"."+monthGridAdapter.getMonth());
            }
        });


        ((MainActivity) Objects.requireNonNull(getActivity())).selectDate= today;
        ((MainActivity)getActivity()).pager.setCurrentItem(1); //calender fragment 생성 후 DayitemFragment로 이동
        return view;
    }

}


