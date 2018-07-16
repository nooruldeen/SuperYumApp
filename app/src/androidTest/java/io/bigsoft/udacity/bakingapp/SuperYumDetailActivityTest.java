package io.bigsoft.udacity.bakingapp;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.bigsoft.udacity.superyum.R;
import io.bigsoft.udacity.superyum.activities.MainActivity;


@RunWith(AndroidJUnit4.class)
public class SuperYumDetailActivityTest {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "Cheesecake";
    private static final String RECIPES = "Super Yum Recipes";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recipeListTitle_Check() {
        onView(withId(R.id.tv_main_list_title)).check(matches(withText(RECIPES)));
    }

    @Test
    public void clickRecyclerViewItem_NutellaPie_OpensRecipeDetailActivity() {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView(withText(NUTELLA_PIE)).perform(click());
        onView(withId(R.id.title_text_view)).check(matches(withText(NUTELLA_PIE)));
    }

    @Test
    public void clickRecyclerViewItem_Brownies_OpensRecipeDetailActivity() {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, scrollTo()));
        onView(withText(BROWNIES)).perform(click());
        onView(withId(R.id.title_text_view)).check(matches(withText(BROWNIES)));
    }

    @Test
    public void clickRecyclerViewItem_Yellow_Cake_OpensRecipeDetailActivity() {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(2, scrollTo()));
        onView(withText(YELLOW_CAKE)).perform(click());
        onView(withId(R.id.title_text_view)).check(matches(withText(YELLOW_CAKE)));
    }

    @Test
    public void clickRecyclerViewItem_Cheesecake_OpensRecipeDetailActivity() {
        onView(ViewMatchers.withId(R.id.recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(3, scrollTo()));
        onView(withText(CHEESECAKE)).perform(click());
        onView(withId(R.id.title_text_view)).check(matches(withText(CHEESECAKE)));
    }
}
