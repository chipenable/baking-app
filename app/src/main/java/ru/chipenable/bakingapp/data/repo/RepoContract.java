package ru.chipenable.bakingapp.data.repo;

import android.provider.BaseColumns;

/**
 * Created by Pavel.B on 20.05.2017.
 */

public final class RepoContract {

    private RepoContract(){}

    public static abstract class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipe_table";

        public static final String COL_NAME = "name";
        public static final String COL_IMAGE_URL = "image_url";
        public static final String COL_SERVINGS = "servings";
    }

    public static abstract class IngredientEntry implements BaseColumns{
        public static final String TABLE_NAME = "ingredient_table";

        public static final String COL_RECIPE_ID = "recipe_id";
        public static final String COL_INGREDIENT = "ingredient";
        public static final String COL_QUANTITY = "quantity";
        public static final String COL_MEASURE = "measure";
    }

    public static abstract class StepEntry implements BaseColumns{
        public static final String TABLE_NAME = "step_table";

        public static final String COL_RECIPE_ID = "recipe_id";
        public static final String COL_SHORT_DESCRIPTION = "shortDescription";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_VIDEO_URL = "videoUrl";
        public static final String COL_THUMBNAIL_URL = "thumbnailUrl";
    }

}
