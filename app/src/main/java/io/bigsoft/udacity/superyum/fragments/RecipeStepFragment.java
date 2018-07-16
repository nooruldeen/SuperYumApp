package io.bigsoft.udacity.superyum.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.adapter.StepListAdapter;
import io.bigsoft.udacity.superyum.model.StepsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepFragment extends Fragment implements StepListAdapter.StepItemClickListener {

    private List<StepsModel> stepsModelList;
    @BindView(R.id.step_list) RecyclerView stepList;
    private OnStepItemClickListener clickListener;

    public interface OnStepItemClickListener {
        void onStepItemClicked(StepsModel stepsModel);
    }

    public RecipeStepFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            clickListener = (OnStepItemClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepItemClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step, container, false);
        ButterKnife.bind(this, rootView);
        StepListAdapter stepListAdapter = new StepListAdapter(this);
        stepListAdapter.setStepsModelList(stepsModelList);
        stepList.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));
        stepList.setAdapter(stepListAdapter);
        stepList.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onClickStep(StepsModel stepsModel) {
        clickListener.onStepItemClicked(stepsModel);
    }

    public void setStepsModelList(List<StepsModel> stepsModelList) {
        this.stepsModelList = stepsModelList;
    }
}
