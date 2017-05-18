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
import ru.chipenable.bakingapp.model.view.Recipe;

/**
 * Created by Pavel.B on 18.05.2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<Recipe> items;

    public RecipeAdapter(){
        this(new ArrayList<Recipe>());
    }

    public RecipeAdapter(List<Recipe> items){
        this.items = items;
    }

    public void setItems(List<Recipe> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeNameView;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Recipe recipe){
            recipeNameView.setText(recipe.getName());
        }
    }

}
