package com.example.mustafa.bakingtime.Adabters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mustafa.bakingtime.DataModels.Ingredients;
import com.example.mustafa.bakingtime.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {

    Context context;
    ArrayList<Ingredients>ingredientsArrayList;

    public IngredientsAdapter(Context context, ArrayList<Ingredients> ingredientsArrayList){
        this.context=context;
        this.ingredientsArrayList=ingredientsArrayList;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_item_layout,viewGroup,false);
        IngredientsViewHolder viewHolder=new IngredientsViewHolder(row);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        ingredientsViewHolder.ingredientsText.setText(ingredientsArrayList.get(i).getIngredient());
        ingredientsViewHolder.ingredientsMeasures.setText(ingredientsArrayList.get(i).getMeasure());
        ingredientsViewHolder.ingredientsQuantity.setText(ingredientsArrayList.get(i).getQuantity());

        }

    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_text)
         TextView ingredientsText;
        @BindView(R.id.ingredient_quantity)
        TextView ingredientsQuantity;
        @BindView(R.id.ingredient_measure)
        TextView ingredientsMeasures;

        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
