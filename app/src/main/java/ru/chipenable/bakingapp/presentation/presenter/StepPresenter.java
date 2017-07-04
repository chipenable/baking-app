package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.model.ArgumentKeys;
import ru.chipenable.bakingapp.model.navigation.Command;
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
            recipeId = args.getLong(ArgumentKeys.ID);
            stepNum = args.getInt(ArgumentKeys.STEP);
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

    public void toPreviousPart(){
        //router.putCommand();
    }

    public void toNextPart(){
        router.putCommand(Command.SHOW_DETAILS, null, null);
    }
}
