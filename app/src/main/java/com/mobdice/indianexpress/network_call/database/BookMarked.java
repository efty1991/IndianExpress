package com.mobdice.indianexpress.network_call.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class BookMarked {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "post_id")
    String postId;

    @ColumnInfo(name = "item")
    String itemData;

    @ColumnInfo(name = "time")
    String timeStamp;

    public BookMarked(String postId, String itemData, String timeStamp) {
        this.postId = postId;
        this.itemData = itemData;
        this.timeStamp = timeStamp;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getItemData() {
        return itemData;
    }

    public void setItemData(String itemData) {
        this.itemData = itemData;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "BookMarked{" +
                "postId=" + postId +
                ", itemData='" + itemData + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
