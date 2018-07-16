package io.bigsoft.udacity.superyum.repository.assets;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import io.bigsoft.udacity.superyum.model.RecipeModel;

public class RecipeData {
    private static final String LOG_TAG = RecipeData.class.getSimpleName();
    private static boolean mStatus;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<RecipeModel>> mLoadedRecipes;

    public RecipeData(Context context) {
        mLoadedRecipes = new MutableLiveData<>();
        mStatus = false;
        fetchJsonFile(context);
    }

    public MutableLiveData<List<RecipeModel>> getLoadedRecipes() {
        return mLoadedRecipes;
    }

    /**
     * Gets the newest weather
     */
    void fetchJsonFile(Context context) {
        Log.d(LOG_TAG, "Fetch json started");
        String json = null;
        try {
            Gson gson = new GsonBuilder().create();
            InputStream is = context.getAssets().open("baking.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            Type listType = new TypeToken<List<RecipeModel>>() {
            }.getType();
            List<RecipeModel> recipes = gson.fromJson(json, listType);

            Log.d(LOG_TAG, "JSON Parsing finished: " + json.length() + " Recipes: " + recipes.size());

            if (recipes.size() != 0) {

                // When you are off of the main thread and want to update LiveData, use postValue.
                // It posts the update to the main thread.
                mLoadedRecipes.postValue(recipes);
                mStatus = true;
                Log.d(LOG_TAG, "JSON Parsing finished: " + json.length() + " Recipes: " + recipes.size());
            }
        } catch (Exception e) {
            // Server probably invalid
            e.printStackTrace();
        }
    }

    public boolean isSuccess(){
        return mStatus;
    }
}

