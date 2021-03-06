package com.nexte.nexte.MatchScene;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.nexte.nexte.MainActivity;
import com.nexte.nexte.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestFiveSetsSendSuccess {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testFiveSetsSendSuccess() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(ViewMatchers.withId(R.id.navigationLogin), withText("Navegar pelo NEXTE!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.challengeNavigation),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.userPicture), withContentDescription("User picture"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.challengeRecyclerView),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.sendChallengeButton), withText("Enviar Desafio"),
                        childAtPosition(
                                withParent(withId(R.id.challengerviewpager)),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction radioRealButton = onView(
                allOf(withId(R.id.buttonFive), withText("5"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonGroup),
                                        2),
                                2),
                        isDisplayed()));
        radioRealButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.challengerResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        1),
                                5)));
        appCompatEditText.perform(scrollTo(), replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.challengedResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        1),
                                4)));
        appCompatEditText2.perform(scrollTo(), replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.challengerResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        2),
                                5)));
        appCompatEditText3.perform(scrollTo(), replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.challengedResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        2),
                                4)));
        appCompatEditText4.perform(scrollTo(), replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.challengerResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        3),
                                5)));
        appCompatEditText5.perform(scrollTo(), replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.challengedResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        3),
                                4)));
        appCompatEditText6.perform(scrollTo(), replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.challengerResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        4),
                                5)));
        appCompatEditText7.perform(scrollTo(), replaceText("6"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.challengedResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        4),
                                4)));
        appCompatEditText8.perform(scrollTo(), replaceText("7"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.challengerResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        5),
                                5)));
        appCompatEditText9.perform(scrollTo(), replaceText("10"), closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.challengedResult),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.matchRecyclerView),
                                        5),
                                4)));
        appCompatEditText10.perform(scrollTo(), replaceText("8"), closeSoftKeyboard());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
