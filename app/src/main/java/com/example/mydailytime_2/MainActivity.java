package com.example.mydailytime_2;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mydailytime_2.dummy.DayItemContent;

public class MainActivity extends AppCompatActivity implements DayItemFragment.OnListFragmentInteractionListener{
    MenuItem prevMenuItem;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        final ViewPager pager = findViewById(R.id.pager);


        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

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

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //페이지 넘김시 호출되는 메소드
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
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
}
