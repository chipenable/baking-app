package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;

public class MainActivity extends AppCompatActivity implements INavigator {

    @Inject Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((BakingApp)getApplication()).getAppComponent().inject(this);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.master_container, new RecipeFragment())
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.attachToNavigator(this);
    }

    @Override
    public void handleCommand(Command command) {
        if (command == Command.SHOW_DETAILS){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.master_container, new RecipeDetailsFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}
