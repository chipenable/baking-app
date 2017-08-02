package ru.chipenable.bakingapp.ui.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.chipenable.bakingapp.R;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class CustomActivity extends AppCompatActivity {

    protected interface IFragmentCreator{
        Fragment createFragment();
    }

    protected boolean isTablet;
    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = checkConfiguration();
    }

    protected boolean checkConfiguration(){
        return getResources().getBoolean(R.bool.is_tablet);
    }

    protected void setOrientation(boolean isTablet){
        int orientation = isTablet? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
    }

    protected void addFragment(@IdRes int containerId, IFragmentCreator fragmentCreator,
                               boolean addToBackStack){

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(containerId) == null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(containerId, fragmentCreator.createFragment());
            if (addToBackStack){
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

}
