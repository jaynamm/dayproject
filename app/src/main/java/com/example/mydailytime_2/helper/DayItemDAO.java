package com.example.mydailytime_2.helper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DayItemDAO {

    @Query("SELECT * FROM DayItemVO")
    LiveData<List<DayItemVO>> getAll();

    @Query("SELECT * FROM DayItemVO WHERE DATE(ITEM_DATE)=(:today) ORDER BY ITEM_TIME ASC")
    LiveData<List<DayItemVO>> getDateData(String today);

    @Query("SELECT DISTINCT * FROM DayItemVO WHERE DATE(ITEM_DATE)=(:today)")
    List<DayItemVO> getSelectDateData(String today);

    @Insert
    void insert(List<DayItemVO> vo);

    @Update
    void update(DayItemVO vo);

    @Delete
    void delete(DayItemVO vo);

}
