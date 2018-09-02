package com.example.mustafa.bakingtime;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mustafa.bakingtime.Adabters.IngredientsAdapter;
import com.example.mustafa.bakingtime.Adabters.StepsAdapter;
import com.example.mustafa.bakingtime.DataModels.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterListFragment extends Fragment {


    @BindView(R.id.ingredient_recycle) RecyclerView ingredientRecycle;
    IngredientsAdapter ingredientsAdapter;

    @BindView(R.id.steps_recycle)RecyclerView stepsRecycle;
    public static StepsAdapter stepsAdapter;

    public static OnStepClickListener mCallback;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView= inflater.inflate(R.layout.master_list_layout,container,false);

        ButterKnife.bind(this,rootView);
        Recipe recipe=getActivity().getIntent().getParcelableExtra("recipe_list");

        ingredientRecycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayout.HORIZONTAL,false));
        ingredientsAdapter = new IngredientsAdapter(getActivity(),recipe.getIngredients());
        ingredientRecycle.setAdapter(ingredientsAdapter);

        stepsRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsAdapter=new StepsAdapter(getActivity(),recipe.getSteps());
        stepsRecycle.setAdapter(stepsAdapter);

        return rootView;
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnStepClickListener");
        }
    }

        public interface OnStepClickListener {
            void stepClicked(int position);
        }

}
