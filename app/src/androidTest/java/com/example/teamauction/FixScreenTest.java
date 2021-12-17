package com.example.teamauction;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.*;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FixScreenTest {
    @Rule
    public ActivityTestRule<FixScreen> activityRule = new ActivityTestRule<FixScreen>(FixScreen.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setCharacterName("1");
            gameAccountInfo.setGameName("MapleStory");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };

    @Test
    public void PriceBox() {
        // 에디트텍스트에 값을 입력. 정상적으로 작동
        Espresso.onView(withId(R.id.fixCostBox)).perform(typeText("123"), closeSoftKeyboard());
    }

    @Test
    public void QuantityBox() {
        // 에디트텍스트에 값을 입력. 정상적으로 작동
        Espresso.onView(withId(R.id.fixQuantityBox)).perform(typeText("123"), closeSoftKeyboard());
    }

    @Test
    public void Cancel() {
        // 토스트 메세지를 뜨고 판매중인 아이템 화면으로 잘 이동하는지. 정상적으로 작동
        Espresso.onView(withId(R.id.fix_check_no)).perform(click()).check(doesNotExist());
    }
}