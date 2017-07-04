package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.util.ActivityUtil;

public class StepActivity extends AppCompatActivity  implements INavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ActivityUtil.setDisplayHomeAsUpEnabled(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleCommand(Command command) {
        switch()
    }
}
