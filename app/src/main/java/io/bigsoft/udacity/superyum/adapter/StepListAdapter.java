package io.bigsoft.udacity.superyum.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.model.StepsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private List<StepsModel> stepsModelList;
    private final StepItemClickListener itemClickListener;

    public interface StepItemClickListener {
        void onClickStep(StepsModel recipeModel);
    }

    public StepListAdapter(StepItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.step_item_layout, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        holder.stepDescription.setText(stepsModelList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepsModelList == null)return 0;
        return stepsModelList.size();
    }

    public void setStepsModelList(List<StepsModel> stepsModelList) {
        this.stepsModelList = stepsModelList;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.step_description) TextView stepDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemClickListener.onClickStep(stepsModelList.get(position));
        }
    }

}
