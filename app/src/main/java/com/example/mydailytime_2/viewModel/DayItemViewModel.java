package com.example.mydailytime_2.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mydailytime_2.helper.DayItemDAO;
import com.example.mydailytime_2.helper.DayItemDatebase;
import com.example.mydailytime_2.helper.DayItemVO;

import java.util.List;

public class DayItemViewModel extends AndroidViewModel {

    private DayItemDatebase itemDB;

    public DayItemViewModel(@NonNull Application application) {
        super(application);
        itemDB = DayItemDatebase.getInstance(application);
    }

    public LiveData<List<DayItemVO>> getDateData(String date){
        return itemDB.dayItemDAO().getDateData(date);
    }

    public LiveData<List<DayItemVO>> getAll(){
       return itemDB.dayItemDAO().getAll();
    }

    public void insert(DayItemVO itemVO){
        new InsertAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
    }

    public void update(DayItemVO itemVO){
        new UpdateAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
    }
    public void delete(DayItemVO itemVO){
        new DeleteAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
    }

    //calender에서 날짜를 클릭하면 실행되는 메소드 (아규먼트에서 실행되는게 좋으려나 )
    //DB에서 날짜를 대조 후 해당되는 날짜의 데이터가 없으면 새로 만들어준다.
    public void selectDateInsert(String date){
        if(itemDB.dayItemDAO().getDateData(date)==null){
            Log.i("DayItemViewModel","selectDateInsert item 24개 생성");
            for(int i= 0; i<24; i++) {
                DayItemVO temp = new DayItemVO();
                temp.setItemDate(date);
                temp.setItemTime(createTimeData(i));
                insert(temp);
            }
        }
        else {
            Log.i("DayItemViewModel", "날짜에 해당되는 DayItem 데이터를 가져옴");
        }

    }
        private static String createTimeData(int position) {
            String setTime;
            String text = " 시";
            int timePosition = position;
            if (timePosition < 12){
                setTime = "오전 ";
            }else{
                setTime="오후 ";
                timePosition=timePosition - 12;
            }

            return String.format("%s%s%s", setTime, String.format("%02d", timePosition), text);
}


    private static class InsertAsyncTask extends AsyncTask<DayItemVO,Void,Void>{

        private DayItemDAO mDayItemDAO;

        private InsertAsyncTask(DayItemDAO mDayItemDAO) {
            this.mDayItemDAO = mDayItemDAO;
        }
        @Override
        protected Void doInBackground(DayItemVO... itemVOS) {
            mDayItemDAO.insert(itemVOS[0]);
            return null;
        }


    }
    private static class UpdateAsyncTask extends AsyncTask<DayItemVO,Void,Void>{

        private DayItemDAO mDayItemDAO;

        public UpdateAsyncTask(DayItemDAO mDayItemDAO) {
            this.mDayItemDAO = mDayItemDAO;
        }
        @Override
        protected Void doInBackground(DayItemVO... itemVOS) {
            mDayItemDAO.update(itemVOS[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<DayItemVO,Void,Void>{

        private DayItemDAO mDayItemDAO;

        public DeleteAsyncTask(DayItemDAO mDayItemDAO) {
            this.mDayItemDAO = mDayItemDAO;
        }
        @Override
        protected Void doInBackground(DayItemVO... itemVOS) {
            mDayItemDAO.delete(itemVOS[0]);
            return null;
        }
    }
}
