
package ru.chipenable.bakingapp.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Step {

    @SerializedName("id")
    private int id;

    @SerializedName("shortDescription")
    public abstract String shortDescription();

    @SerializedName("description")
    public abstract String description();

    @SerializedName("videoURL")
    public abstract String videoURL();

    @SerializedName("thumbnailURL")
    public abstract String thumbnailURL();

    public static TypeAdapter<Step> typeAdapter(Gson gson) {
        return new AutoValue_Step.GsonTypeAdapter(gson);
    }

}
