package ru.chipenable.bakingapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import ru.chipenable.bakingapp.BuildConfig;
import ru.chipenable.bakingapp.model.network.Ingredient;
import ru.chipenable.bakingapp.model.network.Recipe;
import ru.chipenable.bakingapp.model.network.Step;
import ru.chipenable.bakingapp.model.view.RecipeViewModel;


/**
 * Created by Pavel.B on 20.05.2017.
 */

public class Repo implements IRepo {

    public boolean enableLog = false;//BuildConfig.DEBUG;
    private final String TAG = getClass().getName();
    private final RepoHelper repoHelper;
    private final Scheduler scheduler;
    private final Converter converter;
    private final PublishSubject<RepoEvent> publishSubject;


    public Repo(RepoHelper repoHelper, Scheduler scheduler, Converter converter){
        this.repoHelper = repoHelper;
        this.scheduler = scheduler;
        this.converter = converter;
        this.publishSubject = PublishSubject.create();
    }

    @Override
    public Observable<List<RecipeViewModel>> getRecipes() {
        return Observable.concat(Observable.just(RepoEvent.SUBSCRIBE), publishSubject)
                .doOnNext(repoEvent -> Log.d(TAG, "event: " + repoEvent.toString()))
                .concatMap(repoEvent -> getAllRecipes())
                .observeOn(scheduler);
    }

    @Override
    public Observable<Long> putRecipes(List<Recipe> recipeList) {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getWritableDatabase();
            db.delete(RepoContract.RecipeEntry.TABLE_NAME, null, null);
            db.delete(RepoContract.StepEntry.TABLE_NAME, null, null);
            db.delete(RepoContract.IngredientEntry.TABLE_NAME, null, null);
            for(Recipe recipe: recipeList) {
                ContentValues cv = converter.toContentValues(recipe);
                long recipeId = db.insert(RepoContract.RecipeEntry.TABLE_NAME, null, cv);
                putSteps(db, recipeId, recipe.getSteps());
                putIngredients(db, recipeId, recipe.getIngredients());
            }
            logTable(RepoContract.RecipeEntry.TABLE_NAME);
            publishSubject.onNext(RepoEvent.UPDATE);
            return 0L;
        }).observeOn(scheduler);
    }

    private Observable<List<RecipeViewModel>> getAllRecipes() {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();
            Cursor cursor = db.query(RepoContract.RecipeEntry.TABLE_NAME, null, null, null,
                    null, null, null);
            List<RecipeViewModel> list = converter.toRecipeList(cursor);
            cursor.close();
            return list;
        });
    }

    private long putSteps(SQLiteDatabase db, long recipeId, List<Step> stepList){
        for(Step step: stepList){
            ContentValues cv = converter.toContentValues(recipeId, step);
            db.insert(RepoContract.StepEntry.TABLE_NAME, null, cv);
        }
        logTable(RepoContract.StepEntry.TABLE_NAME);
        return 0L;
    }

    private long putIngredients(SQLiteDatabase db, long recipeId, List<Ingredient> ingredientList){
        for(Ingredient ingredient: ingredientList){
            ContentValues cv = converter.toContentValues(recipeId, ingredient);
            db.insert(RepoContract.IngredientEntry.TABLE_NAME, null, cv);
        }
        logTable(RepoContract.IngredientEntry.TABLE_NAME);
        return 0L;
    }

    private void logTable(String tableName) {
        if (!enableLog) return;

        SQLiteDatabase db = repoHelper.getReadableDatabase();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            Log.d(TAG, "table is empty");
        }
        while (!cursor.isAfterLast()) {
            Log.d(TAG, DatabaseUtils.dumpCurrentRowToString(cursor));
            cursor.moveToNext();
        }
    }



}
