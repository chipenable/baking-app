package ru.chipenable.bakingapp.presentation.presenter;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.di.UiScheduler;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.model.Recipe;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;

/**
 * Created by Pavel.B on 12.05.2017.
 */
@InjectViewState
public class RecipePresenter extends MvpPresenter<IRecipeView> {

    @Inject Router router;
    @Inject RecipeInteractor recipeInteractor;

    private Disposable disposable;
    private List<Recipe> recipeList;
    private final String TAG = getClass().getName();

    public RecipePresenter(AppComponent component){
        component.inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        recipeInteractor.updateRecipes()
                .subscribe(aLong -> {}, throwable -> Log.d(TAG, throwable.toString()), () -> {});
    }

    @Override
    public void attachView(IRecipeView view) {
        super.attachView(view);
        disposable = recipeInteractor.subscribeToRecipes()
                .subscribe(result -> {
                    this.recipeList = result;
                    getViewState().showRecipes(result);
                });
    }

    @Override
    public void detachView(IRecipeView view) {
        super.detachView(view);
        disposable.dispose();
    }

    public void showDetails(int position){
        Bundle args = new Bundle();
        args.putLong("ID", recipeList.get(position).id());
        router.putCommand(Command.SHOW_DETAILS, RecipeDetailsPresenter.class.getName(), args);
    }


}
