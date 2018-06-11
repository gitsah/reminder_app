package com.ad340.group3.reminder_app;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    @Test
    public void runThroughItAll(){
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.date_field)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.message_field)).perform(typeText("This is a reminder")).perform(closeSoftKeyboard());
        onView(withId(R.id.limit_field)).perform(typeText("4")).perform(closeSoftKeyboard());
        onView(withId(R.id.snoozeable)).perform(click());
        onView(withId(R.id.add_reminder)).perform(click());
        onView(withId(R.id.time_field)).perform(typeText("3:00:9")).perform(closeSoftKeyboard());
        onView(withId(R.id.add_reminder)).perform(click());
        onView(withId(R.id.time_field)).perform(replaceText("3:00"));
        onView(withId(R.id.add_reminder)).perform(click());
        Espresso.pressBack();
        onView(withId(R.id.delete_button)).perform(click());
    }

    @Test
    public void makeMultipleReminders(){
        onView(withId(R.id.add_button)).perform(click());
        onView(withId(R.id.time_field)).perform(typeText("3:00")).perform(closeSoftKeyboard());
        onView(withId(R.id.date_field)).perform(click());
        onView(withText("OK")).perform(click());
        onView(withId(R.id.message_field)).perform(typeText("This is a reminder")).perform(closeSoftKeyboard());
        onView(withId(R.id.limit_field)).perform(typeText("4")).perform(closeSoftKeyboard());
        onView(withId(R.id.snoozeable)).perform(click());
        onView(withId(R.id.add_reminder)).perform(click());
        onView(withId(R.id.time_field)).perform(replaceText("1:06"));
        onView(withId(R.id.add_reminder)).perform(click());
        onView(withId(R.id.time_field)).perform(replaceText("8:06"));
        onView(withId(R.id.add_reminder)).perform(click());
        Espresso.pressBack();
    }
}