package fr.miage.paris10.projetm1.helpu;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by david on 3/14/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testButtonSearchHelper() {
      //  onView(withId(R.id.button_search)).perform(click());
    }

    @Test
    public void testButtonBecomeHelper(){
     //   onView(withId(R.id.button_help)).perform(click());
    }

}