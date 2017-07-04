package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.model.ArgumentKeys;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IIngredientAndStepsView;

/**
 * Created by Pavel.B on 04.07.2017.
 */

@InjectViewState
public class IngredientAndStepsPresenter extends MvpPresenter<IIngredientAndStepsView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    public IngredientAndStepsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    public void attachView(IIngredientAndStepsView view) {
        super.attachView(view);
        Bundle args = router.getArguments(this.getClass().getName());
        if (args != null){
            final long recipeId = args.getLong(ArgumentKeys.ID);
            final int position = args.getInt(ArgumentKeys.STEP);
            recipeDetailsInteractor.getRecipe(recipeId)
                    .subscribe(
                            recipe -> {
                                int count = recipe.steps().size() + 1;
                                getViewState().showData(recipeId, count, position);
                            },
                            throwable -> {},
                            () -> {}
                    );
        }
    }
}
