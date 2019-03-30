package com.example.mydailytime_2.dummy;

import android.database.Cursor;

import com.example.mydailytime_2.helper.MemoContract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MemoItemContent {

    public static final List<MemoItemVO> ITEMS = new ArrayList<MemoItemVO>();
    public static final Map<String, MemoItemVO>  ITEM_MAP = new HashMap<String, MemoItemVO>();
    private static void addItem(MemoItemVO item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.memoTitle, item);
    }

//    private static MemoItemVO createDummyItem(int position) {
//        return new MemoItemVO(String.valueOf(position), "Item " + position,"Memo Title");
//    }

//    private static String makeDetails(int position) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("Details about Item: ").append(position);
//        for (int i = 0; i < position; i++) {
//            builder.append("\nMore details information here.");
//        }
//        return builder.toString();
//    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MemoItemVO {
        private String memoId;
        private String memoTitle;
        private String memoContent;
        private String memodate;

        public void setMemoTitle(String memoTitle) {
            this.memoTitle = memoTitle;
        }

        public void setMemoContent(String memoContent) {
            this.memoContent = memoContent;
        }

        public void setMemodate(String memodate) {
            this.memodate = memodate;
        }

        public void setMemoId(String memoId) {this.memoId = memoId;}

        public String getMemodate() {
            return memodate;
        }

        public String getMemoContent() {
            return memoContent;
        }

        public String getMemoTitle() {
            return memoTitle;
        }

        public String getMemoId() {
            return memoId;
        }



//        public MemoItemVO(String memoTitle, String memoContent, String memodate) {
//            this.memodate = memodate;
//            this.memoContent = memoContent;
//            this.memoTitle = memoTitle;
//        }


        public static MemoItemVO fromCursor(Cursor cursor) {

            MemoItemVO memoItemVO = new MemoItemVO();
            memoItemVO.setMemoId(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry._ID)));
            memoItemVO.setMemoTitle(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUCMN_NAME_MEMOTITLE)));
            memoItemVO.setMemoContent(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUCMN_NAME_MEMOCONTENTS)));
            memoItemVO.setMemodate(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUCMN_NAME_MEMODATE)));
            return memoItemVO;

        }
        @Override
        public String toString() {
            return memoContent;
        }
    }
}
