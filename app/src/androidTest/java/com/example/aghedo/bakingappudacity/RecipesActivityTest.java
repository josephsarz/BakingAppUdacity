package com.example.aghedo.bakingappudacity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.aghedo.bakingappudacity.ui.MainActivity;
import com.example.aghedo.bakingappudacity.ui.RecipeDetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

    //@Rule public ActivityTestRule<RecipesActivity> activityTestRule = new ActivityTestRule<>(RecipesActivity.class);
    @Rule
    public IntentsTestRule<MainActivity> activityTestRule = new IntentsTestRule<>(MainActivity.class);
    IdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void openRecipeDetailActivity() {
        //onView(withId(R.id.rv_recipes)).perform(click());

        //perform click on the recycler view
        onView(withId(R.id.rv_recipes))
                .perform(actionOnItemAtPosition(0, click()));

        // check if the intent is starts the class RecipeDetailView
        intended(hasComponent(RecipeDetails.class.getName()));

    }

    @After
    public void unregisterIdlingResource() {

        if (idlingResource != null)
            Espresso.unregisterIdlingResources(idlingResource);

    }

}
