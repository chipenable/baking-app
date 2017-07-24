package ru.chipenable.bakingapp.repo;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.observers.TestObserver;
import ru.chipenable.bakingapp.data.repo.Converter;
import ru.chipenable.bakingapp.data.repo.IRepo;
import ru.chipenable.bakingapp.data.repo.Repo;
import ru.chipenable.bakingapp.data.repo.RepoHelper;
import ru.chipenable.bakingapp.model.data.Recipe;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * Created by Pavel.B on 12.06.2017.
 */
@RunWith(AndroidJUnit4.class)
public class RepoTest {

    private RepoHelper repoHelper;
    private Converter converter;
    private IRepo repo;

    @Before
    public void setUp() throws Exception {
        getTargetContext().deleteDatabase(RepoHelper.DATABASE_NAME);
        repoHelper = new RepoHelper(getTargetContext());
        converter = new Converter();
        repo = new Repo(repoHelper, converter);
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
    public void putRecipesAndGetRecipeNames(){
        RepoUtil.deleteRecipes(repoHelper);

        List<Recipe> recipeList = RepoUtil.creteTestRecipeList();
        TestObserver<Long> observerToPutRecipes = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(observerToPutRecipes);
        observerToPutRecipes.assertNoErrors();
        observerToPutRecipes.assertValueCount(recipeList.size());

        TestObserver<List<Recipe>> observerToGetRecipeNames = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(observerToGetRecipeNames);
        observerToGetRecipeNames.assertNoErrors();
        observerToGetRecipeNames.assertValueCount(1);
        List<Recipe> actualRecipeList = observerToGetRecipeNames.values().get(0);
        for(int i = 0; i < recipeList.size(); i++){
            Recipe expectedRecipe = recipeList.get(i);
            Recipe actualRecipe = actualRecipeList.get(i);
            Assert.assertEquals(expectedRecipe.name(), actualRecipe.name());
        }
    }

    @Test
    public void putRecipesAndGetSteps() {
        //delete recipes
        RepoUtil.deleteRecipes(repoHelper);

        //create test recipes and put them to repo
        List<Recipe> recipeList = RepoUtil.creteTestRecipeList();
        TestObserver<Long> observerToPutRecipes = TestObserver.create();
        repo.putRecipes(recipeList)
                .subscribe(observerToPutRecipes);
        observerToPutRecipes.assertNoErrors();
        observerToPutRecipes.assertValueCount(recipeList.size());

        //get recipe names to retrieve database id
        TestObserver<List<Recipe>> observerToGetRecipeNames = TestObserver.create();
        repo.getRecipeNames()
                .subscribe(observerToGetRecipeNames);
        observerToGetRecipeNames.assertNoErrors();
        observerToGetRecipeNames.assertValueCount(1);
        List<Recipe> actualRecipeList = observerToGetRecipeNames.values().get(0);

        //get recipe from repo
        TestObserver<Recipe> observerToGetRecipe = TestObserver.create();
        repo.getRecipe(actualRecipeList.get(0).id())
                .subscribe(observerToGetRecipe);
        observerToGetRecipe.assertNoErrors();
        observerToGetRecipe.assertValueCount(1);

        //check that it is equals to expected recipe
        Recipe expectedRecipe = recipeList.get(0);
        Recipe actualRecipe = observerToGetRecipe.values().get(0);
        Assert.assertEquals(expectedRecipe.name(), actualRecipe.name());
        Assert.assertEquals(expectedRecipe.imageUrl(), actualRecipe.imageUrl());
        Assert.assertEquals(expectedRecipe.servings(), actualRecipe.servings());


    }

}
