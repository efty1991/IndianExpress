package com.mobdice.indianexpress.network_call.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {


    @Expose
    @SerializedName("content")
    String content;

    @Expose
    @SerializedName("media:large")
    MediaLarge medialLarge;


    @Expose
    @SerializedName("media:big")
    MediaBig medialBig;


    @Expose
    @SerializedName("media:thumbnail")
    MediaThumbnail medialThumbnail;

    @Expose
    @SerializedName("link")
    String link;

    @Expose
    @SerializedName("description")
    String description;

    @Expose
    @SerializedName("title")
    String title;

    @Expose
    @SerializedName("content:encoded")
    String contentEncoded;
    @Expose
    @SerializedName("postid")
    String postid;

    @Expose
    @SerializedName("pubDate")
    String pubDate;

    boolean isBookMarked = false;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MediaLarge getMedialLarge() {
        return medialLarge;
    }

    public void setMedialLarge(MediaLarge medialLarge) {
        this.medialLarge = medialLarge;
    }

    public MediaBig getMedialBig() {
        return medialBig;
    }

    public void setMedialBig(MediaBig medialBig) {
        this.medialBig = medialBig;
    }

    public MediaThumbnail getMedialThumbnail() {
        return medialThumbnail;
    }

    public void setMedialThumbnail(MediaThumbnail medialThumbnail) {
        this.medialThumbnail = medialThumbnail;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentEncoded() {
        return contentEncoded;
    }

    public void setContentEncoded(String contentEncoded) {
        this.contentEncoded = contentEncoded;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    @Override
    public String toString() {
        return "Item{" +
                "content='" + content + '\'' +
                ", medialLarge=" + medialLarge +
                ", medialBig=" + medialBig +
                ", medialThumbnail=" + medialThumbnail +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", contentEncoded='" + contentEncoded + '\'' +
                ", postid='" + postid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", isBookMarked=" + isBookMarked +
                '}';
    }
}
