package com.example.teamauction;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.junit.Assert.*;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsRule = new IntentsTestRule<MainActivity>(MainActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setLoginID("1");
            gameAccountInfo.setLoginPW("1");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };


    @Test
    public void LogoutS() {
        onView(withId(R.id.go_logout)).perform(click());
        // 정상적으로 로그아웃 되었으면 레이아웃이 변경되므로 기존 레이아웃이 존재하지 않는 것이 정답
        onView(withId(R.id.check_yes)).perform(click()).check(doesNotExist());
    }

    @Test
    public void LogoutF() {
        onView(withId(R.id.go_logout)).perform(click());
        // 정상적으로 로그아웃 되었으면 레이아웃이 변경되므로 기존 레이아웃이 존재하는 것이 정답(따라서 결과는 fail)
        onView(withId(R.id.check_no)).perform(click());
        onView((withId(R.id.go_logout))).check(doesNotExist());
    }

    @Test
    public void ListClick() {
        SystemClock.sleep(1000);
        // 리스트뷰 정상 작동 확인
        onView(ViewMatchers.withId(R.id.linked_id_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }

    @Test
    public void NewAccount() {
        // 눌렀을 때 새 창으로 정상 이동 확인
        Espresso.onView(withId(R.id.login_account)).perform(click()).check(doesNotExist());
    }
}