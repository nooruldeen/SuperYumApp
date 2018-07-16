package io.bigsoft.udacity.superyum.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.bigsoft.udacity.superyum.AppExecutors;
import io.bigsoft.udacity.superyum.model.IngredientsModel;
import io.bigsoft.udacity.superyum.model.RecipeModel;
import io.bigsoft.udacity.superyum.repository.assets.RecipeData;
import io.bigsoft.udacity.superyum.repository.database.DbDao;

public class RecipesRepository {

    // For Singleton instantiation
    private static final Object LOCK = new Object();

    private RecipeData recipeData;
    private DbDao superYumDao;
    private final AppExecutors mExecutors;
    private final Context mContext;

    private static RecipesRepository sInstance;

    public static RecipesRepository getInstance(DbDao dbDao, AppExecutors executors, Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RecipesRepository(dbDao, executors, context);
            }
        }
        return sInstance;
    }

    private RecipesRepository(DbDao dbDao, AppExecutors executors, Context context) {
        this.superYumDao = dbDao;
        this.mExecutors = executors;
        this.mContext = context;
    }

    public List<IngredientsModel> getFavorite() {
        RecipeModel recipeModel = superYumDao.getFavorite();

        return recipeModel.getIngredients();
    }

    public void setFavorite(int id) {
        superYumDao.clearFavorite();
        superYumDao.setFavorite(id);
    }

    public LiveData<Resource<List<RecipeModel>>> getRecipes() {
        return new BoundResource<List<RecipeModel>,List<RecipeModel>>() {
            @Override
            protected void saveCallResult(@NonNull List<RecipeModel> item) {
                mExecutors.diskIO().execute(() -> {
                    superYumDao.deleteAll();
                    superYumDao.insertAll(item);
                });
            }

            @Override
            protected boolean shouldFetch(@Nullable List<RecipeModel> data) {
                return (data == null || data.size()==0);
            }

            @NonNull @Override
            protected LiveData<List<RecipeModel>> loadFromDb() {
                return superYumDao.getAll();
            }

            @NonNull @Override
            protected LiveData<List<RecipeModel>> loadFromAssets() {
                recipeData = new RecipeData(mContext);
                return recipeData.getLoadedRecipes();
            }
        }.getAsLiveData();
    }
}