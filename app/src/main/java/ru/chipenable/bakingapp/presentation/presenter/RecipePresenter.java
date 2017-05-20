package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.view.Recipe;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;

/**
 * Created by Pavel.B on 12.05.2017.
 */
@InjectViewState
public class RecipePresenter extends MvpPresenter<IRecipeView> {

    @Inject IRepo repo;

    public RecipePresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    public void attachView(IRecipeView view) {
        super.attachView(view);

        repo.getRecipes()
                .subscribe(
                        getViewState()::showRecipes,
                        throwable -> {},
                        () -> {}
                );

    }
}
