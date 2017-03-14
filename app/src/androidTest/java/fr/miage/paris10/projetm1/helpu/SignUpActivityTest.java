package fr.miage.paris10.projetm1.helpu;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
/**
 * Created by david on 07/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {
    @Rule
    public ActivityTestRule<SignupActivity> signUpActivityRule  = new ActivityTestRule<>(SignupActivity.class);


   @Test
    public void testGoodLogIn() throws InterruptedException {
        onView(withId(R.id.input_firstName)).perform(typeText("test"), closeSoftKeyboard());
       Thread.sleep(500);
        onView(withId(R.id.input_lastName)).perform(typeText("test"), closeSoftKeyboard());
       Thread.sleep(500);
        onView(withId(R.id.input_email)).perform(typeText("test@u-paris10.fr"), closeSoftKeyboard());
       Thread.sleep(500);

       onView(withId(R.id.spinner_ufr)).perform(click());
       onData(allOf(is(instanceOf(String.class)), is("SCIENCES TECHNOLOGIES ET SANTE"))).perform(click());
       onView(withId(R.id.spinner_filliere)).perform(click());
       onData(allOf(is(instanceOf(String.class)), is("STAPS"))).perform(click());
       onView(withId(R.id.spinner_level)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("M1"))).perform(click());


        onView(withId(R.id.input_password)).perform(typeText("azerty"), closeSoftKeyboard());
       Thread.sleep(500);
        onView(withId(R.id.input_reEnterPassword)).perform(typeText("azerty"), closeSoftKeyboard());
       Thread.sleep(500);

      //  onView(withId(R.id.link_login)).perform(click());
    }
}
