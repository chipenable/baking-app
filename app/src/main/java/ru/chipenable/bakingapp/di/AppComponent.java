package ru.chipenable.bakingapp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.chipenable.bakingapp.presentation.presenter.IngredientsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipeDetailsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipePresenter;
import ru.chipenable.bakingapp.presentation.presenter.StepPresenter;
import ru.chipenable.bakingapp.ui.activity.MainActivity;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;

/**
 * Created by Pavel.B on 20.05.2017.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    void inject(MainActivity obj);
    void inject(RecipeFragment obj);
    void inject(RecipePresenter obj);
    void inject(RecipeDetailsFragment obj);
    void inject(RecipeDetailsPresenter obj);
    void inject(IngredientsPresenter obj);
    void inject(StepPresenter obj);
    void inject(StepFragment obj);

}
