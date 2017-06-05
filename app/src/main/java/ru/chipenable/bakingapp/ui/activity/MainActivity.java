package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.detail_container) FrameLayout detailContainer;

    private int mainContainerId;
    private int detailcontainerId;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((BakingApp)getApplication()).getAppComponent().inject(this);
        checkConfiguration();
        ButterKnife.bind(this);

        showRecipeList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.attachToNavigator(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        detailContainer.setVisibility(View.GONE);
    }

    @Override
    public void handleCommand(Command command) {
        switch(command) {
            case SHOW_DETAILS: {
                showDetails();
                break;
            }

            case SHOW_INGREDIENTS:{
                showIngredients();
                break;
            }

            case SHOW_STEP:{
                showStep();
                break;
            }

            default:
        }
    }

    private void checkConfiguration(){
        mainContainerId = R.id.master_container;
        detailcontainerId = R.id.detail_container;
        isTablet = getResources().getBoolean(R.bool.is_tablet);
    }

    private void replaceFragment(@IdRes int containerId, Fragment f, boolean addToBackStack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(containerId, f);
        if (addToBackStack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    private void showRecipeList(){
        if (isTablet){
            detailContainer.setVisibility(View.GONE);
            replaceFragment(mainContainerId, new RecipeFragment(), true);
        }
        else{
            replaceFragment(mainContainerId, new RecipeFragment(), true);
        }
    }

    private void showDetails(){
        if (isTablet){
            detailContainer.setVisibility(View.VISIBLE);
            replaceFragment(mainContainerId, new RecipeDetailsFragment(), true);
            replaceFragment(detailcontainerId, new IngredientsFragment(), false);
        }
        else{
            replaceFragment(mainContainerId, new RecipeDetailsFragment(), true);
        }
    }

    private void showIngredients(){
        if (isTablet){
            replaceFragment(detailcontainerId, new IngredientsFragment(), false);
        }
        else{
            replaceFragment(mainContainerId, new IngredientsFragment(), true);
        }
    }

    private void showStep(){
        if (isTablet){
            replaceFragment(detailcontainerId, new StepFragment(), false);
        }
        else{
            replaceFragment(mainContainerId, new StepFragment(), true);
        }
    }



}
