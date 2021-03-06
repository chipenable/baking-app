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
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

/**
 * Created by Pavel.B on 18.05.2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private static final int INGREDIENT_VIEW_TYPE = 0;
    private static final int STEP_VIEW_TYPE = 1;

    private List<Step> stepList;
    private IOnItemClickListener listener;

    public interface IOnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(IOnItemClickListener listener){
        this.listener = listener;
    }

    public StepAdapter(){
        this(new ArrayList<>());
    }

    public StepAdapter(List<Step> stepList){
        this.stepList = stepList;
    }

    public void setData(Recipe recipe){
        stepList = recipe.steps();
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
            default:
        }
    }

    @Override
    public int getItemCount() {
        return stepList == null? 0:stepList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.step_name) TextView stepNameView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        public void bindIngredient(){
            stepNameView.setText(R.string.ingredients);
        }

        public void bindStep(Step step){
            stepNameView.setText(step.shortDescription());
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onItemClick(getAdapterPosition());
            }
        }
    }

}
