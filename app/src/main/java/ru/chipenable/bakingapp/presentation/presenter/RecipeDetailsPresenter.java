package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.presentation.view.IRecipeDetailsView;

/**
 * Created by Pavel.B on 25.05.2017.
 */

@InjectViewState
public class RecipeDetailsPresenter extends MvpPresenter<IRecipeDetailsView> {

    public RecipeDetailsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    public void attachView(IRecipeDetailsView view) {
        super.attachView(view);
    }

    @Override
    public void detachView(IRecipeDetailsView view) {
        super.detachView(view);
    }
}
