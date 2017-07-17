package ru.chipenable.bakingapp.interactor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.model.data.Ingredient;

/**
 * Created by Pavel.B on 17.07.2017.
 */

public class WidgetInteractor {

    private IRepo repo;

    public WidgetInteractor(IRepo repo){
        this.repo = repo;
    }

    public Observable<List<Ingredient>> getIngredients(){
        return repo.getRecipeNames()
                .map(recipes -> recipes.get(0))
                .map(recipe -> recipe.id())
                .concatMap(aLong -> repo.getIngredients(aLong))
                .subscribeOn(Schedulers.trampoline());
    }

}
