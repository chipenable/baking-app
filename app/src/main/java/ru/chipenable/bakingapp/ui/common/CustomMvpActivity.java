package ru.chipenable.bakingapp.ui.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.chipenable.bakingapp.R;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class CustomMvpActivity extends MvpAppCompatActivity {

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

    protected void replaceFragment(@IdRes int containerId, IFragmentCreator fragmentCreator,
                                   boolean addToBackStack, String tag){

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentByTag(tag) == null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(containerId, fragmentCreator.createFragment(), tag);
            if (addToBackStack){
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    protected void replaceFragment(@IdRes int containerId, Fragment f, boolean addToBackStack){

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack){
            ft.addToBackStack(null);
        }
        ft.commit();

    }

}
