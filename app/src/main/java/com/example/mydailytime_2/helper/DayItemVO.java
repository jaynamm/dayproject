package com.example.mydailytime_2.helper;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DayItemVO implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ITEM_ID")
    private int id;
    @ColumnInfo(name = "ITEM_DATE")
    private String itemDate;
    @ColumnInfo(name = "ITEM_TIME")
    private String itemTime;
    @ColumnInfo(name = "ITEM_TABLE")
    private String itemTitle;
    @ColumnInfo(name = "ITEM_CONTENT")
    private String itemContent;
    @ColumnInfo(name = "ITEM_IMG")
    private int itemImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemContent() {
        return itemContent;
    }

    public void setItemContent(String itemContent) {
        this.itemContent = itemContent;
    }

    public int getItemImg() {
        return itemImg;
    }

    public void setItemImg(int itemImg) {
        this.itemImg = itemImg;
    }

    @NonNull
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DayItemVO{");
        sb.append("id=").append(id);
        sb.append(", date=").append(itemDate);
        sb.append(", time=").append(itemTime);
        sb.append(", title=").append(itemTitle);
        sb.append(", Content=").append(itemContent);
        sb.append(", img_Int=").append(itemImg);
        sb.append('/');
        return sb.toString();
    }
}
