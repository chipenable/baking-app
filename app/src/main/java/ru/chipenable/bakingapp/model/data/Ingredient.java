
package ru.chipenable.bakingapp.model.data;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Ingredient implements Parcelable {

    @SerializedName("quantity")
    public abstract float quantity();

    @SerializedName("measure")
    public abstract String measure();

    @SerializedName("ingredient")
    public abstract String ingredient();

    public static TypeAdapter<Ingredient> typeAdapter(Gson gson) {
        return new AutoValue_Ingredient.GsonTypeAdapter(gson);
    }

    public static Ingredient create(float quantity, String measure, String ingredient){
        return new AutoValue_Ingredient(quantity, measure, ingredient);
    }

}
