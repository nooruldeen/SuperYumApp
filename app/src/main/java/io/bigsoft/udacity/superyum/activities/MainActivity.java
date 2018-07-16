package io.bigsoft.udacity.superyum.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.fragments.MasterListFragment;
import io.bigsoft.udacity.superyum.model.RecipeModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity implements MasterListFragment.RecipeListener {

    @BindView(R.id.tv_main_list_title) TextView title;
    @BindView(R.id.nested_scroll_view_mainActivity) NestedScrollView nestedScrollView;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_container, new MasterListFragment())
                    .commit();
        }
    }

    @Override
    public void onRecipeClicked(RecipeModel recipeModel) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        Bundle bundle = new Bundle();
        // TODO
        bundle.putSerializable("key", recipeModel);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showErrorSnackBar() {
        Snackbar.make(nestedScrollView, getString(R.string.error_loading_data), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.master_list_container, new MasterListFragment())
                        .commit();
            }
        }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}

