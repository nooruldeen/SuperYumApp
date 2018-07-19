package io.bigsoft.udacity.superyum.repository.remote;


import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import java.util.List;

import io.bigsoft.udacity.superyum.model.RecipeModel;
import io.bigsoft.udacity.superyum.utils.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteData {
    private static final String LOG_TAG = RemoteData.class.getSimpleName();
    private static boolean mStatus;
    private static RecipeService mRecipeService;

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<RecipeModel>> mLoadedRecipes;

    public RemoteData(Context context) {
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
        mRecipeService = ApiUtils.getRecipeService();

        try {
            Call<List<RecipeModel>> recipeCall = mRecipeService.getRecipe();
            recipeCall.enqueue(new Callback<List<RecipeModel>>() {
                @Override
                public void onResponse(Call<List<RecipeModel>> call, Response<List<RecipeModel>> response) {

                    Integer statusCode = response.code();
                    Log.v("status code: ", statusCode.toString());

                    List<RecipeModel> recipes = response.body();

                    Log.d(LOG_TAG, "ApiResponse: JSON Parsing finished - Recipes: " + recipes.size());

                    if (recipes.size() != 0) {

                        // When you are off of the main thread and want to update LiveData, use postValue.
                        // It posts the update to the main thread.
                        mLoadedRecipes.setValue(recipes);
                        mStatus = true;
                        Log.d(LOG_TAG, "ApiResponse: JSON Parsing finished: - Loaded Recipes: " + mLoadedRecipes.getValue().size());
                    }
                }

                @Override
                public void onFailure(Call<List<RecipeModel>> call, Throwable t) {
                    Log.v("http fail: ", t.getMessage());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isSuccess(){
        return mStatus;
    }
}

