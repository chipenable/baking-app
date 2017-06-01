package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IRecipeDetailsView;

/**
 * Created by Pavel.B on 25.05.2017.
 */

@InjectViewState
public class RecipeDetailsPresenter extends MvpPresenter<IRecipeDetailsView> {

    @Inject Router router;
    @Inject RecipeDetailsInteractor recipeDetailsInteractor;

    private Disposable disposable;
    private long id;

    public RecipeDetailsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        id = router.getArguments().getLong("ID");
    }

    @Override
    public void attachView(IRecipeDetailsView view) {
        super.attachView(view);
        disposable = recipeDetailsInteractor.getRecipe(id)
                .subscribe(recipe -> getViewState().showDetails(recipe));
    }

    @Override
    public void detachView(IRecipeDetailsView view) {
        super.detachView(view);
        disposable.dispose();
    }

    public void onIngredientsClick(){
        Bundle args = new Bundle();
        router.putCommand(Command.SHOW_INGREDIENTS, args);
    }

}
