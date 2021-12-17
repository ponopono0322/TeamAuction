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
public class AuctionScreenTest {
    @Rule
    public ActivityTestRule<AuctionScreen> activityRule = new ActivityTestRule<AuctionScreen>(AuctionScreen.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setLoginID("1");
            gameAccountInfo.setGameName("Maple");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };

    @Test
    public void GoBack() {
        // 메인 엑티비티로 이동 하는지
        Espresso.onView(withId(R.id.back_button)).perform(click()).check(doesNotExist());
    }

    @Test
    public void ListCheck1() {
        // 첫번째 항목을 선택하는 것이 잘 되는지
        onData(anything()).inAdapterView(withId(R.id.auctionList)).atPosition(0).perform(click());
    }

    @Test
    public void ListCheck2() {
        // 두번째 항목을 선택하는 것이 잘 되는지
        onData(anything()).inAdapterView(withId(R.id.auctionList)).atPosition(1).perform(click());
    }

    @Test
    public void ButtonClick1() {
        // 리스트에서 선택하지 않은 채 버튼을 누르면 오류 발생
        Espresso.onView(withId(R.id.auctionList)).perform(click()).check(doesNotExist());
    }

    @Test
    public void ButtonClick2() {
        // 리스트에서 선택 후 버튼 클릭. 정상적으로 작동
        onData(anything()).inAdapterView(withId(R.id.auctionList)).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.buyButton)).perform(click()).check(doesNotExist());
    }

    @Test
    public void Search() {
        // 에디트텍스트에 값을 입력. 정상적으로 작동
        Espresso.onView(withId(R.id.editTextFilter)).perform(typeText("cub"), closeSoftKeyboard());
    }

    @Test
    public void GoSellingScreen() {
        // 판매중 화면으로 잘 이동하는지. 정상적으로 작동
        Espresso.onView(withId(R.id.moveSellingButton)).perform(click()).check(doesNotExist());
    }

    @Test
    public void GoItemCheckScreen() {
        // 내 아이템 화면으로 잘 이동하는지. 정상적으로 작동
        Espresso.onView(withId(R.id.moveMyitemButton)).perform(click()).check(doesNotExist());
    }
}