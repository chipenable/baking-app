package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
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

    public RecipeDetailsPresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    public void attachView(IRecipeDetailsView view) {
        super.attachView(view);
        long id = router.getArguments().getLong("ID");
        disposable = recipeDetailsInteractor.getRecipe(id)
                .subscribe(recipe -> getViewState().showDetails(recipe));
    }

    @Override
    public void detachView(IRecipeDetailsView view) {
        super.detachView(view);
        disposable.dispose();
    }
}
