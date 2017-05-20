package ru.chipenable.bakingapp.data.repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import ru.chipenable.bakingapp.model.view.Recipe;

/**
 * Created by Pavel.B on 20.05.2017.
 */

public class Repo implements IRepo {

    private RepoHelper repoHelper;

    public Repo(RepoHelper repoHelper){
        this.repoHelper = repoHelper;
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        List<Recipe> testList = new ArrayList<>();
        testList.add(new Recipe.Builder(0, "Ice cream", "").build());
        testList.add(new Recipe.Builder(1, "Pizza", "").build());
        testList.add(new Recipe.Builder(2, "Cake", "").build());
        testList.add(new Recipe.Builder(3, "Chicken", "").build());
        return Observable.just(testList);
    }
}
