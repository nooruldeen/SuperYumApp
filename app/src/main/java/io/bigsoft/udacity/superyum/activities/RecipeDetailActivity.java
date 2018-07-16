package io.bigsoft.udacity.superyum.activities;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.fragments.IngredientDetailFragment;
import io.bigsoft.udacity.superyum.fragments.RecipeIngredientFragment;
import io.bigsoft.udacity.superyum.fragments.RecipeStepFragment;
import io.bigsoft.udacity.superyum.fragments.StepDetailFragment;
import io.bigsoft.udacity.superyum.model.IngredientsModel;
import io.bigsoft.udacity.superyum.model.RecipeModel;
import io.bigsoft.udacity.superyum.model.StepsModel;
import io.bigsoft.udacity.superyum.repository.RecipesRepository;
import io.bigsoft.udacity.superyum.utils.InjectorUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.bigsoft.udacity.superyum.widget.RecipeIngredientWidgetProvider;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeIngredientFragment.OnIngredientItemClickListener,
        RecipeStepFragment.OnStepItemClickListener {

    private RecipeModel recipeModel;
    private boolean mTwoPane;
    private boolean mIngredientSelected = true;
    private StepsModel stepsModelSave;
    private RecipesRepository mRepository;
    @BindView(R.id.title_text_view) TextView titleTextView;
    @BindView(R.id.step_title_textView) TextView stepTitleTextView;
    @BindView(R.id.parent_container) FrameLayout parentContainer;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        unbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        recipeModel = (RecipeModel) bundle.getSerializable("key");
        mTwoPane = false;
        titleTextView.setText(recipeModel.getName());
        getSupportActionBar().setTitle(recipeModel.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientFragment ingredientFragment = new RecipeIngredientFragment();
        ingredientFragment.setIngredientsModelList(recipeModel.getIngredients());

        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_ingredient_container, ingredientFragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_ingredient_container, ingredientFragment)
                    .commit();
        }

        RecipeStepFragment stepFragment = new RecipeStepFragment();
        stepFragment.setStepsModelList(recipeModel.getSteps());

        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_container, stepFragment)
                .commit();

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            IngredientDetailFragment ingredientDetailFragment = new IngredientDetailFragment();
            ingredientDetailFragment.setIngredientsModelList(recipeModel.getIngredients());
            if (savedInstanceState == null) {
                fragmentManager.beginTransaction()
                        .add(R.id.detail_container, ingredientDetailFragment)
                        .commit();
            } else {
                Bundle bundle1 = savedInstanceState.getBundle("bun");
                mIngredientSelected = bundle1.getBoolean("bol");
                if (mIngredientSelected) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, ingredientDetailFragment)
                            .commit();
                } else {
                    StepsModel stepsModel = (StepsModel) bundle1.getSerializable("ser");
                    stepsModelSave = stepsModel;
                    StepDetailFragment stepDetailFragment = new StepDetailFragment();
                    stepDetailFragment.setStepsModel(stepsModel);
                    fragmentManager.beginTransaction()
                            .replace(R.id.detail_container, stepDetailFragment)
                            .commit();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
        // TODO
        bundle.putSerializable("ser", stepsModelSave);
        bundle.putBoolean("bol", mIngredientSelected);
        outState.putBundle("bun", bundle);
    }

    @Override
    public void onIngredientItemClicked(List<IngredientsModel> ingredientsModelList) {
        if (mTwoPane) {
            mIngredientSelected = true;
            IngredientDetailFragment ingredientDetailFragment = new IngredientDetailFragment();
            ingredientDetailFragment.setIngredientsModelList(ingredientsModelList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, ingredientDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, IngredientDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("key", (Serializable) ingredientsModelList);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        }
    }

    @Override
    public void onStepItemClicked(StepsModel stepsModel) {
        if (mTwoPane) {
            stepsModelSave = stepsModel;
            mIngredientSelected = false;
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setStepsModel(stepsModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, stepDetailFragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, StepDetailActivity.class);
            Bundle bundle = new Bundle();
            // TODO
            bundle.putSerializable("ser", stepsModel);
            intent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_activity_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        boolean recipeAdded;
        if (itemId == R.id.action_add) {
            mRepository = InjectorUtils.provideRepository(this);
            int favoriteId = recipeModel.getId();
            mRepository.setFavorite(favoriteId);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidgetProvider.class));

            RecipeIngredientWidgetProvider.updateIngredientWidgets(parentContainer.getContext(),appWidgetManager, recipeModel.getName(),appWidgetIds);

            Toast.makeText(parentContainer.getContext(), recipeModel.getName() + " was set as favorite recipe to Super Yum widget!", Toast.LENGTH_LONG).show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
