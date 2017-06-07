package ru.chipenable.bakingapp.model.util;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pavel on 07.06.17.
 */

public class ActivityUtil {

    public static void setDisplayHomeAsUpEnabled(AppCompatActivity activity){
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

}
