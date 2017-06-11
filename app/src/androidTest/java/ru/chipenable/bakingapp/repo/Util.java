package ru.chipenable.bakingapp.repo;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.data.repo.RepoContract;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

/**
 * Created by Pavel.B on 12.06.2017.
 */

public class Util {

    public static List<Recipe> creteTestRecipeList(){
        List<Recipe> recipeList = new ArrayList<>();

        List<Step> stepList = new ArrayList<>();
        Step step = Step.builder()
                .setId(0)
                .setShortDescription("Recipe Introduction")
                .setDescription("Recipe Introduction")
                .setVideoURL("https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4")
                .setThumbnailURL("")
                .build();
        stepList.add(step);

        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = Ingredient.create(2, "CUP", "Graham Cracker crumbs");
        ingredientList.add(ingredient);

        Recipe recipe = Recipe.builder()
                .setId(0)
                .setName("Nutella Pie")
                .setImageUrl("")
                .setServings(8)
                .setSteps(stepList)
                .setIngredients(ingredientList)
                .build();
        recipeList.add(recipe);

        return recipeList;
    }

    public static void deleteRecipes(RepoHelper repoHelper){
        deleteTable(repoHelper, RepoContract.RecipeEntry.TABLE_NAME);
        deleteTable(repoHelper, RepoContract.StepEntry.TABLE_NAME);
        deleteTable(repoHelper, RepoContract.IngredientEntry.TABLE_NAME);
    }

    public static void deleteTable(RepoHelper repoHelper, String table){
        SQLiteDatabase db = repoHelper.getWritableDatabase();
        db.delete(table, null, null);
    }

}
