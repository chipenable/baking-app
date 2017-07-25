package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 25.05.2017.
 */

public interface IRecipeDetailsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showDetails(Recipe recipe);

}
