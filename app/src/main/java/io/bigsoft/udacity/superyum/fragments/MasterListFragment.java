package io.bigsoft.udacity.superyum.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.android.spinkit.SpinKitView;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.adapter.RecipeListAdapter;
import io.bigsoft.udacity.superyum.model.RecipeModel;
import io.bigsoft.udacity.superyum.repository.RecipesViewModel;
import io.bigsoft.udacity.superyum.repository.Resource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MasterListFragment extends Fragment implements RecipeListAdapter.ItemClickListener {

    private static RecipesViewModel mRecipesViewModel;
    private Resource<List<RecipeModel>> recipeModel;
    @BindView(R.id.recipe_list) RecyclerView recipeList;
    @BindView(R.id.spin_kit) SpinKitView spinKitView;
    private RecipeListAdapter recipeListAdapter;
    private RecipeListener recipeClickListener;

    public interface RecipeListener {
        void onRecipeClicked(RecipeModel recipeModel);
        void showErrorSnackBar();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            recipeClickListener = (RecipeListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement RecipeListener");
        }
    }

    public MasterListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        ButterKnife.bind(this, rootView);
        recipeListAdapter = new RecipeListAdapter(this);
        recipeList.setNestedScrollingEnabled(false);

        if (rootView.findViewById(R.id.check_view) != null)
            recipeList.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        else
            recipeList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        recipeList.setHasFixedSize(true);
        recipeList.setAdapter(recipeListAdapter);

        mRecipesViewModel = ViewModelProviders.of(getActivity()).get(RecipesViewModel.class);
        mRecipesViewModel.getListLiveData().observe(this, recipes ->{
            spinKitView.setVisibility(View.INVISIBLE);
            if (recipes != null) {
                recipeModel = recipes;
                recipeListAdapter.setRecipeModelList(recipeModel.data);
            } else {
                recipeClickListener.showErrorSnackBar();
            }

        });
        return rootView;
    }

    @Override
    public void onClick(RecipeModel recipeModel) {
        recipeClickListener.onRecipeClicked(recipeModel);
    }

}
