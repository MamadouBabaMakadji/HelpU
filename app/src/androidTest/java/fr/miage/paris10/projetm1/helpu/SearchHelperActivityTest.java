package fr.miage.paris10.projetm1.helpu;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by david on 3/14/17.
 */

@RunWith(AndroidJUnit4.class)
public class SearchHelperActivityTest {

    @Rule
    public ActivityTestRule<SearchHelperActivity> SearchhelperActivityRule  = new ActivityTestRule<>(SearchHelperActivity.class);


    @Test
    public void testChoixEc()  throws InterruptedException {

        onView(withId(R.id.spinner_SearchHelper_ec)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Statistique"))).perform(click());
        onView(withId(R.id.button_SearchHelper_valider)).perform(click());
    }

}
