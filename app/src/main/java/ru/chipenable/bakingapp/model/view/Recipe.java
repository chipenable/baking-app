package ru.chipenable.bakingapp.model.view;

import java.util.List;

public class Recipe {

    private final long id;
    private final String name;
    private final String image;
    private final List<Ingredient> ingredients;
    private final List<Step> steps;

    private Recipe(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.image = builder.image;
        this.ingredients = builder.ingredients;
        this.steps = builder.steps;
    }

    public long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients(){
        return ingredients;
    }

    public List<Step> getSteps(){
        return steps;
    }

    public static class Builder{
        private long id;
        private String name;
        private String image;
        private List<Ingredient> ingredients;
        private List<Step> steps;

        public Builder(long id, String name, String image){
            this.id = id;
            this.name = name;
            this.image = image;
        }

        public Builder setIngredients(List<Ingredient> ingredients){
            this.ingredients = ingredients;
            return this;
        }

        public Builder setSteps(List<Step> steps){
            this.steps = steps;
            return this;
        }

        public Recipe build(){
            return new Recipe(this);
        }
    }

}
