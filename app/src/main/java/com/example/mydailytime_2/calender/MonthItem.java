package com.example.mydailytime_2.calender;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * 일자 정보를 담기 위한 클래스 정의
 *
 * @author Mike
 *
 */
public class MonthItem {

    private int dayValue;

    private ArrayList<TextView> calenderDayItem;

    public MonthItem() {

    }

    public MonthItem(int day) {
        dayValue = day;
    }

    public int getDay() {
        return dayValue;
    }

    public void setDay(int day) {
        this.dayValue = day;
    }

    public ArrayList<TextView> getCalenderDayItem() {
        return calenderDayItem;
    }

    public void setCalenderDayItem(ArrayList<TextView> calenderDayItem) {
        this.calenderDayItem = calenderDayItem;
    }



}