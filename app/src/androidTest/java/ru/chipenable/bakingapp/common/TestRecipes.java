package ru.chipenable.bakingapp.common;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.data.Recipe;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class TestRecipes {

    public TestRecipes(){}

    public List<Recipe> getRecipes(){
        List<Recipe> list = new ArrayList<>();

        Recipe recipe1 = Recipe.builder()
                .setId(1)
                .setName("Apple Pie")
                .setIngredients(new ArrayList<>())
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
