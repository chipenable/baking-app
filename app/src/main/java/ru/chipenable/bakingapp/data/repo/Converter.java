package ru.chipenable.bakingapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.network.Ingredient;
import ru.chipenable.bakingapp.model.network.Recipe;
import ru.chipenable.bakingapp.model.network.Step;
import ru.chipenable.bakingapp.model.view.RecipeViewModel;

import static ru.chipenable.bakingapp.data.repo.RepoContract.*;

/**
 * Created by Pavel.B on 26.05.2017.
 */

public class Converter {

    public ContentValues toContentValues(Recipe recipe){
        ContentValues cv = new ContentValues();
        cv.put(RecipeEntry.COL_NAME, recipe.getName());
        cv.put(RecipeEntry.COL_IMAGE_URL, recipe.getImage());
        cv.put(RecipeEntry.COL_SERVINGS, recipe.getServings());
        return cv;
    }

    public ContentValues toContentValues(long recipeId, Step step){
        ContentValues cv = new ContentValues();
        cv.put(StepEntry.COL_RECIPE_ID, recipeId);
        cv.put(StepEntry.COL_DESCRIPTION, step.getDescription());
        cv.put(StepEntry.COL_SHORT_DESCRIPTION, step.getShortDescription());
        cv.put(StepEntry.COL_THUMBNAIL_URL, step.getThumbnailURL());
        cv.put(StepEntry.COL_VIDEO_URL, step.getVideoURL());
        return cv;
    }

    public ContentValues toContentValues(long recipeId, Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(IngredientEntry.COL_RECIPE_ID, recipeId);
        cv.put(IngredientEntry.COL_INGREDIENT, ingredient.getIngredient());
        cv.put(IngredientEntry.COL_MEASURE, ingredient.getMeasure());
        cv.put(IngredientEntry.COL_QUANTITY, ingredient.getMeasure());
        return cv;
    }

    public List<RecipeViewModel> toRecipeList(Cursor cursor){
        List<RecipeViewModel> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                long id = getLong(cursor, RecipeEntry._ID);
                String name = getString(cursor, RecipeEntry.COL_NAME);
                String imageUrl = getString(cursor, RecipeEntry.COL_IMAGE_URL);
                RecipeViewModel recipe = new RecipeViewModel.Builder(id, name, imageUrl).build();
                list.add(recipe);
            }while(cursor.moveToNext());
        }
        return list;
    }

    private long getLong(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getLong(colInd);
    }

    private int getInt(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getInt(colInd);
    }

    private String getString(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getString(colInd);
    }

}
