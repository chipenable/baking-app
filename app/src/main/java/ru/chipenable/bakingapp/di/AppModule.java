package ru.chipenable.bakingapp.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;

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
        return new Repo(repoHelper);
    }

}
