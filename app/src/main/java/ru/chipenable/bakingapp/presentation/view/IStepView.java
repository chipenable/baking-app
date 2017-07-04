package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.chipenable.bakingapp.model.data.Step;

/**
 * Created by Pavel.B on 02.06.2017.
 */

public interface IStepView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showStep(Step step);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void releaseResources();

}
