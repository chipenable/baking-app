package ru.chipenable.bakingapp.recipelist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.common.TestRecipes;
import ru.chipenable.bakingapp.common.TestUtils;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.ui.activity.RecipeListActivity;
import ru.chipenable.bakingapp.ui.fragment.RecipeListFragment;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkNotNull;
import static ru.chipenable.bakingapp.common.TestUtils.withRecyclerView;

/**
 * Created by Pavel.B on 22.07.2017.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeScreenTest {

    private List<Recipe> testRecipeList;

    @Rule
    public ActivityTestRule<RecipeListActivity> activityRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    @Before
    public void setup(){
        testRecipeList = new TestRecipes().getRecipes();
    }

    @Test
    public void checkRecipeListFragmentNotNull(){
        FragmentManager fm = activityRule.getActivity().getSupportFragmentManager();
        RecipeListFragment f = (RecipeListFragment)fm.findFragmentById(R.id.recipe_list_fragment);
        Assert.assertNotNull(f);
    }

    @Test
    public void checkRecipeListContent(){
        for(int i = 0; i < testRecipeList.size(); i++) {
            Recipe recipe = testRecipeList.get(i);
            onView(withRecyclerView(R.id.recipe_list).atPositionOnView(i, R.id.recipe_name))
                    .check(matches(withText(recipe.name())));

            String servings = String.valueOf(recipe.servings());
            onView(withRecyclerView(R.id.recipe_list).atPositionOnView(i, R.id.servings_value))
                    .check(matches(withText(servings)));
        }
    }

    @Test
    public void testRecipeListItems(){


    }

}
