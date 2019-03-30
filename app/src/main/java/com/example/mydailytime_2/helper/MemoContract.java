package com.example.mydailytime_2.helper;

import android.provider.BaseColumns;

public class MemoContract {
    public MemoContract() {
    }
    public static class MemoEntry implements BaseColumns {
        public static final String MEMOTABLE_NAME="memo";
        public static final String COLUCMN_NAME_MEMOTITLE="memotitle";
        public static final String COLUCMN_NAME_MEMOCONTENTS="memocontent";
        public static final String COLUCMN_NAME_MEMODATE="memodate";

    }
}
