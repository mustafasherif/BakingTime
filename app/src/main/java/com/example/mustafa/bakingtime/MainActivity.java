package com.example.mustafa.bakingtime;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.example.mustafa.bakingtime.Adabters.RecipesAdabter;
import com.example.mustafa.bakingtime.DataModels.Recipe;
import com.example.mustafa.bakingtime.NetworkConnections.NetworkService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipes_RecycleView)
    RecyclerView recipesRecycleView;
    private static RecipesAdabter recipesAdabter;
    private ArrayList<Recipe>recipes;
    private static String offlineRecipesList="recipe_list";
    private static String layoutManager="layout_manager";
    public static final String BUNDLE = "bundle";
    public static final String INGREDIENTS = "ingredients";
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder=ButterKnife.bind(this);
        recipes=new ArrayList();
        recipesAdabter=new RecipesAdabter(recipes,this);
        recipesRecycleView.setLayoutManager(new GridLayoutManager(getApplicationContext(),calculateNoOfColumns(this)));
        recipesRecycleView.setAdapter(recipesAdabter);
        if(savedInstanceState==null){
            NetworkService.getInstance().getJSONApi().getRecipes().enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    recipes=response.body();
                    recipesAdabter.changeSorting(recipes);
                    recipesAdabter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Log.d("connectionFailed",t+"");
                }
            });
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(offlineRecipesList,recipes);
        outState.putParcelable(layoutManager,recipesRecycleView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recipes=savedInstanceState.getParcelableArrayList(offlineRecipesList);
        recipesRecycleView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable(layoutManager));
        recipesAdabter.changeSorting(recipes);
        recipesAdabter.notifyDataSetChanged();
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 270;
        int columnCount = (int) (dpWidth / scalingFactor);
        return (columnCount>=1?columnCount:1); // if column no. is less than 2, we still display 2 columns
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
