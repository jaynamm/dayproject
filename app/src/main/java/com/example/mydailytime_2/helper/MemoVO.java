package com.example.mydailytime_2.helper;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mydailytime_2.library.TimesTempConverter;

import java.io.Serializable;

@Entity
public class MemoVO implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "MEMO_ID")
    private int memoId;
    @Nullable
    @ColumnInfo(name = "COLUMN_NAME_MEMOTITLE")
    private String memoTitle;
    @Nullable
    @ColumnInfo(name = "COLUMN_NAME_MEMOCONTENTS")
    private String memoContent;
    @ColumnInfo(name = "COLUMN_NAME_MEMODATE")
    @TypeConverters({TimesTempConverter.class})
    private String memoDate;

    public int getMemoId() {
        return memoId;
    }

    public void setMemoId(int memoId) {
        this.memoId = memoId;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public String getMemoContent() {
        return memoContent;
    }

    public void setMemoContent(String memoContent) {
        this.memoContent = memoContent;
    }

    public String getMemoDate() {
        return memoDate;
    }

    public void setMemoDate(String memoDate) {
        this.memoDate = memoDate;
    }

}
