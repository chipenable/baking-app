package ru.chipenable.bakingapp.di;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.chipenable.bakingapp.data.network.AutoValueGsonFactory;
import ru.chipenable.bakingapp.data.network.HttpClient;
import ru.chipenable.bakingapp.helper.time.TimeController;
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.interactor.RecipeDetailsInteractor;
import ru.chipenable.bakingapp.interactor.RecipeInteractor;
import ru.chipenable.bakingapp.interactor.WidgetInteractor;
import ru.chipenable.bakingapp.model.navigation.Router;


/**
 * Created by Pavel.B on 20.05.2017.
 */

@Module
public class AppModule {

    private final Context context;
    private static final String PREF_NAME = "app_preferences";

    public AppModule(Context context){
        this.context = context;
    }

    @ApplicationContext
    @Singleton
    @Provides
    public Context provideApplicationContext(){
        return context;
    }

    @Singleton
    @Provides
    public IRepo provideRepo(RepoHelper repoHelper){
        Converter converter = new Converter();
        return new Repo(repoHelper, converter);
    }

    @Singleton
    @Provides
    Router provideRouter(){
        return new Router();
    }

    @Singleton
    @Provides
    public HttpClient provideClient(){
        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().registerTypeAdapterFactory(AutoValueGsonFactory.create())
                        .create());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpClient.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();

        return retrofit.create(HttpClient.class);
    }

    @IoScheduler
    @Singleton
    @Provides
    public Scheduler provideIoScheduler(){
        return Schedulers.io();
    }

    @UiScheduler
    @Singleton
    @Provides
    public Scheduler provideUiScheduler(){
        return AndroidSchedulers.mainThread();
    }

    @Singleton
    @Provides
    public RecipeInteractor provideRecipeInteractor(IRepo repo, HttpClient client, TimeController timeController,
                            @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler){
        return new RecipeInteractor(repo, client, timeController, ioScheduler, uiScheduler);
    }

    @Singleton
    @Provides
    public RecipeDetailsInteractor provideRecipeDetailsInteractor(IRepo repo,
                            @IoScheduler Scheduler ioScheduler, @UiScheduler Scheduler uiScheduler){
        return new RecipeDetailsInteractor(repo, ioScheduler, uiScheduler);
    }

    @Singleton
    @Provides
    public SharedPreferences providePreferences(@ApplicationContext Context context){
        return context.getSharedPreferences(PREF_NAME, Activity.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public TimeController provideTimeController(SharedPreferences pref){
        return new TimeController(pref);
    }

    @Singleton
    @Provides
    public WidgetInteractor provideWidgetInteractor(IRepo repo, SharedPreferences pref){
        return new WidgetInteractor(repo, pref);
    }

}
