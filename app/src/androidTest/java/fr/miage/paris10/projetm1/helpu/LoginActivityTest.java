package fr.miage.paris10.projetm1.helpu;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by dameimou on 07/03/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule  = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void testGoodLogIn()  throws InterruptedException {
        onView(withId(R.id.input_email))
                .perform(typeText("33012900@u-paris10.fr"), closeSoftKeyboard());

        onView(withId(R.id.input_password)).perform(typeText("azerty"), closeSoftKeyboard());
        Thread.sleep(500);
        onView(withId(R.id.btn_login)).perform(click());
    }
   @Test
    public void testFalseLogIn() throws InterruptedException {
       LoginActivity activity = loginActivityRule.getActivity();
        onView(withId(R.id.input_email))
                .perform(typeText("a@u-paris10.fr"), closeSoftKeyboard());
        Thread.sleep(1000);
        onView(withId(R.id.input_password)).perform(typeText("azerty"), closeSoftKeyboard());
     //   Thread.sleep(500);
    //    onView(withId(R.id.btn_login)).perform(click());
      //         onView(withText("Login failed")).
       //                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
       //                check(matches(isDisplayed()));
    }
}
