package io.bigsoft.udacity.superyum.utils;

import android.content.Context;

import io.bigsoft.udacity.superyum.AppExecutors;
import io.bigsoft.udacity.superyum.repository.RecipesRepository;
import io.bigsoft.udacity.superyum.repository.database.SuperYumDatabase;

public class InjectorUtils {

    public static RecipesRepository provideRepository(Context context) {
        SuperYumDatabase database = SuperYumDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return RecipesRepository.getInstance(database.SuperYumDao(), executors, context);
    }

}
