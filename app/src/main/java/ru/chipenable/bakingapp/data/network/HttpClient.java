package ru.chipenable.bakingapp.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.chipenable.bakingapp.model.network.Recipe;

/**
 * Created by Pavel.B on 25.05.2017.
 */

public interface HttpClient {

    String ENDPOINT = "http://go.udacity.com/";

    @GET("android-baking-app-json")
    Observable<List<Recipe>> getRecipes();

}
