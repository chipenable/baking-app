package ru.chipenable.bakingapp.di;

import android.content.Context;

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
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.model.navigation.Router;


/**
 * Created by Pavel.B on 20.05.2017.
 */

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    @ApplicationContext
    @Singleton
    @Provides
    Context provideApplicationContext(){
        return context;
    }

    @Singleton
    @Provides
    IRepo provideRepo(RepoHelper repoHelper){
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

}
