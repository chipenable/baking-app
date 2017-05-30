package ru.chipenable.bakingapp.ui.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.Ingredient;
import ru.chipenable.bakingapp.model.Recipe;
import ru.chipenable.bakingapp.model.Step;

/**
 * Created by Pavel.B on 18.05.2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {


    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private IOnItemClickListener itemClickListener;

    public interface IOnItemClickListener{
        void onItemClick(int position);
    }

    public void setItemClickListener(IOnItemClickListener listener){
        itemClickListener = listener;
    }

    public StepAdapter(){
        this(new ArrayList<>(), new ArrayList<>());
    }

    public StepAdapter(List<Ingredient> ingredientList, List<Step> stepList){
        this.ingredientList = ingredientList;
        this.stepList = stepList;
    }

    public void setData(Recipe recipe){
        stepList = recipe.steps();
        ingredientList = recipe.ingredients();
        notifyDataSetChanged();
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, int position) {
        holder.bind(stepList.get(position));
    }

    @Override
    public int getItemCount() {
        return stepList == null? 0:stepList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_name) TextView recipeNameView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bind(Step step){
            recipeNameView.setText(step.description());
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null){
                itemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
