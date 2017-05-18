package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.chipenable.bakingapp.model.view.Recipe;

/**
 * Created by Pavel.B on 12.05.2017.
 */

public interface IRecipeView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showRecipes(List<Recipe> list);

}
