package com.mobdice.indianexpress.network_call.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CustomizedItem {
    public CustomizedItem(int type, ArrayList<Item> allItems) {
        this.type = type;
        this.allItems = allItems;
    }
    public CustomizedItem(int type, Item item) {
        this.type = type;
        this.item = item;
    }

    @Expose
    @SerializedName("type")
    private  int type;

    @Expose
    @SerializedName("item")
    private Item item;

    @Expose
    @SerializedName("allItems")
    private ArrayList<Item> allItems;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ArrayList<Item> getAllItems() {
        return allItems;
    }

    public void setAllItems(ArrayList<Item> allItems) {
        this.allItems = allItems;
    }

    @Override
    public String toString() {
        return "CustomizedItem{" +
                "type=" + type +
                ", item=" + item +
                ", allItems=" + allItems +
                '}';
    }
}
