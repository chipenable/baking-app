package ru.chipenable.bakingapp.common;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.di.DaggerAppComponent;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class MockApplication extends BakingApp {

    @Override
    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new TestAppModule(this))
                .build();
    }

}
