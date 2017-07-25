package ru.chipenable.bakingapp.screens;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.After;
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
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;
import ru.chipenable.bakingapp.model.navigation.Command;
import ru.chipenable.bakingapp.model.navigation.Router;
import ru.chipenable.bakingapp.presentation.presenter.IngredientAndStepsPresenter;
import ru.chipenable.bakingapp.presentation.presenter.RecipeDetailsPresenter;
import ru.chipenable.bakingapp.ui.activity.RecipeDetailsActivity;
import ru.chipenable.bakingapp.ui.activity.RecipeListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ru.chipenable.bakingapp.common.TestUtils.withRecyclerView;

/**
 * Created by Pavel.B on 24.07.2017.
 */

public class RecipeDetailsScreenTest {

    private List<Step> testSteps;

    @Rule
    public IntentsTestRule<RecipeDetailsActivity> recipeDetailsRule =
            new IntentsTestRule<>(RecipeDetailsActivity.class, true, false);

    @Before
    public void setup(){
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        MockApplication mockApplication =
                (MockApplication)instrumentation.getTargetContext().getApplicationContext();

        //prepare repo and router for RecipeDetails screen
        int recipePos = 0;
        List<Recipe> testRecipeList = new TestRecipes().getRecipes();
        testSteps = testRecipeList.get(recipePos).steps();

        IRepo repo = mockApplication.getAppComponent().Repo();
        Router router = mockApplication.getAppComponent().Router();

        repo.putRecipes(testRecipeList)
                .concatMap(aLong -> repo.getRecipeNames())
                .observeOn(Schedulers.trampoline())
                .subscribeOn(Schedulers.trampoline())
                .subscribe(recipes -> {
                    Bundle args = new Bundle();
                    args.putLong(ArgumentKeys.ID, recipes.get(recipePos).id());
                    router.putCommand(Command.SHOW_DETAILS,
                            RecipeDetailsPresenter.class.getName(), args);
                });
    }

    @After
    public void tearDown(){
        recipeDetailsRule.getActivity().finish();
    }

    @Test
    public void checkThatRecipeDetailsAreShown(){
        recipeDetailsRule.launchActivity(new Intent());

        onView(withRecyclerView(R.id.step_list).atPositionOnView(0, R.id.step_name))
                .check(matches(withText("Ingredients")));

        for(int i = 0; i < testSteps.size(); i++) {
            Step step = testSteps.get(i);
            onView(withRecyclerView(R.id.step_list).atPositionOnView(i + 1, R.id.step_name))
                    .check(matches(withText(step.shortDescription())));
        }
    }

}
