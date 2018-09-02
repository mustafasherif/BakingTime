package com.example.mustafa.bakingtime.NetworkConnections;

import com.example.mustafa.bakingtime.DataModels.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {

    @GET("baking.json")
    public Call<ArrayList<Recipe>>getRecipes();

}
