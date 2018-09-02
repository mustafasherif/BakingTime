package com.example.mustafa.bakingtime.Adabters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mustafa.bakingtime.DataModels.Steps;
import com.example.mustafa.bakingtime.MasterListFragment;
import com.example.mustafa.bakingtime.R;
import com.example.mustafa.bakingtime.StepDetail;
import com.example.mustafa.bakingtime.StepDetailFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder>{

    Context context;
    ArrayList<Steps>stepsArrayList;
    private int selectedPos = 0;

    public StepsAdapter(Context context,ArrayList<Steps>adapterArrayList){
        this.context=context;
        this.stepsArrayList=adapterArrayList;
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.steps_item_layout,viewGroup,false);
        StepsViewHolder viewHolder=new StepsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder stepsViewHolder, int i) {
        stepsViewHolder.shortDescription.setText(stepsArrayList.get(i).getShortDescription());
        stepsViewHolder.stepNumber.setText(stepsArrayList.get(i).getId());
        stepsViewHolder.itemView.setSelected(selectedPos == i);

        stepsViewHolder.itemView.setBackgroundColor
                (selectedPos==i? context.getResources().getColor(R.color.colorAccent):context.getResources().getColor(R.color.White));
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.short_description)
        TextView shortDescription;
        @BindView(R.id.step_number)
        TextView stepNumber;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            MasterListFragment.mCallback.stepClicked(getAdapterPosition());
            notifyItemChanged(selectedPos);
            selectedPos = getLayoutPosition();
            notifyItemChanged(selectedPos);
        }
    }

    public void onChange(int position){
        notifyItemChanged(selectedPos);
        this.selectedPos=position;
        notifyItemChanged(selectedPos);
    }
}
