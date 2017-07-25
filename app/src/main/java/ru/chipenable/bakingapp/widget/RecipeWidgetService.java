package ru.chipenable.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import ru.chipenable.bakingapp.BakingApp;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        BakingApp app = (BakingApp) getApplicationContext();
        return new RecipeViewsFactory(getApplicationContext(), intent, app.getAppComponent());
    }

}
