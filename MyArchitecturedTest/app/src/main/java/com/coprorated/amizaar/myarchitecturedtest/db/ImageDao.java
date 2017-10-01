package com.coprorated.amizaar.myarchitecturedtest.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;

import java.util.List;

/**
 * Created by amizaar on 30.08.2017.
 */

@Dao
public interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertImages(List<Image> images);

    @Query("SELECT * FROM Image")
    public abstract LiveData<List<Image>> loadImages();

    @Query("SELECT * FROM Image WHERE id = :id ")
    public abstract LiveData<Image> loadImageById(String id);

    @Delete
    public abstract void removeAll(List<Image> images);
}
