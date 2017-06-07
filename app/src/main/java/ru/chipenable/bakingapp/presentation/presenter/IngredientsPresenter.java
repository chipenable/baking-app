package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.model.ArgumentKeys;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IIngredientsView;

/**
 * Created by Pavel.B on 02.06.2017.
 */
@InjectViewState
public class IngredientsPresenter extends MvpPresenter<IIngredientsView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    private final String TAG = getClass().getName();
    private long recipeId = -1;

    public IngredientsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Bundle args = router.getArguments(this.getClass().getName());
        if (args != null){
            recipeId = args.getLong(ArgumentKeys.ID);
        }
    }

    @Override
    public void attachView(IIngredientsView view) {
        super.attachView(view);
        recipeDetailsInteractor.getIngredients(recipeId)
                .subscribe(ingredients -> Log.d(TAG, ingredients.toString()));
    }

}
