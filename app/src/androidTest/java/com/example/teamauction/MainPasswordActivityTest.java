package com.example.teamauction;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainPasswordActivityTest {
    @Rule
    public ActivityTestRule<MainPasswordActivity> activityRule = new ActivityTestRule<>(MainPasswordActivity.class);

    @Test
    public void TextField() {
        String id = "id";
        Espresso.onView(withId(R.id.find_pw_idbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.find_pw_idbox)).check(matches(withText(id)));
    }

    @Test
    public void GoBack() {
        // 해당 액티비티 요소가 없으므로 정답
        Espresso.onView(withId(R.id.back_password)).perform(click()).check(doesNotExist());
    }
}