package com.mobdice.indianexpress.network_call.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaThumbnail {
    @Expose
    @SerializedName("url")
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "MediaThumbnail{" +
                "url='" + url + '\'' +
                '}';
    }
}
