package kr.co.youhyun.a20200323_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivitySplashBinding;
import kr.co.youhyun.a20200323_loginandsignup.utils.ContextUtil;

public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        토큰이 저장되어있고, 자동로그인이 체크되어있다면 => 매인엑티비티로 이동

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!ContextUtil.getUserToken(mContext).equals("") && ContextUtil.isAutoLoginCheck(mContext)) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 2000);




    }
}
