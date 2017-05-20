package ru.chipenable.bakingapp.data.repo;



import java.util.List;

import io.reactivex.Observable;
import ru.chipenable.bakingapp.model.view.Recipe;

/**
 * Created by Pavel.B on 20.05.2017.
 */

public interface IRepo {

    Observable<List<Recipe>> getRecipes();

}
