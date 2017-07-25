package ru.chipenable.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.interactor.WidgetInteractor;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidget extends AppWidgetProvider {

    @Inject WidgetInteractor widgetInteractor;

    private final String TAG = getClass().getName();
    private final String NEXT_RECIPE_ACTION = "next_recipe";
    private final String PREV_RECIPE_ACTION = "prev_recipe";

    @Override
    public void onReceive(Context context, Intent intent) {
        injectDependencies(context);
        super.onReceive(context, intent);

        String action = intent.getAction();
        if (action.equalsIgnoreCase(NEXT_RECIPE_ACTION)){
            widgetInteractor.incRecipeIndex()
                    .subscribe(result -> {
                        if (result){
                            updateWidgets(context);
                        }
                    });
        }
        else if (action.equalsIgnoreCase(PREV_RECIPE_ACTION)){
            widgetInteractor.decRecipeIndex()
                    .subscribe(result -> {
                        if (result){
                            updateWidgets(context);
                        }
                    });
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            widgetInteractor.getRecipe()
                    .subscribe(recipe -> {
                                Intent intent = new Intent(context, RecipeWidgetService.class);
                                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                        R.layout.recipe_widget);
                                remoteViews.setTextViewText(R.id.recipe_name, recipe.name());

                                PendingIntent nextButIntent = getPendingSelfIntent(context, NEXT_RECIPE_ACTION);
                                remoteViews.setOnClickPendingIntent(R.id.next_but, nextButIntent);

                                PendingIntent prevButIntent = getPendingSelfIntent(context, PREV_RECIPE_ACTION);
                                remoteViews.setOnClickPendingIntent(R.id.prev_but, prevButIntent);

                                remoteViews.setRemoteAdapter(R.id.ingredient_list_view, intent);
                                remoteViews.setEmptyView(R.id.ingredient_list_view, R.id.empty_view);
                                appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
                                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,
                                        R.id.ingredient_list_view);
                            },
                            throwable -> {}
                    );
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

    /** util methods */

    private void injectDependencies(Context context){
        BakingApp app = (BakingApp) context.getApplicationContext();
        app.getAppComponent().inject(this);
    }

    private void updateWidgets(Context context){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), RecipeWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    private PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, RecipeWidget.class);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}

