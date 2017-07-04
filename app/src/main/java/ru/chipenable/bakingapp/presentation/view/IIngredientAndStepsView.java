package ru.chipenable.bakingapp.presentation.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by Pavel.B on 04.07.2017.
 */

public interface IIngredientAndStepsView extends MvpView {

    void showData(long recipeId, int count, int position);

}
