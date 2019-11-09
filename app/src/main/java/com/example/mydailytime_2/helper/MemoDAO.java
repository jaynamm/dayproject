package com.example.mydailytime_2.helper;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MemoDAO {

    @Query("SELECT * FROM MemoVO ORDER BY MEMO_ID DESC")
    LiveData<List<MemoVO>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MemoVO vo);

    @Update
    void update(MemoVO vo);

    @Delete
    void delete(MemoVO vo);

}
