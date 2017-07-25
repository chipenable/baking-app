package ru.chipenable.bakingapp.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Pavel.B on 21.05.2017.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface UiScheduler {
}

