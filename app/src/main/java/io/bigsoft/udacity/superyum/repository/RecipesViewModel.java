package io.bigsoft.udacity.superyum.repository;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.bigsoft.udacity.superyum.model.RecipeModel;
import io.bigsoft.udacity.superyum.utils.InjectorUtils;

public class RecipesViewModel extends AndroidViewModel {

    private LiveData<Resource<List<RecipeModel>>> mRecipes;
    private static RecipesRepository recipesRepository;

    public RecipesViewModel(Application application) {
        super(application);
        if (this.recipesRepository != null) {
            // ViewModel is created per Activity, so instantiate once
            // we know the userId won't change
            return;
        }
        initRepository(application);
    }

    public LiveData<Resource<List<RecipeModel>>> getListLiveData() {
        if (mRecipes == null){
            loadRecipes();
        }

        return mRecipes;
    }

    private void loadRecipes(){
        if (mRecipes==null){
            mRecipes = recipesRepository.getRecipes();
        }
    }

    private void initRepository(Application application){
        recipesRepository = InjectorUtils.provideRepository(application);
    }
}
