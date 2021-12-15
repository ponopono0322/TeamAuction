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
    public class GameChActivityTest {

    @Rule
    public ActivityTestRule<GameChActivity> activityRule = new ActivityTestRule<GameChActivity>(GameChActivity.class){
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent();
            GameAccountInfo gameAccountInfo = new GameAccountInfo();
            gameAccountInfo.setLoginID("1");
            gameAccountInfo.setLoginPW("1");
            gameAccountInfo.setGameName("DF");
            gameAccountInfo.setGamePublisherID("8");
            gameAccountInfo.setGamePublisherPW("8");
            intent.putExtra("account_info", gameAccountInfo);
            return intent;
        }
    };

    @Test
    public void GoBack() {
        // 엑티비티가 넘어가는 것이므로 해당 아이디는 없어야 함
        Espresso.onView(withId(R.id.cancel_account_add)).perform(click()).check(doesNotExist());
    }

    @Test
    public void ListCheck1() {
        // 첫번째 항목을 선택하는 것이 잘 되는지
        onData(anything()).inAdapterView(withId(R.id.listview2)).atPosition(0).perform(click());
    }

    @Test
    public void ListCheck2() {
        // 두번째 항목을 선택하는 것이 잘 되는지
        onData(anything()).inAdapterView(withId(R.id.listview2)).atPosition(1).perform(click());
    }

    @Test
    public void ButtonClick1() {
        // 리스트에서 선택하지 않은 채 버튼을 누르면 오류 발생
        Espresso.onView(withId(R.id.listview2)).perform(click()).check(doesNotExist());
    }

    @Test
    public void ButtonClick2() {
        // 리스트에서 선택 후 버튼 클릭. 정상적으로 작동
        onData(anything()).inAdapterView(withId(R.id.listview2)).atPosition(1).perform(click());
        Espresso.onView(withId(R.id.select_game_character)).perform(click()).check(doesNotExist());
    }
}