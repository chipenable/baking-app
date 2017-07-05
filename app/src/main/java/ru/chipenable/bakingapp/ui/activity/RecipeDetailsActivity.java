package ru.chipenable.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.ArgumentKeys;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.INavigator;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.model.util.ActivityUtil;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipeDetailsPresenter;
import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

public class RecipeDetailsActivity extends AppCompatActivity implements INavigator {

    @Inject Router router;

    private final String TAG = getClass().getName();
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ((BakingApp)getApplication()).getAppComponent().inject(this);
        checkConfiguration();
        showRecipeDetails();
        ActivityUtil.setDisplayHomeAsUpEnabled(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        router.attachToNavigator(this);
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
        switch(command) {

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
        isTablet = getResources().getBoolean(R.bool.is_tablet);
    }

    private void showRecipeDetails(){
        if (isTablet){
            Bundle args = router.getArguments(RecipeDetailsPresenter.class.getName());
            long recipeId = args.getLong(ArgumentKeys.ID);
            replaceFragment(R.id.master_container, new RecipeDetailsFragment(), false);
            replaceFragment(R.id.detail_container, IngredientsFragment.newInstance(recipeId), false);
        }
        else{
            replaceFragment(R.id.master_container, new RecipeDetailsFragment(), false);
        }
    }

    private void showIngredients(){
        if (isTablet){
            Bundle args = router.getArguments(IngredientAndStepsPresenter.class.getName());
            long recipeId = args.getLong(ArgumentKeys.ID);
            replaceFragment(R.id.detail_container, IngredientsFragment.newInstance(recipeId), false);
        }
        else{
            Intent intent = new Intent(this, IngredientAndStepsActivity.class);
            startActivity(intent);
        }
    }

    private void showStep(){
        if (isTablet){
            Bundle args = router.getArguments(IngredientAndStepsPresenter.class.getName());
            long recipeId = args.getLong(ArgumentKeys.ID);
            int position = args.getInt(ArgumentKeys.STEP);
            replaceFragment(R.id.detail_container, StepFragment.newInstance(recipeId, position - 1), false);
        }
        else{
            Intent intent = new Intent(this, IngredientAndStepsActivity.class);
            startActivity(intent);
        }
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
