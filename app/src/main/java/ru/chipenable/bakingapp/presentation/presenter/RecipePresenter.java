package ru.chipenable.bakingapp.presentation.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.model.view.Recipe;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;

/**
 * Created by Pavel.B on 12.05.2017.
 */
@InjectViewState
public class RecipePresenter extends MvpPresenter<IRecipeView> {

    @Inject IRepo repo;
    @Inject Router router;
    @Inject HttpClient client;

    private final String TAG = getClass().getName();

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

        client.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> Log.d(TAG, list.toString())
                        /*throwable -> Log.d(TAG, throwable.toString()),
                        () -> {}*/
                );


    }

    public void showDetails(int position){
        router.putCommand(Command.SHOW_DETAILS);
    }


}
