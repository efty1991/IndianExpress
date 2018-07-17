package com.mobdice.indianexpress.network_call.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Channel {

    @Expose
    @SerializedName("content")
    String content;

    @Expose
    @SerializedName("item")
    ArrayList<Item> item;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "content='" + content + '\'' +
                ", item=" + item +
                '}';
    }
}
