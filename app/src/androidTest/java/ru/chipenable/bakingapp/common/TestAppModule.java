package ru.chipenable.bakingapp.common;

import android.content.Context;
import android.content.SharedPreferences;

import org.mockito.Mockito;

import java.util.List;

import javax.inject.Singleton;

import dagger.Provides;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.di.AppModule;
import ru.chipenable.bakingapp.helper.time.TimeController;
import ru.chipenable.bakingapp.model.data.Recipe;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


/**
 * Created by Pavel.B on 20.05.2017.
 */


public class TestAppModule extends AppModule {

    public TestAppModule(Context context) {
        super(context);
    }

    @Override
    public HttpClient provideClient(){
        HttpClient httpClient = mock(HttpClient.class);
        Observable<List<Recipe>> recipeList = Observable.just(new TestRecipes().getRecipes());
        when(httpClient.getRecipes()).thenReturn(recipeList);
        return httpClient;
    }

    @Override
    public TimeController provideTimeController(SharedPreferences pref){
        TimeController timeController = mock(TimeController.class);
        when(timeController.isItTimeToUpdate()).thenReturn(Observable.just(true));
        doNothing().when(timeController).saveTimeOfLastUpdate();
        return timeController;
    }

}
