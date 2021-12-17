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
public class ItemCheckScreenTest {
    @Rule
    public ActivityTestRule<ItemCheckScreen> activityRule = new ActivityTestRule<ItemCheckScreen>(ItemCheckScreen.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setLoginID("1");
            gameAccountInfo.setLoginPW("1");
            gameAccountInfo.setGameName("MapleStory");
            gameAccountInfo.setCharacterName("1");
            gameAccountInfo.setGamePublisherID("1");
            gameAccountInfo.setGamePublisherPW("1");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };

    @Test
    public void GoBack() {
        // 경매장 화면으로 이동 하는지. 정상적으로 작동
        Espresso.onView(withId(R.id.back_auctionScreen)).perform(click()).check(doesNotExist());
    }

    @Test
    public void ListCheck1() {
        // 첫번째 항목을 선택하는 것이 잘 되는지. 정상적으로 작동
        onData(anything()).inAdapterView(withId(R.id.my_item_list)).atPosition(0).perform(click());
    }

    @Test
    public void ListCheck2() {
        // 두번째 항목을 선택하는 것이 잘 되는지. 정상적으로 작동
        onData(anything()).inAdapterView(withId(R.id.my_item_list)).atPosition(1).perform(click());
    }

    @Test
    public void ButtonClick1() {
        // 리스트에서 선택하지 않은 채 버튼을 누르면 오류 발생
        Espresso.onView(withId(R.id.my_item_list)).perform(click()).check(doesNotExist());
    }

    @Test
    public void DropButtonClick() {
        // 리스트에서 선택 후 판매하기 버튼 클릭 후 판매하기 화면으로 이동. 정상적으로 작동
        onData(anything()).inAdapterView(withId(R.id.my_item_list)).atPosition(0).perform(click());
        Espresso.onView(withId(R.id.sellButton)).perform(click()).check(doesNotExist());
    }
}