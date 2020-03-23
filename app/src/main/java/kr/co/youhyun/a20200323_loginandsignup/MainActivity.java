package kr.co.youhyun.a20200323_loginandsignup;

import android.os.Bundle;
import android.util.Log;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityMainBinding;
import kr.co.youhyun.a20200323_loginandsignup.datas.User;
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

        ServerUtil.getReuestUserList(mContext, "ALL", new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("사용자목록", json.toString());
            }
        });

//        저장된 토큰을 확인해서 => 그 토큰으로 내 정보를 서버에서 다시 불러올것 - 조회 (Get)
        ServerUtil.getReuestMyInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("내정보", json.toString());

                try {
                    int code = json.getInt("code");
                    if (code == 200) {
                        JSONObject data = json.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        final User myinfo = User.getUserFromJson(user);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nameTxt.setText(myinfo.getName());
                                binding.memoTxt.setText(myinfo.getMemo());
                                binding.phoneTxt.setText(myinfo.getPhone());
                            }
                        });
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       });

    }
}
