package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
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

    public void init(long recipeId, int stepNum){
        this.recipeId = recipeId;
        this.stepNum = stepNum;
    }

    public void start(){
        recipeDetailsInteractor.getStep(recipeId, stepNum)
                .subscribe(
                        step -> getViewState().showStep(step)
                );
    }

    public void stop(){
        getViewState().releaseResources();
    }

}
