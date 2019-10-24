package com.example.mydailytime_2;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalenderFragment extends Fragment{

    /**
     * 연/월 텍스트뷰
     */
    private TextView tvDate;
    /**
     * 그리드뷰 어댑터
     */
    private GridAdapter gridAdapter;
    /**
     * 일 저장 할 리스트
     */
    private ArrayList<String> dayList;
    /**
     * 그리드뷰
     */
    private GridView gridView;
    /**
     * 캘린더 변수
     */
    private Calendar mCal;

    private ImageButton monthNext;
    private ImageButton monthPrevious;
    private TextView monthText;

    SimpleDateFormat curYearFormat;
    SimpleDateFormat curMonthFormat;
    SimpleDateFormat curDayFormat;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        monthText=(TextView)view.findViewById(R.id.monthText);
        monthNext=(ImageButton)view.findViewById(R.id.nextMonthButton);
        monthPrevious=(ImageButton)view.findViewById(R.id.beforeMonthButton);
        tvDate = (TextView)view.findViewById(R.id.tv_date);
        gridView = (GridView)view.findViewById(R.id.gridview);
        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        //연,월,일을 따로 저장
        curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        //현재 날짜을 상단 텍스트뷰에 뿌려줌
        tvDate.setText(curYearFormat.format(date) + "/" + curMonthFormat.format(date) +"/"+ curDayFormat.format(date));

        //달력 공백을 만들어줄 arraylist
        dayList = new ArrayList<String>();
        mCal = Calendar.getInstance();
        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        setCalendarDate(mCal.get(Calendar.MONTH) + 1);
        gridAdapter = new GridAdapter(getActivity(), dayList);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "position:"+position+", id"+id, Toast.LENGTH_SHORT).show();

            }
        });

//
//// 월별 캘린더 뷰 객체 참조
////        monthView = (GridView)view.findViewById(R.id.monthView);
//        monthViewAdapter = new MonthAdapter(getActivity());
//        monthView.setAdapter(monthViewAdapter);
//// 오늘날짜 그리드뷰에 색상
//        monthViewAdapter.setSelectedPosition(Integer.parseInt(((MainActivity) getActivity()).todayDateNum));
//
//        // 리스너 설정
//        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // 현재 선택한 일자 정보 표시
//                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
//                int day = curItem.getDay();
//                String selectItemDate = curYear+"-"+String.format("%02d",curMonth+1)+"-"+day;
//                Toast.makeText(getActivity(), ""+selectItemDate, Toast.LENGTH_SHORT).show();
//                monthViewAdapter.setSelectedPosition(position);
//                monthViewAdapter.notifyDataSetChanged();
//                ((MainActivity) Objects.requireNonNull(getActivity())).selectDate = selectItemDate;
//
//            }
//        });
//
//

        ((MainActivity)getActivity()).pager.setCurrentItem(1); //calender fragment 생성 후 DayitemFragment로 이동
        return view;
    }

    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month - 1);

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }

    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;
        Calendar mCalendar;

        /**
         * 생성자
         * @param context
         * @param list
        */
        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fragment_calender_item, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.tv_item_gridview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tvItemGridView.setText("" + getItem(position));
            //해당 날짜 텍스트 컬러,배경 변경
            mCal = Calendar.getInstance();
            //오늘 day 가져옴
            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);
            if (sToday.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
                holder.tvItemGridView.setTextColor(getResources().getColor(R.color.color_000000));  
            }
            return convertView;
        }

    }


    private class ViewHolder {
        TextView tvItemGridView;
    }

}


