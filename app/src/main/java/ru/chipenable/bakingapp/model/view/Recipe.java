package ru.chipenable.bakingapp.model.view;

public class Recipe {

    private final long id;
    private final String name;
    private final String image;

    public Recipe(long id, String name, String image){
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

}
