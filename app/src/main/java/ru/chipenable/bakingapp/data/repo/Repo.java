package ru.chipenable.bakingapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import ru.chipenable.bakingapp.BuildConfig;
import ru.chipenable.bakingapp.model.Ingredient;
import ru.chipenable.bakingapp.model.Recipe;
import ru.chipenable.bakingapp.model.Step;


/**
 * Created by Pavel.B on 20.05.2017.
 */

public class Repo implements IRepo {

    public boolean enableLog = BuildConfig.DEBUG;
    private final String TAG = getClass().getName();
    private final RepoHelper repoHelper;
    private final Converter converter;
    private final PublishSubject<RepoEvent> publishSubject;

    public Repo(RepoHelper repoHelper, Converter converter){
        this.repoHelper = repoHelper;
        this.converter = converter;
        this.publishSubject = PublishSubject.create();
    }

    @Override
    public Observable<List<Recipe>> getRecipes() {
        return Observable.concat(Observable.just(RepoEvent.SUBSCRIBE), publishSubject)
                .doOnNext(repoEvent -> Log.d(TAG, "event: " + repoEvent.toString()))
                .concatMap(repoEvent -> getAllRecipes());
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
                putSteps(db, recipeId, recipe.steps());
                putIngredients(db, recipeId, recipe.ingredients());
            }
            logTable(RepoContract.RecipeEntry.TABLE_NAME);
            publishSubject.onNext(RepoEvent.UPDATE);
            return 0L;
        });
    }

    private Observable<List<Recipe>> getAllRecipes() {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();
            Cursor cursor = db.query(RepoContract.RecipeEntry.TABLE_NAME, null, null, null,
                    null, null, null);
            List<Recipe> list = converter.toRecipeList(cursor);
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
