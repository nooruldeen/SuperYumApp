package io.bigsoft.udacity.superyum.repository.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.bigsoft.udacity.superyum.model.IngredientsModel;
import io.bigsoft.udacity.superyum.model.RecipeModel;

@Dao
public interface DbDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeModel>> getAll();

    @Insert
    void insertAll(List<RecipeModel> movieDataList);

    @Query("DELETE FROM recipes")
    void deleteAll();

    @Query("SELECT * FROM recipes WHERE favorite = 1")
    RecipeModel getFavorite();

    @Query("UPDATE recipes SET " +
            "favorite = 1 " +
            "WHERE id = :id")
    int setFavorite(int id);

    @Query("UPDATE recipes SET " +
            "favorite = 0 ")
    int clearFavorite();

}
