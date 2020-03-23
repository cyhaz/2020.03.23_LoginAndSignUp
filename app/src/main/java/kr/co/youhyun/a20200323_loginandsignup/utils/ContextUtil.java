package kr.co.youhyun.a20200323_loginandsignup.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextUtil {

//    메모장파일처럼 데이터를 저장할 공간의 이름으로 쓸 변수
    private static final String prefName = "myPref";

//    항목명도 자동완성으로 지원할 수 있도록 미리 변수화 시켜두기
    private static final String EMAIL = "EMAIL";

//    해당 항목의 값을 저장(setter) / 조회(getter) 하는 메쏘드 두 개 생성

//    setter
    public static void setEmail(Context context, String email) {
        // 메모장을 이용해서 값을 기록 => 메모장 파일부터 열어야한다.
        // 메모장은 Context를 이용해서 열어야한다. => Context 변수도 파라미터로 필요!

        // 메모장을 열 때 1) 어떤 메모장? -> prefName 변수에 담아둠 2) 어떤 모드? -> Context.MODE_PRIVATE 으로 열어주기
        SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        // 열린 메모장에 항목(Key)/값(value) 저장
        pref.edit().putString(EMAIL, email).apply();   // apply()로 저장

    }

}
