package io.bigsoft.udacity.superyum.utils;

import io.bigsoft.udacity.superyum.repository.remote.RecipeService;
import io.bigsoft.udacity.superyum.repository.remote.RetrofitClient;

public class ApiUtils {

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static RecipeService getRecipeService() {
        return RetrofitClient.getClient(BASE_URL).create(RecipeService.class);
    }
}