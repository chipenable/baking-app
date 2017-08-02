package ru.chipenable.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import ru.chipenable.bakingapp.ui.common.CustomActivity;
import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

public class RecipeDetailsActivity extends CustomActivity implements INavigator {

    @Inject Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        ((BakingApp)getApplication()).getAppComponent().inject(this);
        ActivityUtil.setDisplayHomeAsUpEnabled(this);
        showRecipeDetails();
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



    private void showRecipeDetails(){
        if (isTablet){
            Bundle args = router.getArguments(RecipeDetailsPresenter.class.getName());
            long recipeId = args.getLong(ArgumentKeys.ID);
            addFragment(R.id.detail_container, () -> IngredientsFragment.newInstance(recipeId), false);
        }

        addFragment(R.id.master_container, RecipeDetailsFragment::new, false);
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
