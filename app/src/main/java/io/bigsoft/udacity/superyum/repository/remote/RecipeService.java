package io.bigsoft.udacity.superyum.repository.remote;

import java.util.List;

import io.bigsoft.udacity.superyum.model.RecipeModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
    @GET("baking.json")
    Call<List<RecipeModel>> getRecipe();
}
