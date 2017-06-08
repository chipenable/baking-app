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
import ru.chipenable.bakingapp.model.data.Ingredient;

/**
 * Created by pavel on 08.06.17.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder>{

    private List<Ingredient> ingredients;

    public IngredientAdapter(){
        ingredients = new ArrayList<>();
    }

    public void setData(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient) TextView ingredientNameView;
        @BindView(R.id.quantity) TextView quantityView;
        @BindView(R.id.measure) TextView measureView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Ingredient ingredient){
            ingredientNameView.setText(ingredient.ingredient());
            measureView.setText(ingredient.measure());

            float quantity = ingredient.quantity();
            String quantityStr = Float.toString(quantity);
            quantityView.setText(quantityStr);

        }

    }



}
