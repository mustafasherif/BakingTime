package com.example.mustafa.bakingtime;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mustafa.bakingtime.DataModels.Recipe;
import com.example.mustafa.bakingtime.Widget.RecipeWidgetProvider;

public class RecipeDetail extends AppCompatActivity implements MasterListFragment.OnStepClickListener {

    private boolean twoPane;
    Recipe recipe;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        recipe=getIntent().getParcelableExtra("recipe_list");
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetIds,recipe.getIngredients());

        getSupportActionBar().setTitle(recipe.getName());



        fragmentManager=getSupportFragmentManager();

        if(findViewById(R.id.step_detail_fragment_tablet)!=null){
            twoPane=true;
        }else {
            twoPane=false;
        }



    }





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void stepClicked(int position) {
        if(twoPane){
            StepDetailFragment fragment = (StepDetailFragment) fragmentManager.findFragmentById(R.id.step_detail_fragment_tablet);
            fragment.turToNewState(position);
        }else {
            Intent intent=new Intent(this,StepDetail.class);
            intent.putExtra("recipe_list",recipe);
            intent.putExtra("step_number",position);
            startActivity(intent);
        }
    }
}
