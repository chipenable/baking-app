package ru.chipenable.bakingapp.common;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class TestRecipes {

    public TestRecipes(){}

    public List<Recipe> getRecipes(){
        List<Recipe> list = new ArrayList<>();

        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.create(0.5f, "cup", "sugar"));
        ingredients.add(Ingredient.create(0.25f, "cup", "flour"));
        ingredients.add(Ingredient.create(0.5f, "tsp", "ground cinnamon"));
        ingredients.add(Ingredient.create(0.5f, "tsp", "ground nutmeg"));
        ingredients.add(Ingredient.create(0.1f, "tsp", "salt"));
        ingredients.add(Ingredient.create(8, "cup", "thinly sliced peeled tart apples"));
        ingredients.add(Ingredient.create(2, "tbl", "butter"));

        Recipe recipe1 = Recipe.builder()
                .setId(1)
                .setName("Apple Pie")
                .setIngredients(ingredients)
                .setSteps(new ArrayList<>())
                .setServings(4)
                .setImageUrl("")
                .build();

        Recipe recipe2 = Recipe.builder()
                .setId(2)
                .setName("Apple Crumble")
                .setIngredients(new ArrayList<>())
                .setSteps(new ArrayList<>())
                .setServings(4)
                .setImageUrl("")
                .build();

        list.add(recipe1);
        list.add(recipe2);

        return list;
    }




}
