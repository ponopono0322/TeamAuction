package com.example.teamauction;

import static org.junit.Assert.*;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainLoginActivityTest {

    @Rule
    public ActivityTestRule<MainLoginActivity> activityRule = new ActivityTestRule<>(MainLoginActivity.class);

    @Test
    public void LoginS() {
        String id = "1";
        String pw = "1";

        Espresso.onView(withId(R.id.login_idbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        Espresso.onView(withId(R.id.login_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF1() {
        String id = "10";
        String pw = "10";

        Espresso.onView(withId(R.id.login_idbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 화면 전환이 안 됐으므로 레이아웃이 그대로 있을 것임. 그러므로 fail이 정상.
        Espresso.onView(withId(R.id.login_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF2() {
        String id = "1";
        String pw = "";
        // 아이디는 입력했으나 비밀번호를 입력하지 않음
        Espresso.onView(withId(R.id.login_idbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 화면 전환이 안 됐으므로 레이아웃이 그대로 있을 것임. 그러므로 fail이 정상.
        Espresso.onView(withId(R.id.login_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF3() {
        String id = "";
        String pw = "";
        // 아이디는와 비밀번호 둘 다 입력하지 않음.
        Espresso.onView(withId(R.id.login_idbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 화면 전환이 안 됐으므로 레이아웃이 그대로 있을 것임. 그러므로 fail이 정상.
        Espresso.onView(withId(R.id.login_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void MissField() {
        // 화면 전환이 안 됐으므로 레이아웃이 그대로 있을 것임. 그러므로 fail이 정상.
        Espresso.onView(withId(R.id.login_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void GoBack() {
        Espresso.onView(withId(R.id.back_login)).perform(click()).check(doesNotExist());
    }

    @Test
    public void GoSignup() {
        Espresso.onView(withId(R.id.return_signup)).perform(click()).check(doesNotExist());
    }

    @Test
    public void GoPassword() {
        Espresso.onView(withId(R.id.go_fpassword_text)).perform(click()).check(doesNotExist());
    }

}