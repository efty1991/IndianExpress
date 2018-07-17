package com.mobdice.indianexpress.network_call.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feeds {
    @Expose
    @SerializedName("rss")
    Rss rss;

    public Rss getRss() {
        return rss;
    }

    public void setRss(Rss rss) {
        this.rss = rss;
    }

    @Override
    public String toString() {
        return "Feeds{" +
                "rss=" + rss +
                '}';
    }
}
