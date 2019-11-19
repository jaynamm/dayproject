package com.example.mydailytime_2.calender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mydailytime_2.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MonthGridAdapter extends BaseAdapter {
    private String TAG="MonthGridAdapter";
    private final List<String> list;
    private final LayoutInflater inflater;
    private Context context;

    private GregorianCalendar mCal;
    private Date date;
    private SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    private SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
    SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
    SimpleDateFormat curCurrentFormat= new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
    String today;
    private int yyyy;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int dayNum;

    public int getDayNum() {
        return dayNum;
    }

    public String getYyyy() {
        return String.valueOf(yyyy);
    }

    public String getMonth() {
        return String.format("%02d", month+1);
    }

    public String getDay() {
        return String.valueOf(day);
    }

    public String getHour() {
        return String.valueOf(hour);
    }

    public String getMinute() {
        return String.valueOf(minute);
    }


    /**
     * 생성자
     * @param context
     */
    public MonthGridAdapter(Context context) {
        this.list =  new ArrayList<String>();
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCal = new GregorianCalendar();
        long now = System.currentTimeMillis();
        date = new Date(now);
        today = curCurrentFormat.format(date);

         yyyy = mCal.get(Calendar.YEAR);
         month = mCal.get(Calendar.MONTH); // 월은 0부터 시작합니다.
         day = mCal.get(Calendar.DATE);
         hour = mCal.get(Calendar.HOUR_OF_DAY);
         minute = mCal.get(Calendar.MINUTE);



        Log.d(TAG, yyyy +"-"+ month +"-"+day +" "+hour+":"+minute );
        //연,월,일을 따로 저장
        init();

    }

    private void init(){

        mCal.set(yyyy, month, 1);
        addDayPlace();
        setCalendarDate();
    }

    //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
    private void addDayPlace() {

        dayNum = mCal.get(Calendar.DAY_OF_WEEK);
        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            list.add("");
        }
    }

    //month값을 받고 마지막 날짜만큼 list를 만듦
    private void setCalendarDate() {

        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            list.add("" + (i + 1));
        }
    }

    public void setPreviousMonth() {
        Log.d(TAG, "setPreviousMonth 실행");
        //달력에 한달을 뺀다
        list.clear();
        mCal.add(Calendar.MONTH, -1);
        month = mCal.get(Calendar.MONTH);
        yyyy = mCal.get(Calendar.YEAR);
        addDayPlace();
        setCalendarDate();
        notifyDataSetChanged();

    }

    public void setNextMonth() {
        Log.d(TAG, "setNextMonth: 실행");
        list.clear();
        mCal.add(Calendar.MONTH, 1);
        month = mCal.get(Calendar.MONTH);
        yyyy = mCal.get(Calendar.YEAR);
        addDayPlace();
        setCalendarDate();
        notifyDataSetChanged();

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

    @SuppressLint("ResourceAsColor")
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
        //날짜 삽입 dd
        holder.tvItemGridView.setText("" + getItem(position));
        //해당 날짜 텍스트 컬러,배경 변경
        //오늘 day 가져옴
//        Integer today = mCal.get(Calendar.DAY_OF_MONTH);
//        Log.d(TAG, "getView: DAY_OD_MONTH = "+today);
//        Log.d(TAG, "getView: curCurrentFormat = "+today);
//        String sToday = String.valueOf(today);


        if (today.equals(getItem(position))) { //오늘 day 텍스트 컬러 변경
            holder.tvItemGridView.setTextColor(context.getResources().getColor(R.color.color_000000));
            holder.tvItemGridView.setBackgroundColor(R.color.setColor2);
        }


        return convertView;
    }


    private class ViewHolder {
        TextView tvItemGridView;
    }
}

