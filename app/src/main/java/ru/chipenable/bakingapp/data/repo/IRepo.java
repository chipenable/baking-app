package ru.chipenable.bakingapp.data.repo;

import java.util.List;

import io.reactivex.Observable;
import ru.chipenable.bakingapp.model.network.Recipe;
import ru.chipenable.bakingapp.model.view.RecipeViewModel;


/**
 * Created by Pavel.B on 20.05.2017.
 */

public interface IRepo {

    Observable<Long> putRecipes(List<Recipe> recipeList);

    Observable<List<RecipeViewModel>> getRecipes();

}
