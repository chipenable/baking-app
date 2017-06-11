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
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;


/**
 * Created by Pavel.B on 20.05.2017.
 */

public class Repo implements IRepo {

    public final boolean enableLog = BuildConfig.DEBUG;
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
    public Observable<List<Recipe>> getRecipeNames() {
        return Observable.concat(Observable.just(RepoEvent.SUBSCRIBE), publishSubject)
                .doOnNext(repoEvent -> Log.d(TAG, "event: " + repoEvent.toString()))
                .concatMap(repoEvent -> getAllRecipeNames());
    }

    @Override
    public Observable<Long> putRecipes(List<Recipe> recipeList) {
        return Observable.fromCallable(() -> {
            long result = 0L;
            SQLiteDatabase db = repoHelper.getWritableDatabase();
            db.delete(RepoContract.RecipeEntry.TABLE_NAME, null, null);
            db.delete(RepoContract.StepEntry.TABLE_NAME, null, null);
            db.delete(RepoContract.IngredientEntry.TABLE_NAME, null, null);
            try {
                db.beginTransaction();
                for (Recipe recipe : recipeList) {
                    ContentValues cv = converter.toContentValues(recipe);
                    long recipeId = db.insert(RepoContract.RecipeEntry.TABLE_NAME, null, cv);
                    putSteps(db, recipeId, recipe.steps());
                    putIngredients(db, recipeId, recipe.ingredients());
                }
                db.setTransactionSuccessful();
                publishSubject.onNext(RepoEvent.UPDATE);
            }
            catch(Exception e){
                result = -1L;
            }
            finally {
                db.endTransaction();
            }

            logTable(RepoContract.RecipeEntry.TABLE_NAME);
            return result;
        });
    }

    @Override
    public Observable<Recipe> getRecipe(long id) {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();

            String selection = RepoContract.RecipeEntry._ID + " =? ";
            String[] selArgs = {Long.toString(id)};
            Cursor recipeCursor = db.query(RepoContract.RecipeEntry.TABLE_NAME, null, selection, selArgs,
                    null, null, null);

            selection = RepoContract.StepEntry.COL_RECIPE_ID + " =? ";
            Cursor stepCursor = db.query(RepoContract.StepEntry.TABLE_NAME, null, selection, selArgs,
                    null, null, null);
            Recipe recipe = converter.toRecipe(recipeCursor, stepCursor);
            recipeCursor.close();
            stepCursor.close();
            return recipe;
        });
    }

    @Override
    public Observable<Step> getStep(long id, int num) {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();
            String selection = RepoContract.StepEntry.COL_RECIPE_ID + " =? AND " +
                    RepoContract.StepEntry.COL_STEP_NUM + " =?";
            String[] selArgs = {Long.toString(id), Integer.toString(num)};
            Cursor cursor = db.query(RepoContract.StepEntry.TABLE_NAME, null, selection, selArgs,
                    null, null, null);
            Step step = converter.toStep(cursor);
            cursor.close();
            return step;
        });
    }

    @Override
    public Observable<List<Ingredient>> getIngredients(long recipeId) {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();
            String selection = RepoContract.StepEntry.COL_RECIPE_ID + " =?";
            String[] selArgs = {Long.toString(recipeId)};
            Cursor cursor = db.query(RepoContract.IngredientEntry.TABLE_NAME, null, selection, selArgs,
                    null, null, null);
            List<Ingredient> list = converter.toIngredients(cursor);
            cursor.close();
            return list;
        });
    }

    /** helper methods */

    private Observable<List<Recipe>> getAllRecipeNames() {
        return Observable.fromCallable(() -> {
            SQLiteDatabase db = repoHelper.getReadableDatabase();
            Cursor cursor = db.query(RepoContract.RecipeEntry.TABLE_NAME, null, null, null,
                    null, null, null);
            List<Recipe> list = converter.toRecipeNameList(cursor);
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
