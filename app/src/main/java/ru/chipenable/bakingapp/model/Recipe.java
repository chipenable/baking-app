
package ru.chipenable.bakingapp.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Recipe {

    public abstract long id();

    @SerializedName("name")
    public abstract String name();

    @SerializedName("ingredients")
    public abstract List<Ingredient> ingredients();

    @SerializedName("steps")
    public abstract List<Step> steps();

    @SerializedName("servings")
    public abstract int servings();

    @SerializedName("image")
    public abstract String imageUrl();

    public static TypeAdapter<Recipe> typeAdapter(Gson gson){
        return new AutoValue_Recipe.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_Recipe.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(long id);
        public abstract Builder setName(String value);
        public abstract Builder setIngredients(List<Ingredient> list);
        public abstract Builder setSteps(List<Step> list);
        public abstract Builder setServings(int servings);
        public abstract Builder setImageUrl(String imageUrl);
        public abstract Recipe build();
    }

}
