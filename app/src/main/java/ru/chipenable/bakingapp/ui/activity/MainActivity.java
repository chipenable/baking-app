package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

public class MainActivity extends AppCompatActivity implements INavigator {

    @Inject Router router;

    private int mainContainerId;
    private int detailcontainerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((BakingApp)getApplication()).getAppComponent().inject(this);
        checkConfiguration();

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.master_container);
        if (f == null) {
            fm.beginTransaction()
                    .add(mainContainerId, new RecipeFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.attachToNavigator(this);
    }

    @Override
    public void handleCommand(Command command) {
        switch(command) {
            case SHOW_DETAILS: {
                replaceFragment(mainContainerId, new RecipeDetailsFragment(), true);
                break;
            }

            case SHOW_INGREDIENTS:{
                replaceFragment(mainContainerId, new IngredientsFragment(), true);
                break;
            }

            case SHOW_STEP:{
                replaceFragment(detailcontainerId, new StepFragment(), false);
                break;
            }

            default:
        }
    }

    private void checkConfiguration(){
        mainContainerId = R.id.master_container;
        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        detailcontainerId = isTablet? R.id.detail_container:R.id.master_container;
    }

    private void replaceFragment(@IdRes int containerId, Fragment f, boolean addToBackStack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

}
