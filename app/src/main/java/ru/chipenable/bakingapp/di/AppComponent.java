package ru.chipenable.bakingapp.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.chipenable.bakingapp.presentation.presenter.RecipePresenter;
import ru.chipenable.bakingapp.ui.fragment.RecipeFragment;

/**
 * Created by Pavel.B on 20.05.2017.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    void inject(RecipeFragment obj);
    void inject(RecipePresenter obj);

}
