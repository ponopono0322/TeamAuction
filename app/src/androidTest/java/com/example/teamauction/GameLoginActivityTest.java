package com.example.teamauction;

import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GameLoginActivityTest {

    @Rule
    public ActivityTestRule<GameLoginActivity> activityRule = new ActivityTestRule<GameLoginActivity>(GameLoginActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setLoginID("1");
            gameAccountInfo.setLoginPW("1");
            gameAccountInfo.setGameName("DF");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };

    @Test
    public void GoBack() {
        Espresso.onView(withId(R.id.back_add_account)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF1() {
        // 그냥 눌렀다면 필요한 아이디 값이 올바르게 입력되지 않았으므로 실패
        Espresso.onView(withId(R.id.login_publisher_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF2() {
        // 아이디를 입력했으나 비밀번호가 올바르지 못할 때
        String id = "10";
        String pw = "10";

        Espresso.onView(withId(R.id.login_publisher_emailbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_publisher_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 비밀번호가 달라 다음 페이지로 이동하지 못하므로 실패가 맞음
        Espresso.onView(withId(R.id.login_publisher_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginF3() {
        // 아이디를 입력했으나 비밀번호를 입력하지 않았을 때
        String id = "10";
        String pw = "";

        Espresso.onView(withId(R.id.login_publisher_emailbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_publisher_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 비밀번호가 달라 다음 페이지로 이동하지 못하므로 실패가 맞음
        Espresso.onView(withId(R.id.login_publisher_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LoginS() {
        String id = "8";
        String pw = "8";

        Espresso.onView(withId(R.id.login_publisher_emailbox)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.login_publisher_pwbox)).perform(typeText(pw), closeSoftKeyboard());

        // 정상적으로 페이지가 이동하면서 레이아웃 아이디를 찾을 수 없음. 따라서 올바르게 작동
        Espresso.onView(withId(R.id.login_publisher_button)).perform(click()).check(doesNotExist());
    }
}