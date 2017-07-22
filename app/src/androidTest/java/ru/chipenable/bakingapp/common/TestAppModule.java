package ru.chipenable.bakingapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.mockito.Mockito;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.di.AppModule;
import ru.chipenable.bakingapp.helper.time.TimeController;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.interactor.WidgetInteractor;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.navigation.Router;

import static org.mockito.Mockito.when;


/**
 * Created by Pavel.B on 20.05.2017.
 */


public class TestAppModule extends AppModule {

    public TestAppModule(Context context){
        super(context);
    }

    @Override
    public HttpClient provideClient(){
        HttpClient httpClient = Mockito.mock(HttpClient.class);
        List<Recipe> testRecipes = new TestRecipes().getRecipes();
        when(httpClient.getRecipes()).thenReturn(Observable.just(testRecipes));
        return httpClient;
    }

}
