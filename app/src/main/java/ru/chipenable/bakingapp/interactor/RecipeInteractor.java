package ru.chipenable.bakingapp.interactor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.di.IoScheduler;
import ru.chipenable.bakingapp.di.UiScheduler;
import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 26.05.2017.
 */

public class RecipeInteractor {

    private IRepo repo;
    private HttpClient client;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    @Inject
    public RecipeInteractor(IRepo repo, HttpClient client, @IoScheduler Scheduler ioScheduler,
                            @UiScheduler Scheduler uiScheduler){
        this.repo = repo;
        this.client = client;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Observable<Long> updateRecipes(){
        return client.getRecipes()
                .concatMap(recipes -> repo.putRecipes(recipes))
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public Observable<List<Recipe>> subscribeToRecipes(){
        return repo.getRecipes()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

}
