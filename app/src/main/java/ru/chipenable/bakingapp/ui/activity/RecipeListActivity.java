package ru.chipenable.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.navigation.Router;

public class RecipeListActivity extends AppCompatActivity implements INavigator {

    @Inject Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ((BakingApp) getApplication()).getAppComponent().inject(this);
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

    private void showDetails() {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        startActivity(intent);
    }

}
