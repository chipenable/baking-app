
package ru.chipenable.bakingapp.model.data;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Step {

    @SerializedName("id")
    public abstract int id();

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

    public static Builder builder() {
        return new AutoValue_Step.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(int id);
        public abstract Builder setShortDescription(String description);
        public abstract Builder setDescription(String description);
        public abstract Builder setVideoURL(String videoUrl);
        public abstract Builder setThumbnailURL(String thumbnailUrl);
        public abstract Step build();
    }

}
