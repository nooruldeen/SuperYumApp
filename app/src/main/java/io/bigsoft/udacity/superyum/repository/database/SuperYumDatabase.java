package io.bigsoft.udacity.superyum.repository.database;


import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import io.bigsoft.udacity.superyum.model.RecipeModel;

@Database(entities = {RecipeModel.class}, version = 2, exportSchema = false)
public abstract class SuperYumDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = SuperYumDatabase.class.getSimpleName();
    private static SuperYumDatabase instance;

    public static SuperYumDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext()
                        , SuperYumDatabase.class
                        , SuperYumDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return instance;
    }

    public abstract DbDao SuperYumDao();
}
