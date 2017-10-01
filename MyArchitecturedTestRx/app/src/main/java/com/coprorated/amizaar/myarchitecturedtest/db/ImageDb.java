package com.coprorated.amizaar.myarchitecturedtest.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.coprorated.amizaar.myarchitecturedtest.data.images.Image;

/**
 * Created by amizaar on 30.08.2017.
 */

@Database(entities = {Image.class}, version = 1)
public abstract class ImageDb extends RoomDatabase{

    public abstract ImageDao imageDao();
}
