package ru.chipenable.bakingapp.data.repo;

import java.util.List;

import io.reactivex.Observable;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;


/**
 * Created by Pavel.B on 20.05.2017.
 */

public interface IRepo {

    Observable<Long> putRecipes(List<Recipe> recipeList);

    Observable<List<Recipe>> getRecipeNames();

    Observable<Recipe> getRecipe(long id);

    Observable<Step> getStep(long recipeId, int num);

    Observable<List<Ingredient>> getIngredients(long recipeId);

}
