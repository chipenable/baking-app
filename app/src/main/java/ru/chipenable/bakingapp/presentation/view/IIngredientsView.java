package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Pavel.B on 02.06.2017.
 */

public interface IIngredientsView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showIngredients();

}
