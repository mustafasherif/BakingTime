package com.example.mustafa.bakingtime;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mustafa.bakingtime.DataModels.Recipe;

public class StepDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Recipe recipe=getIntent().getParcelableExtra("recipe_list");
        getSupportActionBar().setTitle(recipe.getName());
    }

}
