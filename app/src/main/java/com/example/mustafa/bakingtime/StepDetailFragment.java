package com.example.mustafa.bakingtime;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.bakingtime.Adabters.StepsAdapter;
import com.example.mustafa.bakingtime.DataModels.Recipe;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {


    private static final String POSITION_KEY = "pos_k";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready_k";

    @BindView(R.id.video)public SimpleExoPlayerView playerView;
    @BindView(R.id.fixed_image)ImageView imageView;
    @BindView(R.id.description)TextView description;
    @BindView(R.id.step_number)TextView stepNumber;
    @BindView(R.id.back)Button backButton;
    @BindView(R.id.forward)Button forwardButton;

    SimpleExoPlayer exoPlayer;
    Recipe recipe;

    int position;
    private long mCurrentPosition = C.TIME_UNSET;
    private boolean mPlayWhenReady = true;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.step_detail_layout,container,false);
        ButterKnife.bind(this,rootView);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getLong(POSITION_KEY,C.TIME_UNSET);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
        }
        recipe=getActivity().getIntent().getParcelableExtra("recipe_list");
        position=getActivity().getIntent().getIntExtra("step_number",0);
        description.setText(recipe.getSteps().get(position).getDescription());
        stepNumber.setText(recipe.getSteps().get(position).getId());
        checkButtonState();
        checkImageState();
        handleNewState();
        return rootView;
    }

    public void turToNewState(int position){
        releasePlayer();
        description.setText(recipe.getSteps().get(position).getDescription());
        stepNumber.setText(recipe.getSteps().get(position).getId());
        this.position=position;
        MasterListFragment.stepsAdapter.onChange(position);
        getActivity().getIntent().putExtra("step_number",position);
        checkButtonState();
        checkImageState();
    }

    public void handleNewState(){


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turToNewState(position-1);
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turToNewState(position+1);
            }
        });


    }

    public void checkButtonState() {
        if (position<recipe.getSteps().size()-1){
            if(recipe.getSteps().get(position).getId().matches("0")){
                backButton.setVisibility(View.INVISIBLE);
                forwardButton.setVisibility(View.VISIBLE);
            }else {
                backButton.setVisibility(View.VISIBLE);
            }
            forwardButton.setVisibility(View.VISIBLE);

        }else {
            forwardButton.setVisibility(View.INVISIBLE);
            backButton.setVisibility(View.VISIBLE);
        }
    }

    private void initializePlayer(Uri parse ) {
        if(exoPlayer==null){

            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getActivity(), "ClassicalMusicQuiz");
            MediaSource mediaSource = new ExtractorMediaSource(parse, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);

            if (mCurrentPosition != C.TIME_UNSET)
                exoPlayer.seekTo(mCurrentPosition);

            exoPlayer.setPlayWhenReady(mPlayWhenReady);


        }
    }



    private void checkImageState() {
        if(recipe.getSteps().get(position).getVideoURL().matches("")){
            if(recipe.getSteps().get(position).getThumbnailURL().matches("")){
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.ic_oven);
                playerView.setVisibility(View.INVISIBLE);
            }else {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.ic_oven);
                playerView.setVisibility(View.INVISIBLE);

                Picasso.get().load(recipe.getSteps().get(position).getThumbnailURL()).into(imageView);

            }
        }else {
            imageView.setVisibility(View.INVISIBLE);
            playerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(recipe.getSteps().get(position).getVideoURL()));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if(exoPlayer!=null){
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            mPlayWhenReady = exoPlayer.getPlayWhenReady();
            mCurrentPosition = exoPlayer.getCurrentPosition();

            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(POSITION_KEY, mCurrentPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Toast.makeText(getActivity(),"out",Toast.LENGTH_SHORT).show();
        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if(((StepDetail)getActivity()).getSupportActionBar()!=null) {
                ((StepDetail)getActivity()).getSupportActionBar().hide();
            }
            Toast.makeText(getActivity(),"in",Toast.LENGTH_SHORT).show();
            backButton.setVisibility(View.GONE);
            forwardButton.setVisibility(View.GONE);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=params.MATCH_PARENT;
            playerView.setLayoutParams(params);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(((StepDetail)getActivity()).getSupportActionBar()!=null) {
                ((StepDetail)getActivity()).getSupportActionBar().show();
            }
            checkButtonState();
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
            params.width=params.MATCH_PARENT;
            params.height=200;
            playerView.setLayoutParams(params);
        }
    }
}
