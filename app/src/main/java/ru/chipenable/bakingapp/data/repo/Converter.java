package ru.chipenable.bakingapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.Ingredient;
import ru.chipenable.bakingapp.model.Recipe;
import ru.chipenable.bakingapp.model.Step;

import static ru.chipenable.bakingapp.data.repo.RepoContract.*;

/**
 * Created by Pavel.B on 26.05.2017.
 */

public class Converter {

    public ContentValues toContentValues(Recipe recipe){
        ContentValues cv = new ContentValues();
        cv.put(RecipeEntry.COL_NAME, recipe.name());
        cv.put(RecipeEntry.COL_IMAGE_URL, recipe.imageUrl());
        cv.put(RecipeEntry.COL_SERVINGS, recipe.servings());
        return cv;
    }

    public ContentValues toContentValues(long recipeId, Step step){
        ContentValues cv = new ContentValues();
        cv.put(StepEntry.COL_RECIPE_ID, recipeId);
        cv.put(StepEntry.COL_DESCRIPTION, step.description());
        cv.put(StepEntry.COL_SHORT_DESCRIPTION, step.shortDescription());
        cv.put(StepEntry.COL_THUMBNAIL_URL, step.thumbnailURL());
        cv.put(StepEntry.COL_VIDEO_URL, step.videoURL());
        return cv;
    }

    public ContentValues toContentValues(long recipeId, Ingredient ingredient){
        ContentValues cv = new ContentValues();
        cv.put(IngredientEntry.COL_RECIPE_ID, recipeId);
        cv.put(IngredientEntry.COL_INGREDIENT, ingredient.ingredient());
        cv.put(IngredientEntry.COL_MEASURE, ingredient.measure());
        cv.put(IngredientEntry.COL_QUANTITY, ingredient.measure());
        return cv;
    }

    public List<Recipe> toRecipeList(Cursor cursor){
        List<Recipe> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                long id = getLong(cursor, RecipeEntry._ID);
                String name = getString(cursor, RecipeEntry.COL_NAME);
                String imageUrl = getString(cursor, RecipeEntry.COL_IMAGE_URL);
                Recipe recipe = Recipe.builder()
                        .setId(id)
                        .setName(name)
                        .setIngredients(new ArrayList<>())
                        .setSteps(new ArrayList<>())
                        .setServings(0)
                        .setImageUrl(imageUrl)
                        .build();
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
