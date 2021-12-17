package com.example.teamauction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainStartActivityTest {
    @Rule
    public ActivityTestRule<MainStartActivity> activityRule = new ActivityTestRule<>(MainStartActivity.class);

    @Test
    public void GoLoginClick() {
        // 다음 페이지를 넘어가면 해당 아이디가 없을 것이므로 정답임.
        onView(withId(R.id.go_login)).perform(click()).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void GoSignupClick() {
        // 다른 페이지를 넘어가면 해당 레이아웃이 없을 것이므로 정답임.
        onView(withId(R.id.go_signup)).perform(click()).check(ViewAssertions.doesNotExist());
    }

}