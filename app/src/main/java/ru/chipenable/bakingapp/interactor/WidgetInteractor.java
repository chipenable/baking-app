package ru.chipenable.bakingapp.interactor;

import android.content.SharedPreferences;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 17.07.2017.
 */

public class WidgetInteractor {

    private final IRepo repo;
    private final SharedPreferences pref;
    private int recipeIndex;

    private static final String RECIPE_INDEX_KEY = "recipe_index_key";

    public WidgetInteractor(IRepo repo, SharedPreferences pref){
        this.repo = repo;
        this.pref = pref;
        recipeIndex = pref.getInt(RECIPE_INDEX_KEY, 0);
    }

    public Observable<Boolean> incRecipeIndex(){
        recipeIndex = pref.getInt(RECIPE_INDEX_KEY, 0);
        return repo.getRecipeNames()
                .map(List::size)
                .map(recipeAmount -> {
                    if (recipeIndex < recipeAmount - 1){
                        recipeIndex++;
                        pref.edit().putInt(RECIPE_INDEX_KEY, recipeIndex).commit();
                        return true;
                    }
                    return false;
                });
    }

    public Observable<Boolean> decRecipeIndex(){
        boolean result = false;

        recipeIndex = pref.getInt(RECIPE_INDEX_KEY, 0);
        if (recipeIndex > 0) {
            recipeIndex--;
            result = true;
        }

        pref.edit().putInt(RECIPE_INDEX_KEY, recipeIndex).commit();
        return Observable.just(result);
    }

    public Observable<Recipe> getRecipe(){
        return repo.getRecipeNames()
                .map(recipes -> recipes.get(recipeIndex))
                .map(Recipe::id)
                .concatMap(repo::getRecipe)
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline());
    }

}
