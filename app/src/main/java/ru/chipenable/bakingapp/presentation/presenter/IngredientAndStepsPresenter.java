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
import ru.chipenable.bakingapp.presentation.view.IIngredientAndStepsView;

/**
 * Created by Pavel.B on 04.07.2017.
 */

@InjectViewState
public class IngredientAndStepsPresenter extends MvpPresenter<IIngredientAndStepsView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    private long recipeId;
    private int position;
    private int count;

    public IngredientAndStepsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(this.getClass().getName());
        if (args != null){
            recipeId = args.getLong(ArgumentKeys.ID);
            position = args.getInt(ArgumentKeys.STEP);
        }
    }

    @Override
    public void attachView(IIngredientAndStepsView view) {
        super.attachView(view);
        recipeDetailsInteractor.getRecipe(recipeId)
                .subscribe(
                        recipe -> {
                            count = recipe.steps().size();
                            getViewState().showData(recipeId, position);
                            enableNavigation(0, position, count);
                        },
                        throwable -> {},
                        () -> {}
                );
    }

    public void toPreviousPart(){
        if (position > 0){
            position--;
            getViewState().showData(recipeId, position);
        }

        enableNavigation(0, position, count);
    }

    public void toNextPart(){
        if (position < count) {
            position++;
            getViewState().showData(recipeId, position);
        }

        enableNavigation(0, position, count);
    }

    private void enableNavigation(int min, int position, int max){
        getViewState().enableNavigation(position > min, position < max);
    }

}
