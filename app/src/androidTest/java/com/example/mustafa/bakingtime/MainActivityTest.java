package com.example.mustafa.bakingtime;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void clickGridViewItem_OpensOrderActivity() {

        onView(withId(R.id.recipes_RecycleView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

    }

    //the test check that main recipes recycler view is clickable and open the recipe detail activity then find
    //the incidents recycler view and check if it is can be scrolled to verify data filling in the recycler
    @Test
    public void ingredientsActivityBasicTest()
    {
        clickGridViewItem_OpensOrderActivity();
        onView(withId(R.id.ingredient_recycle)).perform
                (RecyclerViewActions.actionOnItemAtPosition(4, scrollTo()));
    }


}
