package ru.chipenable.bakingapp.ui.common;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;

import ru.chipenable.bakingapp.R;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class CustomMvpActivity extends MvpAppCompatActivity {

    protected boolean isTablet;
    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTablet = checkConfiguration();
        setOrientation(isTablet);
    }

    protected boolean checkConfiguration(){
        return getResources().getBoolean(R.bool.is_tablet);
    }

    protected void setOrientation(boolean isTablet){
        int orientation = isTablet? ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE:
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(orientation);
    }

}
