package ru.chipenable.bakingapp.widget;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.model.data.Ingredient;

public class RecipeWidgetService extends RemoteViewsService {

    @Inject
    RecipeDetailsInteractor recipeDetailsInteractor;

    private final String TAG = getClass().getName();

    @Override
    public void onCreate() {
        super.onCreate();
        BakingApp app = (BakingApp)getApplication();
        app.getAppComponent().inject(this);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory");

        List<Ingredient> list = new ArrayList<>();
        Ingredient ingredient = Ingredient.create(10, "kg", "fish");
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        list.add(ingredient);
        return new RecipeViewsFactory(getApplicationContext(), intent, list);
    }

}
