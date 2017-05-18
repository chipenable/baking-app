package ru.chipenable.bakingapp.model.view;

public class Step {

    private final String shortDescription;
    private final String description;
    private final String videoUrl;
    private final String thumbnailUrl;

    public Step(String shortDescription, String description, String videoUrl, String thumbnailUrl){
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
