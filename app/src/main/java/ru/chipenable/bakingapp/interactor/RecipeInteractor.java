package ru.chipenable.bakingapp.interactor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.helper.time.TimeController;
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
    private TimeController timeController;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    public RecipeInteractor(IRepo repo, HttpClient client, TimeController timeController,
                  @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler){
        this.repo = repo;
        this.client = client;
        this.timeController = timeController;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Observable<Long> updateRecipes(){
        return timeController.isItTimeToUpdate()
                .filter(result -> result == true)
                .concatMap(result -> client.getRecipes())
                .concatMap(recipes -> repo.putRecipes(recipes))
                .doOnNext(aLong -> timeController.saveTimeOfLastUpdate())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public Observable<Long> forceUpdateRecipes(){
        return client.getRecipes()
                .concatMap(recipes -> repo.putRecipes(recipes))
                .doOnNext(aLong -> timeController.saveTimeOfLastUpdate())
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);

    }

    public Observable<List<Recipe>> subscribeToRecipes(){
        return repo.getRecipeNames()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

}
