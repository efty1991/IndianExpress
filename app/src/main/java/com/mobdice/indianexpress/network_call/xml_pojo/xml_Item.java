package com.mobdice.indianexpress.network_call.xml_pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class xml_Item {
    @Expose
    @SerializedName("title")
    String title;

    @Expose
    @SerializedName("priorityTitle")
    String priorityTitle;

    @Expose
    @SerializedName("pubDate")
    String pubDate;

    @Expose
    @SerializedName("dc:creator")
    String dc_creator;

    @Expose
    @SerializedName("dc:slug")
    String dc_slug;

    @Expose
    @SerializedName("title")
    String pubCity;

    @Expose
    @SerializedName("link")
    String link;

    @Expose
    @SerializedName("description")
    String description;

    @Expose
    @SerializedName("content:encoded")
    String content_encoded;

    @Expose
    @SerializedName("category")
    String category;

    @Expose
    @SerializedName("media:thumbnail")
    String media_thumbnail;

    @Expose
    @SerializedName("media:big")
    String media_big;

    @Expose
    @SerializedName("media:large")
    String media_large;

    @Expose
    @SerializedName("postid")
    String postid;

    public xml_Item() {
    }
    public xml_Item(String title, String priorityTitle, String pubDate, String dc_creator, String dc_slug, String pubCity, String link, String description, String content_encoded, String category, String media_thumbnail, String media_big, String media_large, String postid) {
        this.title = title;
        this.priorityTitle = priorityTitle;
        this.pubDate = pubDate;
        this.dc_creator = dc_creator;
        this.dc_slug = dc_slug;
        this.pubCity = pubCity;
        this.link = link;
        this.description = description;
        this.content_encoded = content_encoded;
        this.category = category;
        this.media_thumbnail = media_thumbnail;
        this.media_big = media_big;
        this.media_large = media_large;
        this.postid = postid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriorityTitle() {
        return priorityTitle;
    }

    public void setPriorityTitle(String priorityTitle) {
        this.priorityTitle = priorityTitle;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDc_creator() {
        return dc_creator;
    }

    public void setDc_creator(String dc_creator) {
        this.dc_creator = dc_creator;
    }

    public String getDc_slug() {
        return dc_slug;
    }

    public void setDc_slug(String dc_slug) {
        this.dc_slug = dc_slug;
    }

    public String getPubCity() {
        return pubCity;
    }

    public void setPubCity(String pubCity) {
        this.pubCity = pubCity;
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

    public String getContent_encoded() {
        return content_encoded;
    }

    public void setContent_encoded(String content_encoded) {
        this.content_encoded = content_encoded;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMedia_thumbnail() {
        return media_thumbnail;
    }

    public void setMedia_thumbnail(String media_thumbnail) {
        this.media_thumbnail = media_thumbnail;
    }

    public String getMedia_big() {
        return media_big;
    }

    public void setMedia_big(String media_big) {
        this.media_big = media_big;
    }

    public String getMedia_large() {
        return media_large;
    }

    public void setMedia_large(String media_large) {
        this.media_large = media_large;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    @Override
    public String toString() {
        return "xml_Item{" +
                "title='" + title + '\'' +
                ", priorityTitle='" + priorityTitle + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", dc_creator='" + dc_creator + '\'' +
                ", dc_slug='" + dc_slug + '\'' +
                ", pubCity='" + pubCity + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", content_encoded='" + content_encoded + '\'' +
                ", category='" + category + '\'' +
                ", media_thumbnail='" + media_thumbnail + '\'' +
                ", media_big='" + media_big + '\'' +
                ", media_large='" + media_large + '\'' +
                ", postid='" + postid + '\'' +
                '}';
    }
}
