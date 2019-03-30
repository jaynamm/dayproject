package com.example.mydailytime_2.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemoDBHelper extends SQLiteOpenHelper {
    private static MemoDBHelper sInstence;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME="Memo.db";
    private static final String SLQ_CREATE_ENTRIES=
            String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s DATETIME DEFAULT (datetime('now','localtime')))",
            MemoContract.MemoEntry.MEMOTABLE_NAME,
            MemoContract.MemoEntry._ID,
            MemoContract.MemoEntry.COLUCMN_NAME_MEMOTITLE,
            MemoContract.MemoEntry.COLUCMN_NAME_MEMOCONTENTS,
            MemoContract.MemoEntry.COLUCMN_NAME_MEMODATE);

    private static final String SQL_DELETE_MEMOTABLE=
            "DROP TABLE IF EXISTS "+MemoContract.MemoEntry.COLUCMN_NAME_MEMOTITLE;


    public static MemoDBHelper getInstance(Context context){
        if (sInstence == null){
            sInstence = new MemoDBHelper(context);
        }
        return sInstence;
    }

    private MemoDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SLQ_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_MEMOTABLE);
        onCreate(db);
    }
}
