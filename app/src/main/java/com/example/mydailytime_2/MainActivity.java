package com.example.mydailytime_2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mydailytime_2.dummy.DayItemContent;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity implements DayItemFragment.OnListFragmentInteractionListener{
    MenuItem prevMenuItem;
    PagerAdapter pagerAdapter;
    private long backPressedTime = 0;
    ViewPager pager;
    String selectDate;
    String todayDateNum;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat mFormatDay = new SimpleDateFormat("dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        todayDateNum = getTime("dd");
        selectDate = getTime(null);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        pager = findViewById(R.id.pager);
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        //프레그먼트를 미리 양쪽으로 2page 씩 생성
        pager.setOffscreenPageLimit(2);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_one:
                            pager.setCurrentItem(0);
                            return true;
                        case R.id.action_two:
                            pager.setCurrentItem(1);
                            return true;
                        case R.id.action_three:
                            pager.setCurrentItem(2);
                            return true;
                    }
                    return false;
                }

            });

        //페이지 넘김시 호출되는 메소드
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            //페이지에 변화가 생겼을때 호출
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onListFragmentInteraction(DayItemContent.DayItemVO item) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
    }


//뒤로가기 버튼
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        long FINISH_INTERVAL_TIME = 2000;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }

    //프레그먼트 갱신
    public void refresh(){
        pagerAdapter.notifyDataSetChanged();
    }

    //get today
    private String getTime(@Nullable String i){
        long mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        if(i!=null) {
            return mFormatDay.format(mDate);
        }
        else {
            return mFormat.format(mDate);
        }

    }
}
