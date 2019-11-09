package com.example.mydailytime_2.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mydailytime_2.helper.DayItemDatebase;
import com.example.mydailytime_2.helper.MemoDAO;
import com.example.mydailytime_2.helper.MemoVO;

import java.util.List;

public class MemoViewModel extends AndroidViewModel {

    DayItemDatebase itemDB;

    public MemoViewModel(@NonNull Application application) {
        super(application);
        itemDB = DayItemDatebase.getInstance(application);
    }

    public LiveData<List<MemoVO>> getAll(){
        return itemDB.MemoDAO().getAll();
    }

    public void insert(MemoVO MemoVO){
        new InsertAsyncTask(itemDB.MemoDAO()).execute(MemoVO);
    }

    public void update(MemoVO MemoVO){
        new UpdateAsyncTask(itemDB.MemoDAO()).execute(MemoVO);
    }
    public void delete(MemoVO MemoVO){
        new DeleteAsyncTask(itemDB.MemoDAO()).execute(MemoVO);
    }



    private static class InsertAsyncTask extends AsyncTask<MemoVO,Void,Void> {

        private MemoDAO mMemoDAO;

        private InsertAsyncTask(MemoDAO mMemoDAO) {
            this.mMemoDAO = mMemoDAO;
        }
        @Override
        protected Void doInBackground(MemoVO... MemoVOS) {
            mMemoDAO.insert(MemoVOS[0]);
            return null;
        }


    }
    private static class UpdateAsyncTask extends AsyncTask<MemoVO,Void,Void>{

        private MemoDAO mMemoDAO;

        public UpdateAsyncTask(MemoDAO mMemoDAO) {
            this.mMemoDAO = mMemoDAO;
        }
        @Override
        protected Void doInBackground(MemoVO... MemoVOS) {
            mMemoDAO.update(MemoVOS[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<MemoVO,Void,Void>{

        private MemoDAO mMemoDAO;

        public DeleteAsyncTask(MemoDAO mMemoDAO) {
            this.mMemoDAO = mMemoDAO;
        }
        @Override
        protected Void doInBackground(MemoVO... MemoVOS) {
            mMemoDAO.delete(MemoVOS[0]);
            return null;
        }
    }

}
