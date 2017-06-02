package ru.chipenable.bakingapp.interactor;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.di.IoScheduler;
import ru.chipenable.bakingapp.di.UiScheduler;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

/**
 * Created by Pavel.B on 29.05.2017.
 */

public class RecipeDetailsInteractor {

    private IRepo repo;
    private Scheduler ioScheduler;
    private Scheduler uiScheduler;

    @Inject
    public RecipeDetailsInteractor(IRepo repo, @IoScheduler Scheduler ioScheduler,
                                   @UiScheduler Scheduler uiScheduler){
        this.repo = repo;
        this.ioScheduler = ioScheduler;
        this.uiScheduler = uiScheduler;
    }

    public Observable<Recipe> getRecipe(long id){
        return repo.getRecipe(id)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

    public Observable<Step> getStep(long recipeId, int stepNum){
        return repo.getStep(recipeId, stepNum)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler);
    }

}
