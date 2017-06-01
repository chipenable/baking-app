package ru.chipenable.bakingapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.chipenable.bakingapp.di.AppComponent;
import ru.chipenable.bakingapp.presentation.view.IIngredientsView;

/**
 * Created by Pavel.B on 02.06.2017.
 */
@InjectViewState
public class IngredientsPresenter extends MvpPresenter<IIngredientsView> {

    public IngredientsPresenter(AppComponent component){
        component.inject(this);
    }

}
