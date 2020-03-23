package kr.co.youhyun.a20200323_loginandsignup;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import org.json.JSONObject;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityMainBinding;
import kr.co.youhyun.a20200323_loginandsignup.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

//        저장된 토큰을 확인해서 => 그 토큰으로 내 정보를 서버에서 다시 불러올것 - 조회 (Get)
        ServerUtil.getReuestMyInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("내정보", json.toString());
            }
        });

    }
}
