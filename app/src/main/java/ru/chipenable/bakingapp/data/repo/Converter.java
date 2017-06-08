package ru.chipenable.bakingapp.data.repo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

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
        cv.put(StepEntry.COL_STEP_NUM, step.id());
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
        cv.put(IngredientEntry.COL_QUANTITY, ingredient.quantity());
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

    public Recipe toRecipe(Cursor cursor){
        List<Step> stepList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String description = getString(cursor, StepEntry.COL_DESCRIPTION);
                String shortDescription = getString(cursor, StepEntry.COL_SHORT_DESCRIPTION);
                String videoUrl = getString(cursor, StepEntry.COL_VIDEO_URL);
                String thumbnailUrl = getString(cursor, StepEntry.COL_THUMBNAIL_URL);
                Step step = Step.builder()
                        .setId(0)
                        .setShortDescription(shortDescription)
                        .setDescription(description)
                        .setVideoURL(videoUrl)
                        .setThumbnailURL(thumbnailUrl)
                        .build();
                stepList.add(step);
            } while (cursor.moveToNext());
        }

        return Recipe.builder()
                .setId(0)
                .setName("")
                .setSteps(stepList)
                .setIngredients(new ArrayList<>())
                .setServings(0)
                .setImageUrl("")
                .build();
    }

    public Step toStep(Cursor cursor){
        int num = 0;
        String shortDescription = "";
        String description = "";
        String videoUrl = "";
        String thumbnailUrl = "";

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            num = getInt(cursor, StepEntry.COL_STEP_NUM);
            shortDescription = getString(cursor, StepEntry.COL_SHORT_DESCRIPTION);
            description = getString(cursor, StepEntry.COL_DESCRIPTION);
            videoUrl = getString(cursor, StepEntry.COL_VIDEO_URL);
            thumbnailUrl = getString(cursor, StepEntry.COL_THUMBNAIL_URL);
        }

        return Step.builder()
                .setId(num)
                .setShortDescription(shortDescription)
                .setDescription(description)
                .setVideoURL(videoUrl)
                .setThumbnailURL(thumbnailUrl)
                .build();
    }

    public List<Ingredient> toIngredients(Cursor cursor){
        List<Ingredient> list = new ArrayList<>();
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                float quantity = getFloat(cursor, IngredientEntry.COL_QUANTITY);
                String measure = getString(cursor, IngredientEntry.COL_MEASURE);
                String ingredient = getString(cursor, IngredientEntry.COL_INGREDIENT);
                list.add(Ingredient.create(quantity, measure, ingredient));
            }while(cursor.moveToNext());
        }
        return list;
    }

    /** helper methods */

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

    private float getFloat(Cursor cursor, String colName){
        int colInd = cursor.getColumnIndex(colName);
        return cursor.getFloat(colInd);
    }

}
