package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Pavel.B on 04.07.2017.
 */

public interface IIngredientAndStepsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showData(long recipeId, int position);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void enableNavigation(boolean enablePrevBut, boolean enableNextBut);

}
