package ru.chipenable.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.WidgetInteractor;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 16.07.2017.
 */

public class RecipeViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    @Inject WidgetInteractor widgetInteractor;

    private Context context;
    private List<Ingredient> ingredientList;

    public RecipeViewsFactory(Context context, Intent intent, AppComponent component){
        component.inject(this);
        this.context = context;
        this.ingredientList = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        widgetInteractor.getRecipe()
                .map(Recipe::ingredients)
                .subscribe(ingredients -> ingredientList = ingredients, throwable -> {});
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientList == null? 0:ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Ingredient ingredient = ingredientList.get(position);

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_item);
        row.setTextViewText(R.id.ingredient, ingredient.ingredient());
        row.setTextViewText(R.id.quantity, String.valueOf(ingredient.quantity()));
        row.setTextViewText(R.id.measure, ingredient.measure());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
