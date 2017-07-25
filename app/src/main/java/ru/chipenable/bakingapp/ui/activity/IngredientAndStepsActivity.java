package ru.chipenable.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Button;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.chipenable.bakingapp.BakingApp;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.model.util.ActivityUtil;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.presentation.view.IIngredientAndStepsView;
import ru.chipenable.bakingapp.ui.common.CustomMvpActivity;
import ru.chipenable.bakingapp.ui.fragment.IngredientsFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

public class IngredientAndStepsActivity extends CustomMvpActivity implements IIngredientAndStepsView {

    @BindView(R.id.prev_but) Button prevBut;
    @BindView(R.id.next_but) Button nextBut;

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
        ButterKnife.bind(this);
        prevBut.setOnClickListener(v -> presenter.toPreviousPart());
        nextBut.setOnClickListener(v -> presenter.toNextPart());
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
    public void showData(long recipeId, int position) {
        FragmentManager fm = getSupportFragmentManager();
        Fragment newFragment;

        if (position == 0) {
            newFragment = IngredientsFragment.newInstance(recipeId);
        }
        else {
            newFragment = StepFragment.newInstance(recipeId, position - 1);
        }

        fm.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }

    @Override
    public void enableNavigation(boolean enablePrevBut, boolean enableNextBut) {
        prevBut.setEnabled(enablePrevBut);
        nextBut.setEnabled(enableNextBut);
    }

}
