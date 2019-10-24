package com.example.mydailytime_2.dummy;

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
public class DayItemContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DayItemVO> ITEMS = new ArrayList<DayItemVO>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DayItemVO> ITEM_MAP = new HashMap<String, DayItemVO>();
//아이템 갯수
    private static final int COUNT = 24;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDayItemVO(i));
        }
    }

    private static void addItem(DayItemVO item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.itemTime, item);
    }

    //VO 객체 생성시 쓰는 메소드
    private static DayItemVO createDayItemVO(int position) {
        return new DayItemVO(null, getDateFormat(position), null, null);
    }

    //datitem 시간설정
    private static String getDateFormat(int position) {
        String setTime;
        String text = " 시";
        int timePosition = position;
        if (timePosition < 14){
            setTime = "오전 ";
        }else{
            setTime="오후 ";
            timePosition=timePosition - 12;
        }

        return setTime+String.format("%02d",timePosition-1)+text;
    }

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
    public static class DayItemVO {
        public final String itemDate;
        public final String itemTime;
        public final String itemTitle;
        public final String itemContent;
        public final int itemImg;



        public DayItemVO(String itemDate, String itemTime, String itemTitle, String itemContent) {
            this.itemDate = itemDate;
            this.itemTime =  itemTime;
            this.itemTitle = itemTitle;
            this.itemContent = itemContent;
            this.itemImg = 0;
        }

        @Override
        public String toString() {
            return itemTime+" : "+itemTitle;
        }
    }
}
