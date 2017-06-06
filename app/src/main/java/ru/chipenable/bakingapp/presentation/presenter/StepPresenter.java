package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.model.data.Step;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IStepView;

/**
 * Created by Pavel.B on 02.06.2017.
 */

@InjectViewState
public class StepPresenter extends MvpPresenter<IStepView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    private final String TAG = getClass().getName();
    private long recipeId;
    private int stepNum;

    public StepPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(this.getClass().getName());
        if (args != null){
            recipeId = args.getLong("ID");
            stepNum = args.getInt("STEP");
            Log.d(TAG, "id: " + Long.toString(recipeId) + " num: " + Integer.toString(stepNum));
        }
    }

    @Override
    public void attachView(IStepView view) {
        super.attachView(view);
        recipeDetailsInteractor.getStep(recipeId, stepNum)
                .subscribe(
                        step -> getViewState().showStep(step)
                );
    }

}