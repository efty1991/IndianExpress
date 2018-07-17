package com.mobdice.indianexpress.network_call.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookMarkedDAO {
    @Query("SELECT * from bookmarked")
    List<BookMarked> getAll();

    @Query("SELECT * from bookmarked WHERE post_id=:postId")
    BookMarked getBookmarked(int postId);

    @Insert
    long insertBookMarked(BookMarked bookMarked);

    @Query("DELETE from bookmarked WHERE post_id=:postId")
    int deleteBookMArked(int postId);
}
