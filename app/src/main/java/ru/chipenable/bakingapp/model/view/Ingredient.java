package ru.chipenable.bakingapp.model.view;

public class Ingredient {

    private final int quantity;
    private final String measure;
    private final String ingredient;

    public Ingredient(int quantity, String measure, String ingredient){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }



}
