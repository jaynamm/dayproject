package com.example.mydailytime_2.viewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.mydailytime_2.helper.DayItemDAO;
import com.example.mydailytime_2.helper.DayItemDatebase;
import com.example.mydailytime_2.helper.DayItemVO;

import java.util.ArrayList;
import java.util.List;

public class DayItemViewModel extends AndroidViewModel {

    private String TAG = "DayItemViewModel";
    private DayItemDatebase itemDB;

    private MutableLiveData<String> selectDate = new MutableLiveData<>();
    public LiveData<List<DayItemVO>> mSelectDate =
          Transformations.switchMap(selectDate,(date)  -> {
                Log.d(TAG, "Transformation date: " +date);
                return itemDB.dayItemDAO().getDateData(date);
            });

//    Transformations.distinctUntilChanged(Transformations.switchMap(selectDate,(date)  -> {
//        Log.d(TAG, "Transformation date: " +date);
//        return itemDB.dayItemDAO().getDateData(date);
//    }));

    public void setInputDate(String date) {
        Log.d(TAG, "setInputDate: "+date);
        selectDate.setValue(date);
    }

    public DayItemViewModel(@NonNull Application application) {
        super(application);
        itemDB = DayItemDatebase.getInstance(application);
    }

//    public LiveData<List<DayItemVO>> getDateData(String date){
//        return itemDB.dayItemDAO().getDateData(date);
//    }

//    public LiveData<List<DayItemVO>> getAll(){
//       return itemDB.dayItemDAO().getAll();
//    }


    private void insert(List<DayItemVO> itemVO){
        new InsertAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
    }

    public void update(DayItemVO itemVO){
        new UpdateAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
    }

//    public void delete(DayItemVO itemVO){
//        new DeleteAsyncTask(itemDB.dayItemDAO()).execute(itemVO);
//    }

//    public Boolean selectDate(String date){
//        DayItemVO temp=null;
//        try {
//            temp = new SelectDateAsyncTask(itemDB.dayItemDAO()).execute(date).get();
//            Log.d(TAG, "selectDate: " +temp);
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        if(temp!=null){
//            return true;
//        }
//        else {
//            return false;
//        }
//    }


    //calender에서 날짜를 클릭하면 실행되는 메소드 (아규먼트에서 실행되는게 좋으려나 )
    //DB에서 날짜를 대조 후 해당되는 날짜의 데이터가 없으면 새로 만들어준다.
    public void selectDateInsert(String date){
            Log.i("DayItemViewModel", "selectDateInsert item 24개 생성");
            List<DayItemVO> ListTemp = new ArrayList<DayItemVO>();
            for (int i = 0; i < 24; i++) {
                DayItemVO temp = new DayItemVO();
                temp.setItemDate(date);
                temp.setItemTime(createTimeData(i));
                temp.setItemImg(1);
                ListTemp.add(temp);
                Log.d("(selectDateInsert" + i + ")", temp.getItemDate() + "," + temp.getItemTime());
            }
            insert(ListTemp);
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

//    private static class SelectDateAsyncTask extends AsyncTask<String,Void,DayItemVO> {
//
//        private DayItemDAO mDayItemDAO;
//
//        private SelectDateAsyncTask(DayItemDAO mDayItemDAO) {
//            this.mDayItemDAO = mDayItemDAO;
//
//        }
//        @Override
//        protected DayItemVO doInBackground(String... date) {
//            mDayItemDAO.getSelectDateData(date[0]);
//            return null;
//        }
//    }


    private static class InsertAsyncTask extends android.os.AsyncTask<List<DayItemVO>,Void,Void> {

        private DayItemDAO mDayItemDAO;

        private InsertAsyncTask(DayItemDAO mDayItemDAO) {
            this.mDayItemDAO = mDayItemDAO;
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<DayItemVO>... lists) {
            mDayItemDAO.insert(lists[0]);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<DayItemVO,Void,Void> {

        private DayItemDAO mDayItemDAO;

        private UpdateAsyncTask(DayItemDAO mDayItemDAO) {
            this.mDayItemDAO = mDayItemDAO;
        }
        @Override
        protected Void doInBackground(DayItemVO... itemVOS) {
            mDayItemDAO.update(itemVOS[0]);
            return null;
        }
    }
//
//    private static class DeleteAsyncTask extends AsyncTask<DayItemVO,Void,Void>{
//
//        private DayItemDAO mDayItemDAO;
//
//        public DeleteAsyncTask(DayItemDAO mDayItemDAO) {
//            this.mDayItemDAO = mDayItemDAO;
//        }
//        @Override
//        protected Void doInBackground(DayItemVO... itemVOS) {
//            mDayItemDAO.delete(itemVOS[0]);
//            return null;
//        }
//    }
}
