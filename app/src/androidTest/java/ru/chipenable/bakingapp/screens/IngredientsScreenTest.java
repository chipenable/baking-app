package ru.chipenable.bakingapp.screens;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import ru.chipenable.bakingapp.R;
import ru.chipenable.bakingapp.common.MockApplication;
import ru.chipenable.bakingapp.common.TestRecipes;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.model.ArgumentKeys;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.ui.activity.IngredientAndStepsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ru.chipenable.bakingapp.common.TestUtils.withRecyclerView;


/**
 * Created by Pavel.B on 22.07.2017.
 */

public class IngredientsScreenTest {

    private List<Recipe> testRecipeList;
    private int recipePos;

    @Rule
    public ActivityTestRule<IngredientAndStepsActivity> activityRule =
            new ActivityTestRule<>(IngredientAndStepsActivity.class, true, false);

    @Before
    public void setup(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockApplication mockApplication =
                (MockApplication)instrumentation.getTargetContext().getApplicationContext();

        //prepare repo and router for ingredients screen
        recipePos = 0;
        testRecipeList = new TestRecipes().getRecipes();

        final IRepo repo = mockApplication.getAppComponent().Repo();
        final Router router = mockApplication.getAppComponent().Router();

        repo.putRecipes(testRecipeList)
                .concatMap(aLong -> repo.getRecipeNames())
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(recipes -> {
                    Bundle args = new Bundle();
                    args.putLong(ArgumentKeys.ID, recipes.get(recipePos).id());
                    args.putInt(ArgumentKeys.STEP, 0);
                    router.putCommand(Command.SHOW_INGREDIENTS,
                            IngredientAndStepsPresenter.class.getName(), args);
                });
    }

    @Test
    public void checkThatIngredientsAreShown(){
        activityRule.launchActivity(new Intent());

        List<Ingredient> ingredientList = testRecipeList.get(recipePos).ingredients();

        for(int i = 0; i < ingredientList.size(); i++) {
            Ingredient ingredient = ingredientList.get(i);
            onView(withRecyclerView(R.id.ingredient_list_view).atPositionOnView(i, R.id.ingredient))
                    .check(matches(withText(ingredient.ingredient())));

            String quantity = String.valueOf(ingredient.quantity());
            onView(withRecyclerView(R.id.ingredient_list_view).atPositionOnView(i, R.id.quantity))
                    .check(matches(withText(quantity)));

            String measure = String.valueOf(ingredient.measure());
            onView(withRecyclerView(R.id.ingredient_list_view).atPositionOnView(i, R.id.measure))
                    .check(matches(withText(measure)));
        }
    }



}
