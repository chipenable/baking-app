package ru.chipenable.bakingapp.common;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnitRunner;

import ru.chipenable.bakingapp.common.MockApplication;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class MockTestRunner extends AndroidJUnitRunner {

    @Override
    @NonNull
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        return super.newApplication(cl, MockApplication.class.getName(), context);
    }

}
