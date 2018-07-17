package com.mobdice.indianexpress.network_call.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface PagingDataDAO {
    @Query("SELECT * FROM pagingdata")
    public List<PagingData> getAll();

    @Query("SELECT * from pagingdata WHERE page_no=:pageNo")
    public PagingData getData(int pageNo);

    @Query("DELETE from pagingdata WHERE page_no=:pageNo")
    public void deletePageData(int pageNo);

    @Insert
    public void insertPageData(PagingData pagingData);

    @Delete
    public void delete(PagingData pagingData);
}
