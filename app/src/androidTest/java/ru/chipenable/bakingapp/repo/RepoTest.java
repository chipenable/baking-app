package ru.chipenable.bakingapp.repo;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;
import ru.chipenable.bakingapp.common.TestRecipes;
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.model.data.Ingredient;
import ru.chipenable.bakingapp.model.data.Recipe;
import ru.chipenable.bakingapp.model.data.Step;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Pavel.B on 12.06.2017.
 */
@RunWith(AndroidJUnit4.class)
public class RepoTest {

    private List<Recipe> recipeList;
    private RepoHelper repoHelper;
    private Converter converter;
    private IRepo repo;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(RepoHelper.DATABASE_NAME);
        repoHelper = new RepoHelper(getTargetContext());
        converter = new Converter();
        repo = new Repo(repoHelper, converter);
        recipeList = new TestRecipes().getRecipes();
    }

    @After
    public void tearDown() throws Exception {
        converter = null;
        repo = null;
        repoHelper.close();
    }

    @Test
    public void getRecipesFromEmptyRepo(){
        TestObserver<List<Recipe>> testObserver = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(testObserver);
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        List<Recipe> list = testObserver.values().get(0);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.isEmpty());
    }

    @Test
    public void getRecipeNames(){
        TestObserver<Long> recipesObserver = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(recipesObserver);
        recipesObserver.assertNoErrors();
        recipesObserver.assertValueCount(1);

        TestObserver<List<Recipe>> recipeNamesObserver = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(recipeNamesObserver);
        recipeNamesObserver.assertNoErrors();
        recipeNamesObserver.assertValueCount(1);
        List<Recipe> actualRecipeList = recipeNamesObserver.values().get(0);
        for(int i = 0; i < recipeList.size(); i++){
            Recipe expectedRecipe = recipeList.get(i);
            Recipe actualRecipe = actualRecipeList.get(i);
            Assert.assertEquals(expectedRecipe.name(), actualRecipe.name());
        }
    }

    @Test
    public void getRecipeById(){
        //put test recipes to repo
        TestObserver<Long> recipesObserver = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(recipesObserver);
        recipesObserver.assertNoErrors();
        recipesObserver.assertValueCount(1);

        //get recipe names to retrieve recipe id
        TestObserver<List<Recipe>> recipeNamesObserver = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(recipeNamesObserver);
        recipeNamesObserver.assertNoErrors();
        recipeNamesObserver.assertValueCount(1);
        List<Recipe> actualRecipeList = recipeNamesObserver.values().get(0);

        //get recipe from repo
        TestObserver<Recipe> recipeObserver = TestObserver.create();
        repo.getRecipe(actualRecipeList.get(0).id())
                .subscribe(recipeObserver);
        recipeObserver.assertNoErrors();
        recipeObserver.assertValueCount(1);

        //check that it is equals to expected recipe
        Recipe expectedRecipe = recipeList.get(0);
        Recipe actualRecipe = recipeObserver.values().get(0);
        Assert.assertEquals(expectedRecipe, actualRecipe);
    }

    @Test
    public void getSteps() {
        //put test recipes to repo
        TestObserver<Long> recipesObserver = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(recipesObserver);
        recipesObserver.assertNoErrors();
        recipesObserver.assertValueCount(1);

        //get recipe names to retrieve database id
        TestObserver<List<Recipe>> recipeNamesObserver = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(recipeNamesObserver);
        recipeNamesObserver.assertNoErrors();
        recipeNamesObserver.assertValueCount(1);
        List<Recipe> actualRecipeList = recipeNamesObserver.values().get(0);

        //get recipe id
        long recipeId = actualRecipeList.get(0).id();
        List<Step> expectedSteps = recipeList.get(0).steps();

        //check steps
        for(Step expectedStep: expectedSteps){
            TestObserver<Step> stepObserver = TestObserver.create();
            repo.getStep(recipeId, expectedStep.id())
                    .subscribe(stepObserver);

            stepObserver.assertNoErrors();
            stepObserver.assertValueCount(1);
            Step actualStep = stepObserver.values().get(0);
            Assert.assertEquals(expectedStep, actualStep);
        }
    }

    @Test
    public void getIngredients(){
        //put test recipes to repo
        TestObserver<Long> recipeListObserver = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(recipeListObserver);
        recipeListObserver.assertNoErrors();
        recipeListObserver.assertValueCount(1);

        //get recipe names to retrieve database id
        TestObserver<List<Recipe>> recipeNamesObserver = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(recipeNamesObserver);
        recipeNamesObserver.assertNoErrors();
        recipeNamesObserver.assertValueCount(1);
        List<Recipe> actualRecipeList = recipeNamesObserver.values().get(0);

        //get ingredients from repo
        TestObserver<List<Ingredient>> ingredientsObserver = TestObserver.create();
        repo.getIngredients(actualRecipeList.get(0).id())
                .subscribe(ingredientsObserver);
        ingredientsObserver.assertNoErrors();
        ingredientsObserver.assertValueCount(1);

        //check ingredients
        List<Ingredient> expectedIngredients = recipeList.get(0).ingredients();
        List<Ingredient> actualIngredients = ingredientsObserver.values().get(0);
        Assert.assertEquals(expectedIngredients, actualIngredients);
    }


}
