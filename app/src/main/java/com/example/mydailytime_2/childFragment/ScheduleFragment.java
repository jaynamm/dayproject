package com.example.mydailytime_2.childFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mydailytime_2.R;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScheduleFragment extends Fragment {

    private static final String SELECT_DATE = "select_date";

    public ScheduleFragment() {
    }

    public static ScheduleFragment newInstance(String date) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(SELECT_DATE,date);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule,container,false);

        ListView listView = (ListView)view.findViewById(R.id.ScheduleList);
        MaterialButton button = (MaterialButton)view.findViewById(R.id.addSCDbnt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.schedule_item);
        listView.setAdapter(adapter);



        return view;
    }



}
