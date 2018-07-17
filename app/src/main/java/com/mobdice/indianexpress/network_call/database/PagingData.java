package com.mobdice.indianexpress.network_call.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PagingData {
    @PrimaryKey
    @ColumnInfo(name = "page_no")
    private int pageNo;

    @ColumnInfo(name="page_Data")
    private String pageData;

    @ColumnInfo(name="page_save_time")
    private String pageSaveTime;

    public PagingData(int pageNo, String pageData, String pageSaveTime) {
        this.pageNo = pageNo;
        this.pageData = pageData;
        this.pageSaveTime = pageSaveTime;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageData() {
        return pageData;
    }

    public void setPageData(String pageData) {
        this.pageData = pageData;
    }

    public String getPageSaveTime() {
        return pageSaveTime;
    }

    public void setPageSaveTime(String pageSaveTime) {
        this.pageSaveTime = pageSaveTime;
    }

    @Override
    public String toString() {
        return "PagingData{" +
                "pageNo=" + pageNo +
                ", pageData='" + pageData + '\'' +
                ", pageSaveTime='" + pageSaveTime + '\'' +
                '}';
    }
}
