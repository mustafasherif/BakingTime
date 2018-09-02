package com.example.mustafa.bakingtime.Adabters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mustafa.bakingtime.DataModels.Recipe;
import com.example.mustafa.bakingtime.R;
import com.example.mustafa.bakingtime.RecipeDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesAdabter extends RecyclerView.Adapter<RecipesAdabter.RecipesViewHolder> {

    ArrayList<Recipe>recipes;
    Context context;

    public RecipesAdabter(ArrayList<Recipe>recipes, Context context){
        this.recipes=recipes;
        this.context=context;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recipe_item,viewGroup,false);
        RecipesViewHolder viewHolder=new RecipesViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder recipesViewHolder, int i) {
        Recipe recipe=recipes.get(i);
        if(!recipe.getImage().matches("")){
            Picasso.get().load(recipe.getImage()).into(recipesViewHolder.recipe_image);
        }else if (getDrawable(recipe.getName())!=0){
            try {
                recipesViewHolder.recipe_image.setImageResource(getDrawable(recipe.getName()));

            }catch (Exception exception){
                recipesViewHolder.recipe_image.setImageResource(R.drawable.nutellapie);
            }
        }

        else {
            TypedArray ta = context.getResources().obtainTypedArray(R.array.colors);
            recipesViewHolder.recipe_image.setImageResource(ta.getResourceId(i,R.color.colorPrimary));
        }
        recipesViewHolder.recipe_name.setText(recipe.getName());

    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name)TextView recipe_name;
        @BindView(R.id.recipe_image)ImageView recipe_image;
        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent detailIntent=new Intent(context, RecipeDetail.class);
            detailIntent.putExtra("recipe_list",recipes.get(getAdapterPosition()));
            context.startActivity(detailIntent);
        }
    }
    public void changeSorting(ArrayList<Recipe> recipes) {
        this.recipes = recipes;

    }

    private  int getDrawable(String name) {
        Resources resources = context.getResources();
        String imageName=name.replaceAll("\\s+","").toLowerCase();
        final int resourceId = resources.getIdentifier(imageName, "drawable",
                context.getPackageName());

        return resourceId;
    }
    void startWidgetService()
    {

    }
}
