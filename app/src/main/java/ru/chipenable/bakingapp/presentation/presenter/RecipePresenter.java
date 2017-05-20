package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.view.Recipe;
import ru.chipenable.bakingapp.presentation.view.IRecipeView;

/**
 * Created by Pavel.B on 12.05.2017.
 */
@InjectViewState
public class RecipePresenter extends MvpPresenter<IRecipeView> {

    public RecipePresenter(){}

    @Override
    public void attachView(IRecipeView view) {
        super.attachView(view);

        List<Recipe> testList = new ArrayList<>();
        testList.add(new Recipe.Builder(0, "Ice cream", "").build());
        testList.add(new Recipe.Builder(1, "Pizza", "").build());
        testList.add(new Recipe.Builder(2, "Cake", "").build());
        getViewState().showRecipes(testList);
    }
}
