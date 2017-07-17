package ru.chipenable.bakingapp.di;

import android.appwidget.AppWidgetProvider;

import javax.inject.Singleton;

import dagger.Component;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.IngredientsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipeDetailsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipeListPresenter;
import ru.chipenable.bakingapp.presentation.presenter.StepPresenter;
import ru.chipenable.bakingapp.ui.activity.RecipeDetailsActivity;
import ru.chipenable.bakingapp.ui.activity.RecipeListActivity;
import ru.chipenable.bakingapp.ui.fragment.RecipeDetailsFragment;
import ru.chipenable.bakingapp.ui.fragment.RecipeListFragment;
import ru.chipenable.bakingapp.ui.fragment.StepFragment;
import ru.chipenable.bakingapp.widget.RecipeViewsFactory;
import ru.chipenable.bakingapp.widget.RecipeWidget;
import ru.chipenable.bakingapp.widget.RecipeWidgetService;

/**
 * Created by Pavel.B on 20.05.2017.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {

    void inject(RecipeListActivity obj);
    void inject(RecipeDetailsActivity obj);
    void inject(RecipeListFragment obj);
    void inject(RecipeListPresenter obj);
    void inject(RecipeDetailsFragment obj);
    void inject(RecipeDetailsPresenter obj);
    void inject(IngredientsPresenter obj);
    void inject(StepPresenter obj);
    void inject(StepFragment obj);
    void inject(IngredientAndStepsPresenter obj);
    void inject(RecipeWidgetService obj);
    void inject(RecipeViewsFactory obj);
    void inject(RecipeWidget obj);

}
