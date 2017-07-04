package ru.chipenable.bakingapp.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.util.ActivityUtil;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.presentation.view.IIngredientAndStepsView;
import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;
import ru.chipenable.bakingapp.ui.other.RecipePagerAdapter;

public class IngredientAndStepsActivity extends MvpAppCompatActivity implements IIngredientAndStepsView {

    @InjectPresenter
    IngredientAndStepsPresenter presenter;

    @ProvidePresenter
    IngredientAndStepsPresenter providePresenter(){
        return new IngredientAndStepsPresenter(((BakingApp) getApplication()).getAppComponent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_and_step);
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
    public void showData(long recipeId, int count, int position) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment f;
        if (position == 0) {
            f = IngredientsFragment.newInstance(recipeId);
        }
        else {
            f = StepFragment.newInstance(recipeId, position - 1);
        }
        fm.beginTransaction()
                .add(R.id.fragment_container, f)
                .commit();
    }

}
