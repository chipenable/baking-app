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
        testList.add(new Recipe(0, "Ice cream", ""));
        testList.add(new Recipe(1, "Pizza", ""));
        testList.add(new Recipe(2, "Cake", ""));
        getViewState().showRecipes(testList);
    }
}
