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

    public void incRecipeIndex(){
        recipeIndex = pref.getInt(RECIPE_INDEX_KEY, 0);
        recipeIndex++;
        pref.edit().putInt(RECIPE_INDEX_KEY, recipeIndex).commit();
    }

    public void decRecipeIndex(){
        recipeIndex = pref.getInt(RECIPE_INDEX_KEY, 0);
        if (recipeIndex > 0) {
            recipeIndex--;
        }

        pref.edit().putInt(RECIPE_INDEX_KEY, recipeIndex).commit();
    }

    public Observable<Recipe> getRecipe(){
        return repo.getRecipeNames()
                .map(recipes -> recipes.get(recipeIndex))
                .map(recipe -> recipe.id())
                .concatMap(aLong -> repo.getRecipe(aLong))
                .subscribeOn(Schedulers.trampoline());
    }

}
