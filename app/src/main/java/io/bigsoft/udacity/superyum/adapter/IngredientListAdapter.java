package io.bigsoft.udacity.superyum.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.model.IngredientsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {


    private List<IngredientsModel> ingredientsModelList;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredient_item_layout, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        holder.ingredientTitle.setText(ingredientsModelList.get(position).getIngredient());
        holder.ingredientQuantity.setText(" " + String.valueOf(ingredientsModelList.get(position).getQuantity()));
        holder.ingredientMeasure.setText(" " + ingredientsModelList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        if (ingredientsModelList == null) return 0;
        return ingredientsModelList.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_title) TextView ingredientTitle;
        @BindView(R.id.ingredient_quantity) TextView ingredientQuantity;
        @BindView(R.id.ingredient_measure) TextView ingredientMeasure;

        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setIngredientsModelList(List<IngredientsModel> ingredientsModelList) {
        this.ingredientsModelList = ingredientsModelList;
    }
}
