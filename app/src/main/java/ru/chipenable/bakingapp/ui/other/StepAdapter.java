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

    private static final int INGREDIENT_VIEW_TYPE = 0;
    private static final int STEP_VIEW_TYPE = 1;

    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    private IOnIngredientsClickListener ingredientsClickListener;
    private IOnStepsClickListener stepsClickListener;

    public interface IOnIngredientsClickListener{
        void onIngredientsClick();
    }

    public interface IOnStepsClickListener{
        void onStepClick(int position);
    }

    public void setIngredientsClickListener(IOnIngredientsClickListener listener){
        ingredientsClickListener = listener;
    }

    public void setStepsClickListener(IOnStepsClickListener listener){
        stepsClickListener = listener;
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
    public int getItemViewType(int position) {
        return position == 0? INGREDIENT_VIEW_TYPE:STEP_VIEW_TYPE;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_detail_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, int position) {
        switch(getItemViewType(position)){
            case INGREDIENT_VIEW_TYPE:
                holder.bindIngredient();
                return;
            case STEP_VIEW_TYPE:
                holder.bindStep(stepList.get(position - 1));
                return;
        }
    }

    @Override
    public int getItemCount() {
        return stepList == null? 0:stepList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.recipe_name) TextView recipeNameView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindIngredient(){
            recipeNameView.setText(R.string.ingredients);
        }

        public void bindStep(Step step){
            recipeNameView.setText(step.shortDescription());
        }

        @Override
        public void onClick(View v) {
            switch(getItemViewType()){
                case INGREDIENT_VIEW_TYPE:
                    if (ingredientsClickListener != null){
                        ingredientsClickListener.onIngredientsClick();
                    }
                    break;
                case STEP_VIEW_TYPE:
                    if (stepsClickListener != null){
                        stepsClickListener.onStepClick(getAdapterPosition() - 1);
                    }
            }
        }
    }

}
