package ru.chipenable.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.di.ApplicationContext;
import ru.chipenable.bakingapp.interactor.WidgetInteractor;
import ru.chipenable.bakingapp.model.data.Ingredient;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    @Inject WidgetInteractor widgetInteractor;

    private final String TAG = getClass().getName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        if (widgetInteractor == null){
            BakingApp app = (BakingApp)context.getApplicationContext();
            app.getAppComponent().inject(this);
        }

        for (int appWidgetId : appWidgetIds) {

            widgetInteractor.getIngredients()
                    .map(ingredients -> {
                        ArrayList<Parcelable> parcelables = new ArrayList<>();
                        for(Ingredient i: ingredients){
                            parcelables.add(i);
                        }
                        return parcelables;
                    })
                    .subscribe(ingredients -> {
                        Log.d(TAG, "subscribe");
                        Intent intent = new Intent(context, RecipeWidgetService.class);
                        try {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("ingredients", ingredients);
                            intent.putExtra("args", bundle);
                            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                    R.layout.recipe_widget);
                            remoteViews.setRemoteAdapter(R.id.ingredient_list_view, intent);
                            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                        }
                        catch(Exception e){
                            Log.d(TAG, e.toString());
                        }

                    });
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

