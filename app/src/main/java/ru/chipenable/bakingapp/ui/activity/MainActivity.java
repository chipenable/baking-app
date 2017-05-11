package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.master_container, new RecipeFragment())
                .commit();
    }

}
