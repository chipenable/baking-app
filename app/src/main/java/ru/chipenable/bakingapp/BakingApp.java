package ru.chipenable.bakingapp;

import android.app.Application;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.di.AppModule;
import ru.chipenable.bakingapp.di.DaggerAppComponent;

/**
 * Created by Pavel.B on 20.05.2017.
 */

public class BakingApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = createAppComponent();
    }

    protected AppComponent createAppComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }

}
