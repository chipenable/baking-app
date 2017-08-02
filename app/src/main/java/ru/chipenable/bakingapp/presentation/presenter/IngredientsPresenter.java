package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IIngredientsView;

/**
 * Created by Pavel.B on 02.06.2017.
 */
@InjectViewState
public class IngredientsPresenter extends MvpPresenter<IIngredientsView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    private long recipeId;

    public IngredientsPresenter(AppComponent component) {
        component.inject(this);
    }

    public void init(long recipeId){
        this.recipeId = recipeId;
    }

    @Override
    public void attachView(IIngredientsView view) {
        super.attachView(view);
        recipeDetailsInteractor.getIngredients(recipeId)
                .subscribe(ingredients -> getViewState().showIngredients(ingredients));
    }

}
