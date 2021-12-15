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
public class MainSignupActivityTest {
    @Rule
    public ActivityTestRule<MainSignupActivity> activityRule = new ActivityTestRule<>(MainSignupActivity.class);

    @Test
    public void TextField() {
        String name = "name";
        String phone = "010";
        String email = "email";
        String id = "id";
        String pw = "pw";
        String pwch = "pw";

        // 값 입력
        Espresso.onView(withId(R.id.signup_name)).perform(typeText(name), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_phone)).perform(typeText(phone), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_email)).perform(typeText(email), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_id)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.signup_password)).perform(typeText(pw), closeSoftKeyboard());
        Espresso.onView(withId(R.id.confirm_pwbox)).perform(typeText(pwch), closeSoftKeyboard());

        // 값 확인
        Espresso.onView(withId(R.id.signup_name)).check(matches(withText(name)));
        Espresso.onView(withId(R.id.signup_phone)).check(matches(withText(phone)));
        Espresso.onView(withId(R.id.signup_email)).check(matches(withText(email)));
        Espresso.onView(withId(R.id.signup_id)).check(matches(withText(id)));
        Espresso.onView(withId(R.id.signup_password)).check(matches(withText(pw)));
        Espresso.onView(withId(R.id.confirm_pwbox)).check(matches(withText(pwch)));

    }

    @Test
    public void ButtonDIS() {
        String id = "10";   // 존재하지 않는 아이디로 실험
        Espresso.onView(withId(R.id.signup_id)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.check_id)).perform(click());
        // 알림이므로 뒤로가기 버튼으로 해결 가능
        Espresso.pressBack();
        // 사용 가능한 아이디라면 입력 필드 수정 불가능함 -> 정상
        Espresso.onView((withId(R.id.signup_id))).check(matches(not(isEnabled())));
        // 사용 가능한 아이디라면 버튼 노출x -> 정상
        Espresso.onView((withId(R.id.check_id))).check(matches(withEffectiveVisibility(Visibility.GONE)));
    }

    @Test
    public void ButtonON() {
        String id = "1";    // 이미 존재하는 아이디로 실험
        Espresso.onView(withId(R.id.signup_id)).perform(typeText(id), closeSoftKeyboard());
        Espresso.onView(withId(R.id.check_id)).perform(click());
        // 알림이므로 뒤로가기 버튼으로 해결 가능
        Espresso.pressBack();
        // 사용 불가능한 아이디라면 입력 필드 수정 가능함 -> 정상
        Espresso.onView((withId(R.id.signup_id))).check(matches(is(isEnabled())));
        // 사용 불가능한 아이디라면 버튼 노출 유지 -> 정상
        Espresso.onView((withId(R.id.check_id))).check(matches(withEffectiveVisibility(Visibility.VISIBLE)));
    }

    @Test
    public void Login() {
        // 해당 액티비티 요소가 없으므로 정답
        Espresso.onView(withId(R.id.go_login_txt)).perform(click()).check(doesNotExist());
    }

    @Test
    public void GoBack() {
        // 해당 액티비티 요소가 없으므로 정답
        Espresso.onView(withId(R.id.back_signup)).perform(click()).check(doesNotExist());
    }

}