package ru.chipenable.bakingapp.common;

import java.util.ArrayList;
import java.util.List;

import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

/**
 * Created by Pavel.B on 22.07.2017.
 */

public class TestRecipes {

    public TestRecipes(){}

    public List<Recipe> getRecipes(){
        List<Recipe> list = new ArrayList<>();

        /** create recipe 1*/

        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(Ingredient.create(0.5f, "cup", "sugar"));
        ingredients1.add(Ingredient.create(0.25f, "cup", "flour"));
        ingredients1.add(Ingredient.create(0.5f, "tsp", "ground cinnamon"));
        ingredients1.add(Ingredient.create(0.5f, "tsp", "ground nutmeg"));
        ingredients1.add(Ingredient.create(0.1f, "tsp", "salt"));
        ingredients1.add(Ingredient.create(8, "cup", "thinly sliced peeled tart apples"));
        ingredients1.add(Ingredient.create(2, "tbl", "butter"));

        List<Step> steps1 = new ArrayList<>();
        steps1.add(Step.builder()
                .setId(0)
                .setShortDescription("Prepare Double-Crust Pastry.")
                .setDescription("Heat oven to 425ºF. Prepare Double-Crust Pastry.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps1.add(Step.builder()
                .setId(1)
                .setShortDescription("Mix sugar, flour, cinnamon, nutmeg and salt in large bowl.")
                .setDescription("Mix sugar, flour, cinnamon, nutmeg and salt in large bowl. Stir in apples. " +
                        "Turn into pastry-lined pie plate. Dot with butter. Trim overhanging edge of pastry " +
                        "1/2 inch from rim of plate.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps1.add(Step.builder()
                .setId(2)
                .setShortDescription("Roll other round of pastry.")
                .setDescription("Roll other round of pastry. Fold into fourths and cut slits so " +
                        "steam can escape. Unfold top pastry over filling; trim overhanging edge " +
                        "1 inch from rim of plate. Fold and roll top edge under lower edge, pressing " +
                        "on rim to seal; flute as desired. Cover edge with 3-inch strip of aluminum " +
                        "foil to prevent excessive browning. Remove foil during last 15 minutes of " +
                        "baking.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps1.add(Step.builder()
                .setId(3)
                .setShortDescription("Bake 40 to 50 minutes or until crust is brown and juice " +
                        "begins to bubble through slits in crust.")
                .setDescription("Bake 40 to 50 minutes or until crust is brown and juice begins " +
                        "to bubble through slits in crust. Serve warm if desired.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        Recipe recipe1 = Recipe.builder()
                .setId(0)
                .setName("Apple Pie")
                .setIngredients(ingredients1)
                .setSteps(steps1)
                .setServings(8)
                .setImageUrl("")
                .build();

        /** create recipe 2*/

        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(Ingredient.create(1, "pkg", "package Betty Crocker™ pie crust mix"));
        ingredients2.add(Ingredient.create(0.3f, "cup", "cold water"));
        ingredients2.add(Ingredient.create(0.5f, "cup", "sugar"));
        ingredients2.add(Ingredient.create(0.5f, "cup", "Gold Medal™ all-purpose flour"));
        ingredients2.add(Ingredient.create(0.5f, "tsp", "ground cinnamon"));
        ingredients2.add(Ingredient.create(0.25f, "tsp", "ground nutmeg"));
        ingredients2.add(Ingredient.create(5, "cup", "thinly sliced peeled apples"));
        ingredients2.add(Ingredient.create(1, "tsp", "butter or margarine"));

        List<Step> steps2 = new ArrayList<>();
        steps2.add(Step.builder()
                .setId(0)
                .setShortDescription("Heat oven to 425°F")
                .setDescription("Heat oven to 425°F. Make pie crust mix as directed for 9-Inch " +
                        "Two-Crust Pie, using 1/3 cup cold water--except trim overhanging edge " +
                        "of bottom pastry 1 inch from rim of plate")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps2.add(Step.builder()
                .setId(1)
                .setShortDescription("Stir together 1/2 cup sugar, the flour, cinnamon " +
                        "and nutmeg in large bowl. ")
                .setDescription("Stir together 1/2 cup sugar, the flour, cinnamon and nutmeg in " +
                        "large bowl. Add apples; toss. Spoon into pastry-lined pie plate. " +
                        "Dot with butter.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps2.add(Step.builder()
                .setId(2)
                .setShortDescription("Roll remaining pastry")
                .setDescription("Roll remaining pastry; cut into 10 strips, each about 1/2 inch " +
                        "wide. Place 5 strips across filling in pie plate. Weave a cross-strip " +
                        "through by first folding back every other strip of the first 5 strips. " +
                        "Continue weaving, folding back alternate strips before adding each " +
                        "cross-strip, until lattice is complete. Trim ends. Fold trimmed edge " +
                        "of bottom crust over ends of strips, building up a high edge. Seal and " +
                        "flute. Brush lightly with milk; sprinkle lightly with additional sugar.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        steps2.add(Step.builder()
                .setId(3)
                .setShortDescription("Bake 30 to 35 minutes or until crust is golden brown and " +
                        "juice is bubbly.")
                .setDescription("Bake 30 to 35 minutes or until crust is golden brown and juice is bubbly.")
                .setThumbnailURL("")
                .setVideoURL("")
                .build());

        Recipe recipe2 = Recipe.builder()
                .setId(1)
                .setName("Fresh Apple Pie")
                .setIngredients(ingredients2)
                .setSteps(steps2)
                .setServings(8)
                .setImageUrl("")
                .build();

        list.add(recipe1);
        list.add(recipe2);

        return list;
    }




}
