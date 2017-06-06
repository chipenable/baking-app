package ru.chipenable.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;

public class RecipeListActivity extends AppCompatActivity implements INavigator {

    @Inject Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ((BakingApp) getApplication()).getAppComponent().inject(this);
        showRecipeList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.attachToNavigator(this);
    }

    @Override
    public void handleCommand(Command command) {
        switch (command) {
            case SHOW_DETAILS: {
                showDetails();
                break;
            }

            default:
        }
    }

    private void showRecipeList() {
        replaceFragment(R.id.master_container, new RecipeFragment(), false);
    }

    private void replaceFragment(@IdRes int containerId, Fragment f, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void showDetails() {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        startActivity(intent);
    }

}
